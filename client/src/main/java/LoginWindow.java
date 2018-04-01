import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class LoginWindow extends Application {

    private TextField usernameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Label infoLabel = new Label();
    private Button connectButton = new Button("Connect");
    private TextField ipField = new TextField("localhost:3306");
    private ClientSocket cs = ClientSocket.getInstance();

    @Override
    public void start(Stage primaryStage) {

        connectButton.setOnAction(event -> {
            String[] ipAddress = ipField.getText().split(":");
            ClientSocket cs = ClientSocket.getInstance();

            blockFields(true);
            infoLabel.setText("Establishing connection...");
            if (cs.socketTryConnect(ipAddress[0], Integer.parseInt(ipAddress[1]))) {
                infoLabel.setText("Logging in...");
                if (cs.userLogin(usernameField.getText(), passwordField.getText())) {
                    infoLabel.setText("Directing to waiting list...");
                    if (cs.addUserToWaitList()) {
                        infoLabel.setText("Success!");
                        Platform.exit();
                    } else {
                        infoLabel.setText("Waiting list full/Game is in progress!");
                    }
                } else {
                    infoLabel.setText("Unknown username or password!");
                    blockFields(false);
                }
            } else {
                infoLabel.setText("Connection failed!");
                blockFields(false);
            }
        });

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(usernameField, 1, 1);
        grid.add(passwordField, 1, 2);
        grid.add(new Label("Username:"), 0, 1);
        grid.add(new Label("Password:"), 0, 2);
        grid.add(connectButton, 0, 3);
        grid.add(infoLabel, 1, 3);
        grid.add(ipField,0,0,2,1);
        primaryStage.setScene(new Scene(grid, 256, 180));
        primaryStage.setTitle("Log-in");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void blockFields(boolean value) {
        usernameField.setDisable(value);
        passwordField.setDisable(value);
        connectButton.setDisable(value);
        ipField.setDisable(value);
    }
}