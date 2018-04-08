package models;

import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;
import services.QueryHandler;
import shared.user.auth.Auth;

import java.net.Socket;

public class AuthModel {
    private final Socket socket;
    private final QueryHandler queryHandler;

    public AuthModel(Socket socket) {
        this.socket = socket;
        this.queryHandler = new QueryHandler(socket);
    }

    public Observable<Object> login() {
        ReplaySubject<Object> subject = ReplaySubject.create(1);

        queryHandler.login().subscribe(
            data -> {
                handleLogin((Auth.LoginData) data);
                subject.onNext(true);
                subject.onComplete();
            },
            errors -> {
                subject.onError(errors);
            }
        );

        return subject;
    }

    public Observable<Object> register() {
        ReplaySubject<Object> subject = ReplaySubject.create(1);

        queryHandler.register().subscribe(
            data -> {
                handleRegister((Auth.RegisterData) data);
                subject.onNext(true);
                subject.onComplete();
            },
            errors -> {
                subject.onError(errors);
            }
        );

        return subject;
    }

    private void handleLogin(Auth.LoginData loginData) {
        Auth.AuthResponse data = Auth.AuthResponse.newBuilder()
            .setMessage(Auth.AuthResponse.MessageType.Success)
            .build();

        queryHandler.sendData("mutation", "loginSuccess", data);
    }

    private void handleRegister(Auth.RegisterData registerData) {
        Auth.AuthResponse data = Auth.AuthResponse.newBuilder()
            .setMessage(Auth.AuthResponse.MessageType.Success)
            .build();

        queryHandler.sendData("mutation", "registerSuccess", data);
    }
}
