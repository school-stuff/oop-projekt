package match;

import models.MatchModel;
import services.QueryHandler;
import shared.match.location.Location;

public class Player {

    private Location.UserLocation locationRequest;
    private Location.UserLocation lastLocation;

    private QueryHandler playerSocket;

    public Player(QueryHandler playerSocket) {
        this.playerSocket = playerSocket;

        playerSocket.locationRequest().subscribe(data -> {
            locationRequest = (Location.UserLocation) data;
        });

        playerSocket.createWatchQuery("matchLocation").subscribe(data -> {
            playerSocket.sendData("watchUpdate", "matchLocation", data);
        });
    }

    public QueryHandler getPlayerSocket() {
        return playerSocket;
    }

    public void updatePlayerLocation(Location.UserLocation location) {
        if (locationRequest == null) {
            lastLocation = location;
        } else {
            lastLocation = locationRequest;
        }
        if (MatchModel.canGoTo()) {
            locationRequest = location;
            playerSocket.updateLocation(location);
            System.out.println(location);
        }
    }

    public void sendOpponentLocation(Location.UserLocation location) {
        playerSocket.sendData("watchUpdate", "opponentLocation", location);
    }

    public Location.UserLocation getLastLocation() {
        return lastLocation;
    }

    public Location.UserLocation getLocationRequest() {
        return locationRequest;
    }
}
