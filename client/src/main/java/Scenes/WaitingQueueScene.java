package Scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import services.WaitingQueueService;
import shared.match.queue.Queue;

import java.util.List;


public class WaitingQueueScene {
    private GridPane gridPane;
    private static int USERS_IN_ROW = 25;
    private int columnIndex = 0;
    private int rowIndex = 1;


    public WaitingQueueScene() {
        createAndUpdateScene();
    }

    public void setWaitingQueueSceneBase() {
        GridPane gridPane = createGridPane();
        Scene waitingQueueScene = new Scene(gridPane);
        Render.getInstance().showScene(waitingQueueScene);
    }

    private void createAndUpdateScene(){
        setWaitingQueueSceneBase();
        WaitingQueueService.getInstance().getWaitingQueue().subscribe(data -> {
            addAllWaiters(data);
        });
    }

    private void addAllWaiters(List<Queue.Person> usersWaiting) {
        if (gridPane.getChildren().size() > 1){
            gridPane.getChildren().remove(1, usersWaiting.size() + 1); // excluding welcoming text
        }
        for (int i = 0; i < usersWaiting.size(); i++) {
            if (i % 25 == 0){gridPane.addColumn(i / USERS_IN_ROW);
                columnIndex = i / USERS_IN_ROW;
                rowIndex = 1;
            }
            gridPane.add(new Label(usersWaiting.get(i).getName()), columnIndex, rowIndex);
            rowIndex++;
        }
    }
    //TODO better column-row logic

    private GridPane createGridPane() {
        this.gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setMinSize(800, 600);

        Label welcomeText = new Label("Welcome! You are waiting with users: ");
        gridPane.add(welcomeText, 0, 0);
        return gridPane;
    }
}
