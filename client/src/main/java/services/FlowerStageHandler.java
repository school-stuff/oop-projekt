package services;

import enums.FlowerLifeCycle;
import shared.match.location.Location;

public class FlowerStageHandler {
    private static FlowerStageHandler ourInstance;
    private ServerCommunicationService server = ServerCommunicationService.getInstance();
    private FlowerLifeCycle currentStage = FlowerLifeCycle.GROWING;

    public FlowerStageHandler() {
        server.sendData("watchQuery", "flowerStage", Location.Filters.newBuilder().build());

        server.watchData("flowerStage").subscribe(data -> {
            shared.match.plant.Flower.FlowerCycleChange flowerStage = (shared.match.plant.Flower.FlowerCycleChange) data;
            currentStage = FlowerLifeCycle.getCycle(flowerStage.getStage());
        });
    }

    public static FlowerStageHandler getInstance() {
        if (ourInstance == null) ourInstance = new FlowerStageHandler();
        return ourInstance;
    }


    public FlowerLifeCycle getCurrentStage() {
        return currentStage;
    }
}
