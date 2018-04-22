package Scenes;

import io.reactivex.disposables.Disposable;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import services.LoginService;
import services.ServerCommunicationService;

import java.io.IOException;

public class LoginAndRegisterScene {

    // TODO: Register elements

    private ServerCommunicationService serverCommunicationsService =
            ServerCommunicationService.getInstance();
    private LoginService loginService = LoginService.getInstance();

    private TextField emailField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Label infoLabel = new Label();
    private Button connectButton = new Button("Connect");
    private Disposable isConnectedSubscription;

    public LoginAndRegisterScene() {
        setLoginAndRegisterSceneBase();
    }

    public void setLoginAndRegisterSceneBase() {
        formSetDisable(true);
        infoLabel.setText("Connecting to server...");
        GridPane gridPane = createGridPane();
        Scene loginAndRegisterScene = new Scene(gridPane);
        Render.getInstance().showScene(loginAndRegisterScene);
        isConnectedSubscription = serverCommunicationsService.isConnected.subscribe(isConnected -> {
            if (isConnected) {
                Platform.runLater(() -> infoLabel.setText("")); // Solution to a common JavaFX threading issue
                formSetDisable(false);
                isConnectedSubscription.dispose();
            }
        });
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(emailField, 1, 1);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(new Label("email:"), 0, 1);
        gridPane.add(new Label("Password:"), 0, 2);
        gridPane.add(connectButton, 0, 3);
        gridPane.add(infoLabel, 1, 3);

        connectButton.setOnAction(event -> {
            try {
                onConnectButtonClick();
            } catch (IOException e) {
                formSetDisable(false);
            }
        });

        return gridPane;
    }

    private void onConnectButtonClick() throws IOException {
        formSetDisable(true);
        infoLabel.setText("Establishing connection...");

        if (serverCommunicationsService.socketTryConnect()) {
            infoLabel.setText("Logging in...");

            loginService.authenticateUser(emailField.getText(), passwordField.getText()).subscribe(isSuccess -> {
                if (isSuccess) {
                    displayWaitingQueue(); // switches login screen render scene to waiting queue

                } else {
                    infoLabel.setText("Unknown email or password!");
                    formSetDisable(false);
                }
            });
        } else {
            infoLabel.setText("Connection to server failed!");
            formSetDisable(false);
        }
    }

    private void formSetDisable(boolean value) {
        emailField.setDisable(value);
        passwordField.setDisable(value);
        connectButton.setDisable(value);
    }

    private void displayWaitingQueue() {
        new WaitingQueueScene();
    }
}
