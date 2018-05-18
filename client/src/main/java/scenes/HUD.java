package scenes;

import enums.Item;
import game.Player;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HUD {
    private GridPane gridPane;

    public HUD() {
        gridPane = new GridPane();
        createGraphics();
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    private void createGraphics() {
        gridPane.setStyle("-fx-background-color: #222222;");
        ;
        gridPane.setPadding(new Insets(5, 5, 5, 5));

        GridPane inventoryBasePane = new GridPane();
        inventoryBasePane.setHgap(1);
        Rectangle[] invBaseRectangles = new Rectangle[6];
        for (int i = 0; i < 6; i++) {
            invBaseRectangles[i] = new Rectangle(32, 32);
            invBaseRectangles[i].setFill(Color.GRAY);
            invBaseRectangles[i].setStroke(Color.WHITE);
            inventoryBasePane.add(invBaseRectangles[i], i, 0);
        }
        Label healthLabel = new Label("Health: ");
        Label armorLabel = new Label("Armor: ");
        Label healthValue = new Label("");
        Label armorValue = new Label("");
        Label playersAliveLabel = new Label("Alive: ");
        Label playersAliveValue = new Label("");

        healthLabel.setTextFill(Color.WHITE);
        armorLabel.setTextFill(Color.WHITE);
        playersAliveLabel.setTextFill(Color.WHITE);
        healthValue.setTextFill(Color.LIGHTPINK);
        armorValue.setTextFill(Color.LIGHTBLUE);
        playersAliveValue.setTextFill(Color.LIMEGREEN);
        healthValue.setMinWidth(50);
        armorValue.setMinWidth(50);

        Player.health.subscribe(data -> {
            Platform.runLater(() -> healthValue.setText(data.toString()));
        });

        Player.armor.subscribe(data -> {
            Platform.runLater(() -> armorValue.setText(data.toString()));
        });

        Player.opponentsAlive.subscribe(data -> {
            Platform.runLater(() -> playersAliveValue.setText(data.toString()));
        });

        Player.slotEquipped.subscribe(data -> {
            for (Rectangle invBaseRectangle : invBaseRectangles) {
                invBaseRectangle.setFill(Color.GRAY);
            }
            invBaseRectangles[data].setFill(Color.web("#C7C7C7"));
        });

        Player.inventory.subscribe(data -> {
            for (int i = 0; i < 6; i++) {
                if (Item.fromId(data[i]).getId() != 0) {
                    ImageView imageView = new ImageView(Item.fromId(data[i]).getImage());
                    inventoryBasePane.add(imageView, i, 0);
                }
            }
        });

        gridPane.add(healthLabel, 0, 0);
        gridPane.add(healthValue, 1, 0);
        gridPane.add(armorLabel, 2, 0);
        gridPane.add(armorValue, 3, 0);
        gridPane.add(inventoryBasePane, 4, 0);
        gridPane.add(playersAliveLabel, 5, 0);
        gridPane.add(playersAliveValue, 6, 0);
    }
}
