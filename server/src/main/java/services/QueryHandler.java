package services;

import com.google.protobuf.AbstractMessage;
import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;
import shared.errors.UnknownMessage;
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

    public Observable<AbstractMessage> login() {
        return createMutation("login");
    }

    public Observable<AbstractMessage> register() {
        return createMutation("register");
    }

    public void sendData(String type, String message, AbstractMessage data) {
        try {
            DataOutputStream outputStream = getDataOutputStream();
            outputStream.writeUTF(type);
            outputStream.writeUTF(message);
            data.writeDelimitedTo(outputStream);
        } catch (IOException e) {
            // TODO: handle
        }
    }

    public void updateMatchQueue(AbstractMessage queue) {
        updateWatchQueryData("matchQueue", queue);
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

    private ReplaySubject<AbstractMessage> createWatchQuery(String queryName) {
        ReplaySubject<AbstractMessage> query = watchQueryList.get(queryName);
        if (query == null) {
            queryList.put(queryName, ReplaySubject.create(1));
            query = queryList.get(queryName);

            query.subscribe(data -> {
                sendData("watchUpdate", queryName, data);
            });
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
        createWatchQuery(queryName).onNext(data);
    }
}
