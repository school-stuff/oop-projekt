import javafx.application.Application;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Application.launch(LoginWindow.class, args);
    }
    public static void connection(Socket socket, DataOutputStream out, DataInputStream in) {

    }
}
