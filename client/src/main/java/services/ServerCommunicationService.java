package services;

import com.google.protobuf.AbstractMessage;
import io.reactivex.Observable;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ServerCommunicationService {

    private Socket socket;

    private static ServerCommunicationService ourInstance = new ServerCommunicationService();
    private OutputStream output;
    private DataOutputStream dataOutput;
    private InputStream input;
    private DataInputStream dataInput;

    public static ServerCommunicationService getInstance() {
        return ourInstance;
    }

    ServerCommunicationService() {
        this.socket = new Socket();
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
        getDataOutput().writeUTF(string);
    }

    // for sending the protobuf specified data
    public void sendData(AbstractMessage data) throws IOException {
        data.writeDelimitedTo(getOutput());
    }

    public Observable<DataInterface> getData(String requestData) {
        // gets requested data from server
        return null;
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
}


