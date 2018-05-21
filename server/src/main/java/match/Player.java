package match;

import services.QueryHandler;
import shared.match.item.RenderItem;
import shared.match.location.Location;
import shared.match.player.Health;
import shared.match.player.Inventory;

import java.util.Set;

public class Player {

    private Location.UserLocation locationRequest;
    private Location.UserLocation lastLocation;
    private Inventory.InventoryData inventoryData;
    private Health.HealthData healthData;

    private QueryHandler playerSocket;

    public Player(QueryHandler playerSocket, ItemHandler itemHandler) {
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

        playerSocket.createWatchQuery("itemData").subscribe(data -> {
            playerSocket.sendData("watchUpdate", "itemData", data);
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

    public void sendRenderedItems(Set<RenderItem.ItemData> itemData) {
        for (RenderItem.ItemData item : itemData) {
            playerSocket.updateItemData(item);
        }
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
