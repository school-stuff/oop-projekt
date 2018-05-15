package services;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.DescriptorProtos;
import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;
import javafx.application.Platform;
import shared.match.location.Location;
import shared.match.queue.Queue;
import shared.user.auth.Auth;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownServiceException;
import java.util.HashMap;
import java.util.Map;

public class ServerCommunicationService {

    private Socket socket;

    public ReplaySubject<Boolean> isConnected = ReplaySubject.create(1);

    private static ServerCommunicationService ourInstance = new ServerCommunicationService();
    private OutputStream output;
    private DataOutputStream dataOutput;
    private InputStream input;
    private DataInputStream dataInput;
    private Map<String, ReplaySubject<AbstractMessage>> queryList = new HashMap<>();

    public static ServerCommunicationService getInstance() {
        return ourInstance;
    }

    ServerCommunicationService() {
        this.socket = new Socket();
        pingServer();
        serverCommunicationThread();
    }

    private void serverCommunicationThread() {
        new Thread(() -> {
            try (DataInputStream inputStream = new DataInputStream(socket.getInputStream())) {
                while (true) {
                    String messageType = inputStream.readUTF();
                    String messageName = inputStream.readUTF();

                    switch (messageType) {
                        case "update":
                            handleQuery("query_", messageName);
                            break;
                        case "watchUpdate":
                            handleQuery("watchquery_", messageName);
                            break;
                        default:
                            break;
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
                // TODO: handle
            }
        }).start();
    }

    private void pingServer() {
        // Do once for linear connection
        isConnected.onNext(socketTryConnect());
        new Thread(() -> {
            while (true) {
                isConnected.onNext(socketTryConnect());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    continue;
                }
            }
        }).start();
    }

    public boolean socketTryConnect() {
        if (socket.isConnected()) {
            return true;
        }
        try {
            socket.connect(new InetSocketAddress("localhost", 8001));
        } catch (IOException e) {
            socket = new Socket();
            return false;
        }
        return true;
    }

    public void sendData(String requestType, String requestName, AbstractMessage data) throws IOException {
        getDataOutput().writeUTF(requestType);
        getDataOutput().writeUTF(requestName);
        data.writeDelimitedTo(getOutput());
    }

    public Observable<AbstractMessage> getData(String requestName) {
        return createQuery("query_" + requestName);
    }

    public InputStream getInput() {
        if (input != null) return input;

        try {
            input = socket.getInputStream();
        } catch (IOException e) {
            // TODO: handle
        }

        return input;
    }

    public DataInputStream getDataInput() {
        if (dataInput != null) return dataInput;

        dataInput = new DataInputStream(getInput());

        return dataInput;
    }

    public Observable<AbstractMessage> watchData(String requestName) {
        return createQuery("watchquery_" + requestName);
    }

    private ReplaySubject<AbstractMessage> createQuery(String queryName) {
        ReplaySubject<AbstractMessage> query = queryList.get(queryName);
        if (query == null) {
            queryList.put(queryName, ReplaySubject.create(1));
            query = queryList.get(queryName);
        }
        return query;
    }

    private OutputStream getOutput() {
        if (output != null) return output;

        try {
            output = socket.getOutputStream();
        } catch (IOException e) {
            // TODO: handle
        }

        return output;
    }

    private DataOutputStream getDataOutput() {
        if (dataOutput != null) return dataOutput;

        dataOutput = new DataOutputStream(getOutput());

        return dataOutput;
    }

    private void handleQuery(String prefix, String messageName) throws IOException {
        // TODO: make this elegant
        switch (messageName) {
            case "loginSuccess":
                updateQueryData(prefix + messageName, Auth.AuthResponse.parseDelimitedFrom(getInput()));
                break;
            case "registerSuccess":
                updateQueryData(prefix + messageName, Auth.AuthResponse.parseDelimitedFrom(getInput()));
                break;
            case "matchQueue":
                updateQueryData(prefix + messageName, Queue.MatchQueue.parseDelimitedFrom(getInput()));
                break;
            case "matchLocation":
                updateQueryData(prefix + messageName, Location.UserLocation.parseDelimitedFrom(getInput()));
                break;
            case "opponentLocation":
                updateQueryData(prefix + messageName, Location.UserLocation.parseDelimitedFrom(getInput()));
                break;
            default:
                break;
        }
    }

    private void updateQueryData(String mutationName, AbstractMessage data) {
        createQuery(mutationName).onNext(data);
    }
}
