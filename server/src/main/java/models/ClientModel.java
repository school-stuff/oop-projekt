package models;


import java.net.Socket;

public class ClientModel {
    private final Socket socket;

    public ClientModel(Socket socket) {
        this.socket = socket;
        new Thread(this::handleClient).start();
    }

    private void handleClient() {
        new AuthModel(socket);
    }
}
