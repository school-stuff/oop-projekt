package models;

import com.google.protobuf.AbstractMessage;
import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;
import org.mindrot.jbcrypt.BCrypt;
import services.DatabaseService;
import services.QueryHandler;
import shared.user.auth.Auth;

import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthModel {
    private final Socket socket;
    private final QueryHandler queryHandler;
    private final DatabaseService databaseService;

    public AuthModel(Socket socket) {
        this.socket = socket;
        this.queryHandler = new QueryHandler(socket);
        this.databaseService = DatabaseService.getInstance();
    }

    public Observable<Auth.LoginData> login() {
        ReplaySubject<Auth.LoginData> subject = ReplaySubject.create(1);

        queryHandler.login().subscribe(
            data -> {
                Auth.LoginData result = (Auth.LoginData) data;
                handleLogin(result);
                subject.onNext(result);
                subject.onComplete();
            },
            errors -> {
                subject.onError(errors);
            }
        );

        return subject;
    }

    public Observable<Auth.RegisterData> register() {
        ReplaySubject<Auth.RegisterData> subject = ReplaySubject.create(1);

        queryHandler.register().subscribe(
            data -> {
                Auth.RegisterData result = (Auth.RegisterData) data;
                handleRegister(result);
                subject.onNext(result);
                subject.onComplete();
            },
            errors -> {
                subject.onError(errors);
            }
        );

        return subject;
    }

    private void handleLogin(Auth.LoginData loginData) {
        Connection connection = databaseService.getConnection();
        String email = loginData.getEmail();
        String password = loginData.getPassword();
        AbstractMessage data = null;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT password FROM user WHERE email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            if (checkPassword(password, (resultSet.getString("password")))) {
                data = Auth.AuthResponse.newBuilder()
                        .setMessage(Auth.AuthResponse.MessageType.Success)
                        .build();
            } else {
                data = Auth.AuthResponse.newBuilder()
                        .setMessage(Auth.AuthResponse.MessageType.Error)
                        .build();
            }
        } catch (SQLException e) {
            data = Auth.AuthResponse.newBuilder()
                    .setMessage(Auth.AuthResponse.MessageType.Error)
                    .build();
        } finally {
            queryHandler.sendData("update", "loginSuccess", data);
        }


    }

    private void handleRegister(Auth.RegisterData registerData) {
        Connection connection = databaseService.getConnection();
        String email = registerData.getEmail();
        String password = registerData.getPassword();
        AbstractMessage data = null;

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO user(email, password) VALUES (?, ?)");
            statement.setString(1, email);
            statement.setString(2, saltedPassword(password));
            statement.executeUpdate();
            data = Auth.AuthResponse.newBuilder()
                    .setMessage(Auth.AuthResponse.MessageType.Success)
                    .build();
        } catch (SQLException e) {
            data = Auth.AuthResponse.newBuilder()
                    .setMessage(Auth.AuthResponse.MessageType.Error)
                    .build();
        } finally {
            queryHandler.sendData("update", "registerSuccess", data);
        }
    }

    private boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }

    private String saltedPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(5));
    }
}
