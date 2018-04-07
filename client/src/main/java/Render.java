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


        primaryStage.show();
    }

    public void showScene(Scene scene) {
        primaryStage.setScene(scene);
    }
}
