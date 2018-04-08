package services;

import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;
import shared.user.auth.Auth;

import java.io.IOException;


public class LoginService {
    private static LoginService ourInstance = new LoginService();

    public static LoginService getInstance() {
        return ourInstance;
    }

    private LoginService() {}

    ServerCommunicationService server = ServerCommunicationService.getInstance();

    public Observable<Boolean> authenticateUser(String username, String password) throws IOException {
        ReplaySubject<Boolean> subject = ReplaySubject.create();

        Auth.LoginData loginData =
            Auth.LoginData.newBuilder()
                .setEmail(username)
                .setPassword(password)
                .build();

        server.sendData("mutation", "login", loginData);
        server.getData("loginSuccess").subscribe(data -> {
            Auth.AuthResponse response = (Auth.AuthResponse) data;

            if (response.getMessage() == Auth.AuthResponse.MessageType.Error) {
                subject.onNext(false);
            } else if (response.getMessage() == Auth.AuthResponse.MessageType.Success) {
                subject.onNext(true);
            }
        });

        return subject;
    }

    // TODO: Register method

}
