import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Render extends Application {
    private static Render ourInstance = new Render();
    private static Stage primaryStage;

    public static Render getInstance() {
        return ourInstance;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        // TODO: open user login scene
        // first scene user login screen, if user has registered;
        // that scene opens WaitingQueueScene;
        // if game starts renders battlefield
        new WaitingQueueScene();
        primaryStage.show();
    }

    public void showScene(Scene scene) {
        primaryStage.setScene(scene);
    }
}
