import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class LoginWindow extends Application {

    private TextField usernameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Label infoLabel = new Label();
    private Button connectButton = new Button("Connect");

    @Override
    public void start(Stage primaryStage) {

        connectButton.setOnAction(event -> {
            blockFields(true);
            infoLabel.setText("Establishing connection...");
            try {
                if (establishConnection(usernameField.getText(), passwordField.getText().hashCode())) {
                    infoLabel.setText("Connection established!");
                } else {
                    infoLabel.setText("Wrong username or password!");
                    blockFields(false);
                    Platform.exit();
                }
            } catch (IOException e) {
                infoLabel.setText("Connection failed");
                blockFields(false);
            }
        });

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(usernameField, 1, 0);
        grid.add(passwordField, 1, 1);
        grid.add(new Label("Username:"), 0, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(connectButton, 0, 2);
        grid.add(infoLabel, 1, 2);
        primaryStage.setScene(new Scene(grid, 256, 128));
        primaryStage.setTitle("Log-in");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public boolean establishConnection(String username, int password) throws IOException {
        try (Socket socket = new Socket("localhost", 3306);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {

            out.writeUTF(username);
            out.writeInt(password);

            if (in.readBoolean()) {
                Client.connection(socket, out, in);
                return true;
            } else {
                return false;
            }
        }
    }

    private void blockFields(boolean value) {
        usernameField.setDisable(value);
        passwordField.setDisable(value);
        connectButton.setDisable(value);
    }
}
