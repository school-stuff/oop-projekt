package services;

import shared.user.auth.Auth;
import java.io.IOException;
import java.io.InputStream;


public class LoginService {
    private static LoginService ourInstance = new LoginService();

    public static LoginService getInstance() {
        return ourInstance;
    }

    private LoginService() {}

    ServerCommunicationService server = ServerCommunicationService.getInstance();

    public boolean authenticateUser(String username, String password) throws IOException {
        Auth.AuthMessage loginData = Auth.AuthMessage.newBuilder()
                .setLoginData(
                        Auth.LoginData.newBuilder()
                                .setEmail(username)
                                .setPassword(password)
                                .build()
                ).build();

        server.sendData("mutation");
        server.sendData("login");
        server.sendData(loginData);

        InputStream input = server.getInput();
        Auth.AuthResponse response;
        try {
            response = Auth.AuthResponse.parseDelimitedFrom(input);
            if (response.getMessage() == Auth.AuthResponse.MessageType.Error) {
                return false;
            } else if (response.getMessage() == Auth.AuthResponse.MessageType.Success) {
                return true;
            }
        } finally {
            input.close();
        }
        return false;
    }

    // TODO: Register method

}
