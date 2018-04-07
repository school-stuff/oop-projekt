package services;

import models.ClientModel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientsManager {
    private static ClientsManager ourInstance = new ClientsManager();

    public static ClientsManager getInstance() {
        return ourInstance;
    }

    private final List<ClientModel> clients = new ArrayList<>();

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
                // TODO: error handling e.g. s
                // awaitConnections();
                Thread.currentThread().interrupt();
                throw new Error(e);
            }
        }).start();
    }

    private void handleNewClient(Socket socket) {
        ClientModel client = new ClientModel(socket);
        clients.add(client);
        client.authenticate().subscribe(next -> {
            // TODO: queue clients
        });
    }
}
