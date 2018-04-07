package models;

import shared.user.auth.Auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    private final Socket socket;

    public Client(Socket socket) {
        this.socket = socket;
        new Thread(this::listenAuth).start();
    }

    private Auth.AuthResponse handleLogin(Auth.LoginData loginData) {
        return Auth.AuthResponse.newBuilder()
            .setMessage(Auth.AuthResponse.MessageType.Success)
            .build();
    }

    private Auth.AuthResponse handleRegister(Auth.RegisterData registerData) {
        return Auth.AuthResponse.newBuilder()
            .setMessage(Auth.AuthResponse.MessageType.Success)
            .build();
    }

    private void listenAuth() {
        try (
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream()
        ) {
            Auth.AuthMessage message = Auth.AuthMessage.parseDelimitedFrom(input);

            Auth.AuthResponse response = Auth.AuthResponse.newBuilder()
                .setMessage(Auth.AuthResponse.MessageType.Error)
                .build();

            if (message.hasLoginData()) {
                response = handleLogin(message.getLoginData());
            } else if (message.hasRegisterData()) {
                response = handleRegister(message.getRegisterData());
            }

            response.writeDelimitedTo(output);

        } catch (IOException e) {
            // TODO: handle
            throw new Error(e);
        }
    }
}
