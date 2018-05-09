package models;


import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;
import shared.user.auth.Auth;

import java.net.Socket;

public class ClientModel {
    private final Socket socket;

    public ClientModel(Socket socket) {
        this.socket = socket;
    }

    public Observable<Auth.LoginData> authenticate() {
        AuthModel authModel = new AuthModel(socket);
        ReplaySubject<Auth.LoginData> subject = ReplaySubject.create();

        Observable<Auth.LoginData> login = authModel.login();
        Observable<Auth.RegisterData> register = authModel.register();

        login.takeUntil(register).subscribe(data -> {
            subject.onNext(data);
            subject.onComplete();
        });
        register.takeUntil(login).subscribe(data -> {
            subject.onNext(
                Auth.LoginData.newBuilder()
                    .setEmail(data.getEmail())
                    .setPassword(data.getPassword())
                    .build());
            subject.onComplete();
        });


        return subject;
    }
}
