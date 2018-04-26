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
    private ServerCommunicationService serverCommunicationsService =
            ServerCommunicationService.getInstance();
    private LoginService loginService = LoginService.getInstance();

    private TextField emailField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Label infoLabel = new Label();
    private Button loginButton = new Button("Login");
    private Button registerButton = new Button("Register");

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
                if (isConnectedSubscription != null) {
                    isConnectedSubscription.dispose();
                }
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
        gridPane.add(loginButton, 0, 3);
        gridPane.add(infoLabel, 1, 4);
        gridPane.add(registerButton, 1, 3);

        loginButton.setOnAction(event -> {
            try {
                onLoginButtonClick();
            } catch (IOException e) {
                formSetDisable(false);
            }
        });

        registerButton.setOnAction(event -> {
            try {
                onRegisterButtonClick();
            } catch (IOException e) {
                formSetDisable(false);
            }
        });

        return gridPane;
    }

    private void onLoginButtonClick() throws IOException {
        if (!emailMatchesRegEx(emailField.getText())) {
            return;
        }
        formSetDisable(true);
        infoLabel.setText("Logging in...");
        loginService.authenticateUser(emailField.getText(), passwordField.getText()).subscribe(isSuccess -> {
            if (isSuccess) {
                displayWaitingQueue();
            } else {
                infoLabel.setText("Unknown email or password!");
                formSetDisable(false);
            }
        });
    }

    private void onRegisterButtonClick() throws IOException {
        if (!emailMatchesRegEx(emailField.getText())) {
            return;
        }
        formSetDisable(true);
        infoLabel.setText("Register in process...");
        loginService.createUser(emailField.getText(), passwordField.getText()).subscribe(isSuccess -> {
            if (isSuccess) {
                infoLabel.setText("Account created!");
            } else {
                infoLabel.setText("Account could not be created!");
            }
            formSetDisable(false);
        });
    }

    private boolean emailMatchesRegEx(String value) {
        String regEx = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        if (!value.matches(regEx)) {
            infoLabel.setText("Invalid email");
            return false;
        }
        return true;
    }

    private void formSetDisable(boolean value) {
        emailField.setDisable(value);
        passwordField.setDisable(value);
        loginButton.setDisable(value);
        registerButton.setDisable(value);
    }

    private void displayWaitingQueue() {
        new WaitingQueueScene();
    }
}
