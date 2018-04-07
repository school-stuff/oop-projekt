package services;

import io.reactivex.Observable;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ServerCommunicationService {

    private final Socket socket;

    private static ServerCommunicationService ourInstance = new ServerCommunicationService();

    public static ServerCommunicationService getInstance() {
        return ourInstance;
    }

    ServerCommunicationService() {
        socket = new Socket();
    }

    public boolean socketTryConnect() {
        try {
            socket.connect(new InetSocketAddress("localhost", 8001));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void killSocket() throws IOException {
        socket.close();
    }

    public void sendData(DataInterface data) {
        // sends data to server
    }

    public Observable<DataInterface> getData(String requestData) {
        // gets requested data from server
        return null;
    }
}


