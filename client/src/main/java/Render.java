import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Render extends Application {
    private static Render ourInstance = new Render();
    private static Stage primaryStage;

    public static Render getInstance() {
        return ourInstance;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        new LoginAndRegisterScene();

        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void showScene(Scene scene) {
        primaryStage.setScene(scene);
    }
}
