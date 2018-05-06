package models;

import battleFieldMap.Maps;
import services.QueryHandler;
import shared.match.location.Location;

public class MatchModel {
    private final QueryHandler queryHandler;

    public MatchModel(QueryHandler queryHandler) {
        this.queryHandler = queryHandler;
        subscribeToLocationRequests();
        sendPlayerLocation(generateFirstLocation());
    }


    private void sendPlayerLocation(Location.UserLocation location) {
        if (canGoTo()) {
            queryHandler.sendData("watchUpdate", "matchLocation", location);
        }
    }

    private void subscribeToLocationRequests() {
        queryHandler.getPlayerLocation().subscribe(data -> {
            Location.UserLocation location = (Location.UserLocation) data;
            sendPlayerLocation(location);
        });
    }

    private Location.UserLocation generateFirstLocation() {
        Location.UserLocation firstLocation = getRandomLocation();
        while (!canGoTo()) {
            firstLocation = getRandomLocation();
        }
        return firstLocation;
    }

    private Location.UserLocation getRandomLocation() {
        return Location.UserLocation.newBuilder().setX((int) Math.round(Math.random() * Maps.map[0].length)).setY((int) Math.round(Math.random() * Maps.map.length)).build();
    }

    private boolean canGoTo() {
        //TODO: method body
        return true;
    }
}
