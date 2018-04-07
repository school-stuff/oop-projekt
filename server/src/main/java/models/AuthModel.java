package models;

import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;
import shared.user.auth.Auth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class AuthModel {
    private final Socket socket;

    public AuthModel(Socket socket) {
        this.socket = socket;
    }

    public Observable authenticate() {
        ReplaySubject<Object> subject = ReplaySubject.create(1);

        try {
            listenAuth();
            subject.onNext(true);
        } catch (IOException e) {
            subject.onError(e);
        } finally {
            subject.onComplete();
        }

        return subject;
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

    private void listenAuth() throws IOException {
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

        }
    }
}
