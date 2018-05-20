package services;

import enums.FlowerLifeCycle;
import shared.match.location.Location;

public class FlowerService {
    private static FlowerService ourInstance;
    private ServerCommunicationService server = ServerCommunicationService.getInstance();
    private FlowerLifeCycle currentStage = FlowerLifeCycle.GROWING;

    public FlowerService() {
        getServerConnection();
    }

    public static FlowerService getInstance() {
        if (ourInstance == null) ourInstance = new FlowerService();
        return ourInstance;
    }

    private void getServerConnection() {
        server.sendData("watchQuery", "flowerStage", Location.Filters.newBuilder().build());

        server.watchData("flowerStage").subscribe(data -> {
            shared.match.plant.Flower.FlowerCycleChange flowerStage = (shared.match.plant.Flower.FlowerCycleChange) data;
            currentStage = FlowerLifeCycle.getCycle(flowerStage.getStage());
        });
    }

    public FlowerLifeCycle getCurrentStage() {
        return currentStage;
    }
}
