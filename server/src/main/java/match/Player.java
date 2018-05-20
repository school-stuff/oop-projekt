package match;

import services.QueryHandler;
import shared.match.location.Location;
import shared.match.player.Health;
import shared.match.player.Inventory;

public class Player {

    private Location.UserLocation locationRequest;
    private Location.UserLocation lastLocation;
    private Inventory.InventoryData inventoryData;
    private Health.HealthData healthData;

    private final QueryHandler playerSocket;

    public Player(QueryHandler playerSocket) {
        this.playerSocket = playerSocket;

        playerSocket.createWatchQuery("matchLocation").subscribe(data -> {
            playerSocket.sendData("watchUpdate", "matchLocation", data);
        });

        playerSocket.createWatchQuery("opponentLocation").subscribe(data -> {
            playerSocket.sendData("watchUpdate", "opponentLocation", data);
        });

        playerSocket.createWatchQuery("matchHealth").subscribe(data -> {
            playerSocket.sendData("watchUpdate", "matchHealth", data);
        });

        playerSocket.createWatchQuery("matchInventory").subscribe(data -> {
            playerSocket.sendData("watchUpdate", "matchInventory", data);
        });
    }

    public QueryHandler getPlayerSocket() {
        return playerSocket;
    }

    public void updatePlayerHealth(Health.HealthData healthData) {
        this.healthData = healthData;
        playerSocket.updateHealth(healthData);
    }

    public void updatePlayerInventory(Inventory.InventoryData inventoryData) {
        this.inventoryData = inventoryData;
        playerSocket.updateInventory(inventoryData);
    }

    public Health.HealthData getHealthData() {
        return healthData;
    }

    public Inventory.InventoryData getInventoryData() {
        return inventoryData;
    }

    public void sendOpponentLocation(Location.UserLocation location) {
        playerSocket.updateOpponentLocation(location);
    }

    public Location.UserLocation getLastLocation() {
        return lastLocation;
    }

    public Location.UserLocation getLocationRequest() {
        return locationRequest;
    }

    public void setLocationRequest(Location.UserLocation locationRequest) {
        this.locationRequest = locationRequest;
    }

    public void setLastLocation(Location.UserLocation lastLocation) {
        this.lastLocation = lastLocation;
    }
}
