import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientSocket {
    private Socket socket;
    private int clientID; //an unique ID that gets set upon a successful login to the server.

    private static ClientSocket ourInstance = new ClientSocket();

    public static ClientSocket getInstance() {
        return ourInstance;
    }

    private ClientSocket() {
        initSocket();
    }

    public boolean socketTryConnect(String hostName, int port) {
        try {
            socket.connect(new InetSocketAddress(hostName, port));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private void initSocket() {
        socket = new Socket();
    }

    public void killSocket() throws IOException {
        socket.close();
    }

    public boolean userLogin(String username, String password) { // true on successful login
        LoginDetails loginDetails = new LoginDetails(username, password);
        sendData(loginDetails);
    }

    public boolean sendData(LoginDetails loginDetails) { // true on successful login
        // clientID gets assigned by the server here
    }
}
