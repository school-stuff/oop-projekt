package services;

import com.google.protobuf.AbstractMessage;
import io.reactivex.Observable;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    // server requires two helper arguments that identify the following data
    public void sendData(String string) throws IOException {
        try (OutputStream output = socket.getOutputStream();
             DataOutputStream dataOutput = new DataOutputStream(output)) {
            dataOutput.writeUTF(string);
        }
    }

    // for sending the protobuf specified data
    public void sendData(AbstractMessage data) throws IOException {
        try (OutputStream output = socket.getOutputStream()) {
            data.writeDelimitedTo(output);
        }
    }

    public InputStream getInput() throws IOException {
        return socket.getInputStream();
    }


    public Observable<DataInterface> getData(String requestData) {
        // gets requested data from server
        return null;
    }
}


