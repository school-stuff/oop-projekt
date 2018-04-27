import Scenes.Render;
import javafx.application.Application;

public class Client {
    public static void main(String[] args) {
        // Definitely not a hotfix <_< >_>
        System.err.close();
        System.setErr(System.out);


        Application.launch(Render.class, args);
    }
}
