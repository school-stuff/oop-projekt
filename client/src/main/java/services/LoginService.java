package services;

import shared.user.auth.Auth;

import java.io.DataInputStream;
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
        Auth.LoginData loginData =
            Auth.LoginData.newBuilder()
                .setEmail(username)
                .setPassword(password)
                .build();

        server.sendData("mutation");
        server.sendData("login");
        server.sendData(loginData);

        InputStream input = server.getInput();
        DataInputStream dataInputStream = server.getDataInput();
        Auth.AuthResponse response;

        String s1 = dataInputStream.readUTF();
        String s2 = dataInputStream.readUTF();
        response = Auth.AuthResponse.parseDelimitedFrom(input);

        if (response.getMessage() == Auth.AuthResponse.MessageType.Error) {
            return false;
        } else if (response.getMessage() == Auth.AuthResponse.MessageType.Success) {
            return true;
        }
        return false;
    }

    // TODO: Register method

}
