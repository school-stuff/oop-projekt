package models;

import battleFieldMap.Maps;
import match.Player;
import services.MapService;
import services.QueryHandler;
import shared.match.location.Location;
import shared.user.auth.Auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MatchModel {
    private final List<Player> players;

    public MatchModel(Map<Auth.LoginData, QueryHandler> clients) {
        players = new ArrayList<>();
        for (QueryHandler queryHandler : clients.values()) {
            players.add(new Player(queryHandler));
        }
        for (Player player : players) {
            player.updatePlayerLocation(generateFirstLocation());
        }
    }

    public static boolean canGoTo(Location.UserLocation location) {
        return MapService.canGoToLocation(location.getX(), location.getY());
    }

    private Location.UserLocation generateFirstLocation() {
        Location.UserLocation firstLocation = getRandomLocation();
        while (!canGoTo(firstLocation)) {
            firstLocation = getRandomLocation();
        }
        return firstLocation;
    }

    private Location.UserLocation getRandomLocation() {
        return Location.UserLocation.newBuilder().setX((int) Math.round(Math.random() * Maps.map[0].length)).setY((int) Math.round(Math.random() * Maps.map.length)).build();
    }
}
