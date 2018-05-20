package services;

import com.google.protobuf.AbstractMessage;
import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;
import shared.errors.UnknownMessage;
import shared.match.item.RenderItem;
import shared.match.location.Location;
import shared.match.plant.Flower;
import shared.match.queue.Queue;
import shared.user.auth.Auth;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class QueryHandler {
    private Socket socket;
    private Map<String, ReplaySubject<AbstractMessage>> mutationResponseList = new HashMap<>();
    private Map<String, ReplaySubject<AbstractMessage>> queryList = new HashMap<>();
    private Map<String, ReplaySubject<AbstractMessage>> watchQueryList = new HashMap<>();
    private OutputStream outputStream;
    private DataOutputStream dataOutputStream;
    private InputStream inputStream;

    public QueryHandler(Socket socket) {
        this.socket = socket;
        clientCommunicationThread(socket);
    }

    private void clientCommunicationThread(Socket socket) {
        new Thread(() -> {
            try (
                    DataInputStream inputStream = new DataInputStream(socket.getInputStream())
            ) {
                while (true) {
                    String messageType = inputStream.readUTF();
                    String messageName = inputStream.readUTF();

                    switch (messageType) {
                        case "query":
                            handleQuery(messageName);
                            break;
                        case "watchQuery":
                            handleWatchQuery(messageName);
                            break;
                        case "mutation":
                            handleMutation(messageName);
                            break;
                        default:
                            handleUnknownMessage();
                            break;
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
                // TODO: handle
            }
        }).start();
    }

    public Observable<AbstractMessage> getMatchQueue() {
        return createWatchQuery("matchQueue");
    }

    public Observable<AbstractMessage> getPlayerLocation() {
        return createWatchQuery("matchLocation");
    }

    public Observable<AbstractMessage> login() {
        return createMutation("login");
    }

    public Observable<AbstractMessage> register() {
        return createMutation("register");
    }

    public Observable<AbstractMessage> locationRequest() {
        return createMutation("matchLocation");
    }


    public void sendData(String type, String message, AbstractMessage data) {
        try {
            DataOutputStream outputStream = getDataOutputStream();
            outputStream.writeUTF(type);
            outputStream.writeUTF(message);
            data.writeDelimitedTo(outputStream);
        } catch (IOException e) {
            throw new RuntimeException("Data not sent to client, exception description :" + e);
        }
    }

    public void updateMatchQueue(AbstractMessage queue) {
        updateWatchQueryData("matchQueue", queue);
    }

    public void updateLocation(AbstractMessage location) {
        updateWatchQueryData("matchLocation", location);
    }

    public void updateOpponentLocation(AbstractMessage location) {
        updateWatchQueryData("opponentLocation", location);
    }

    public void updateItemData(AbstractMessage itemData) {
        updateWatchQueryData("itemData", itemData);
    }

    public void updateHealth(AbstractMessage healthData) {
        updateWatchQueryData("matchHealth", healthData);
    }

    private ReplaySubject<AbstractMessage> createMutation(String mutationName) {
        ReplaySubject<AbstractMessage> mutation = mutationResponseList.get(mutationName);
        if (mutation == null) {
            mutationResponseList.put(mutationName, ReplaySubject.create(1));
            mutation = mutationResponseList.get(mutationName);
        }
        return mutation;
    }

    private ReplaySubject<AbstractMessage> createQuery(String queryName) {
        ReplaySubject<AbstractMessage> query = queryList.get(queryName);
        if (query == null) {
            queryList.put(queryName, ReplaySubject.create(1));
            query = queryList.get(queryName);
        }
        return query;
    }

    public ReplaySubject<AbstractMessage> createWatchQuery(String queryName) {
        ReplaySubject<AbstractMessage> query = watchQueryList.get(queryName);
        if (query == null) {
            watchQueryList.put(queryName, ReplaySubject.create());
            query = watchQueryList.get(queryName);
        }
        return query;
    }


    private DataOutputStream getDataOutputStream() {
        if (dataOutputStream != null) return dataOutputStream;

        dataOutputStream = new DataOutputStream(getOutputStream());

        return dataOutputStream;
    }

    private OutputStream getOutputStream() {
        if (outputStream != null) return outputStream;

        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            // TODO: handle
        }

        return outputStream;
    }

    private InputStream getInputStream() {
        if (inputStream != null) return inputStream;

        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            // TODO: handle
        }

        return inputStream;
    }

    private void handleMutation(String messageName) throws IOException {
        // TODO: make this elegant
        switch (messageName) {
            case "matchLocation":
                updateMutationData(messageName, Location.UserLocation.parseDelimitedFrom(getInputStream()));
                break;
            case "login":
                updateMutationData(messageName, Auth.LoginData.parseDelimitedFrom(getInputStream()));
                break;
            case "register":
                updateMutationData(messageName, Auth.RegisterData.parseDelimitedFrom(getInputStream()));
                break;
            default:
                handleUnknownMessage();
                break;
        }
    }

    private void handleQuery(String messageName) throws IOException {
        // TODO: make this elegant
        switch (messageName) {
            default:
                handleUnknownMessage();
                break;
        }
    }

    private void handleWatchQuery(String messageName) throws IOException {
        // TODO: make this elegant
        switch (messageName) {
            case "matchQueue":
                updateWatchQueryData(messageName, Queue.Filters.parseDelimitedFrom(getInputStream()));
                break;
            case "matchLocation":
                updateWatchQueryData(messageName, Location.Filters.parseDelimitedFrom(getInputStream()));
                break;
            case "opponentLocation":
                updateWatchQueryData(messageName, Location.Filters.parseDelimitedFrom(getInputStream()));
                break;
            case "itemData":
                updateWatchQueryData(messageName, RenderItem.Filters.parseDelimitedFrom(getInputStream()));
            case "matchHealth":
                updateWatchQueryData(messageName, Location.Filters.parseDelimitedFrom(getInputStream()));
                break;
            default:
                handleUnknownMessage();
                break;
        }
    }

    private void handleUnknownMessage() {
        // TODO: tell if message type or query name was invalid
        UnknownMessage.MessageTypeError errorMessage = UnknownMessage.MessageTypeError.newBuilder()
                .setMessage(UnknownMessage.MessageTypeError.ErrorType.UnknownProperty)
                .build();
        // TODO: sent error to client
    }

    private void updateMutationData(String mutationName, AbstractMessage data) {
        createMutation(mutationName).onNext(data);
    }

    private void updateQueryData(String queryName, AbstractMessage data) {
        // TODO: make it, that once queried, this closes and removes self
        createQuery(queryName).onNext(data);
    }

    private void updateWatchQueryData(String queryName, AbstractMessage data) {
        while (true) {
            if (watchQueryList.containsKey(queryName)) {
                ReplaySubject<AbstractMessage> observable = watchQueryList.get(queryName);
                if (data.isInitialized()) observable.onNext(data);
                return;
            }
            createWatchQuery(queryName);
        }
    }
}
