package models;


import io.reactivex.Observable;
import shared.user.auth.Auth;

import java.net.Socket;

public class ClientModel {
    private final Socket socket;

    public ClientModel(Socket socket) {
        this.socket = socket;
    }

    public Observable<Auth.LoginData> authenticate() {
        AuthModel authModel = new AuthModel(socket);

        return authModel.login();
    }
}
