
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import services.LoginService;
import services.ServerCommunicationService;

public class LoginAndRegisterScene {

    private ServerCommunicationService serverCommunicationsService =
            ServerCommunicationService.getInstance();
    private LoginService loginService = LoginService.getInstance();

    private TextField usernameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Label infoLabel = new Label();
    private Button connectButton = new Button("Connect");

    public LoginAndRegisterScene() {
        setLoginAndRegisterSceneBase();
    }

    public void setLoginAndRegisterSceneBase() {
        GridPane gridPane = createGridPane();
        Scene loginAndRegisterScene = new Scene(gridPane);
        Render.getInstance().showScene(loginAndRegisterScene);
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(usernameField, 1, 1);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(new Label("Username:"), 0, 1);
        gridPane.add(new Label("Password:"), 0, 2);
        gridPane.add(connectButton, 0, 3);
        gridPane.add(infoLabel, 1, 3);

        connectButton.setOnAction(event -> onConnectButtonClick());

        return gridPane;
    }

    private void onConnectButtonClick() {
        formSetDisable(true);
        infoLabel.setText("Establishing connection...");

        if (serverCommunicationsService.socketTryConnect()) {
            infoLabel.setText("Logging in...");

            if (loginService.authenticateUser(usernameField.getText(), passwordField.getText())) {
                displayWaitingQueue();

            } else {
                infoLabel.setText("Unknown username or password!");
                formSetDisable(false);
            }
        } else {
            infoLabel.setText("Connection to server failed!");
            formSetDisable(false);
        }
    }

    private void formSetDisable(boolean value) {
        usernameField.setDisable(value);
        passwordField.setDisable(value);
        connectButton.setDisable(value);
    }

    private void displayWaitingQueue() {
        new WaitingQueueScene();
    }
}
