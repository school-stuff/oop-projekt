package services;

public class LoginService {
    private static LoginService ourInstance = new LoginService();

    public static LoginService getInstance() {
        return ourInstance;
    }

    private LoginService() {}


    // TODO: authentication communication
    public boolean authenticateUser(String username, String password) {
        return true;
    }


}
