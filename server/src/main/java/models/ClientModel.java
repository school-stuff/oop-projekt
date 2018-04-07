package models;


import io.reactivex.Observable;

import java.net.Socket;

public class ClientModel {
    private final Socket socket;

    public ClientModel(Socket socket) {
        this.socket = socket;
    }

    public Observable authenticate() {
        return new AuthModel(socket).authenticate();
    }
}
