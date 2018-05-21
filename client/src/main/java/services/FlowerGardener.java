package services;

import enums.FlowerLifeCycle;
import shared.match.location.Location;
import shared.match.plant.Flower;

public class FlowerGardener {
    private static FlowerGardener ourInstance;
    private ServerCommunicationService server = ServerCommunicationService.getInstance();
    private FlowerLifeCycle currentStage = FlowerLifeCycle.GROWING;

    public FlowerGardener() {
        server.sendData("watchQuery", "flowerStage", Location.Filters.newBuilder().build());

        server.watchData("flowerStage").subscribe(data -> {
            Flower.FlowerCycleChange flowerStage = (Flower.FlowerCycleChange) data;
            currentStage = FlowerLifeCycle.getCycle(flowerStage.getStage());
        });
    }

    public static FlowerGardener getInstance() {
        if (ourInstance == null) ourInstance = new FlowerGardener();
        return ourInstance;
    }


    public FlowerLifeCycle getCurrentStage() {
        return currentStage;
    }
}
