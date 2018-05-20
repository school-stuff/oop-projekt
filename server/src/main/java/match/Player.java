package match;

import models.MatchModel;
import services.QueryHandler;
import shared.match.item.RenderItem;
import shared.match.location.Location;
import shared.match.player.Health;

import java.util.Set;

public class Player {

    private Location.UserLocation locationRequest;
    private Location.UserLocation lastLocation;

    private QueryHandler playerSocket;
    private ItemHandler itemHandler;

    public Player(QueryHandler playerSocket, ItemHandler itemHandler) {
        this.playerSocket = playerSocket;
        this.itemHandler = itemHandler;

        playerSocket.locationRequest().subscribe(data -> {
            locationRequest = (Location.UserLocation) data;
        });

        playerSocket.createWatchQuery("matchLocation").subscribe(data -> {
            playerSocket.sendData("watchUpdate", "matchLocation", data);
        });

        playerSocket.createWatchQuery("opponentLocation").subscribe(data -> {
            playerSocket.sendData("watchUpdate", "opponentLocation", data);
        });

        playerSocket.createWatchQuery("matchHealth").subscribe(data -> {
            playerSocket.sendData("watchUpdate", "matchHealth", data);
        });
    }

    public void updatePlayerLocation(Location.UserLocation location) {
        if (locationRequest == null) {
            lastLocation = location;
        } else {
            lastLocation = locationRequest;
        }
        if (MatchModel.canGoTo(location)) {
            locationRequest = location;
            playerSocket.updateLocation(location);
            sendRenderedItems(itemHandler.getItemsToRender(location.getX(), location.getY()));
            System.out.println("rendered items sent");
        }
    }

    public void sendRenderedItems(Set<RenderItem.ItemData> itemData) {
        for (RenderItem.ItemData item : itemData) {
            playerSocket.updateItemData(item);
        }
    }

    public void updatePlayerHealth(Health.HealthData healthData) {
        playerSocket.updateHealth(healthData);
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
}
