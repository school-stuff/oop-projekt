package services;

import models.ClientModel;
import models.MatchModel;
import org.flywaydb.core.Flyway;
import shared.match.queue.Queue;
import shared.user.auth.Auth;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ClientsManager {
    private static ClientsManager ourInstance = new ClientsManager();

    public static ClientsManager getInstance() {
        return ourInstance;
    }

    private final Map<Auth.LoginData, QueryHandler> clients = new HashMap<>();

    private ClientsManager() {
        awaitConnections();
    }

    private void awaitConnections() {
        new Thread(() -> {
            try (
                ServerSocket socket = new ServerSocket(8001)
            ) {
                while (true) {
                    Socket accept = socket.accept();
                    handleNewClient(accept);
                }
            } catch (IOException e) {
                // TODO: error handling
                Thread.currentThread().interrupt();
                throw new Error(e);
            }
        }).start();
    }

    private void handleNewClient(Socket socket) {
        ClientModel client = new ClientModel(socket);

        client.authenticate().subscribe(user -> {
            clients.put(user, new QueryHandler(socket));
            updateClientsQueue();

        });
    }

    private void updateClientsQueue() {
        for (QueryHandler queryHandler : clients.values()) {
            ArrayList<Queue.Person> clientNames = new ArrayList<>();

            Set<Auth.LoginData> loginData = clients.keySet();
            for (Auth.LoginData user : loginData) {
                clientNames.add(
                    Queue.Person.newBuilder().setName(user.getEmail()).build()
                );
            }
            Queue.MatchQueue queue = Queue.MatchQueue.newBuilder().addAllPersons(clientNames).build();
            queryHandler.updateMatchQueue(queue);
        }
    }
}
