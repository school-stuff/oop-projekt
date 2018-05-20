package models;

import battleFieldMap.Maps;
import match.ItemHandler;
import match.Player;
import services.MapService;
import services.QueryHandler;
import shared.match.location.Location;
import shared.match.player.Health;
import shared.user.auth.Auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MatchModel {
    private final List<Player> players;
    private final ItemHandler itemHandler;


    private final int mapSizeX = Maps.map.length;
    private final int mapSizeY = Maps.map[0].length;

    public MatchModel(Map<Auth.LoginData, QueryHandler> clients) {
        itemHandler = new ItemHandler();
        players = new ArrayList<>();
        for (QueryHandler queryHandler : clients.values()) {
            players.add(new Player(queryHandler, itemHandler));
        }
        for (Player player : players) {
            player.updatePlayerLocation(generateFirstLocation());
            player.updatePlayerHealth(Health.HealthData.newBuilder().setHealth(100).setArmor(0).build());
        }
        updateClientDelayThread();
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

    private void updateClientDelayThread() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updatePlayers();
            }
        }).start();
    }

    private void updatePlayers() {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).updatePlayerLocation(players.get(i).getLocationRequest());
            updatePlayerOpponents(players.get(i));
        }
    }

    private void updatePlayerOpponents(Player client) {
        for (Player player : players) {
            if (!player.equals(client)) {
                if (opponentIsInFOV(client, player)) {
                    client.sendOpponentLocation(player.getLastLocation());
                }
            }
        }
    }

    private boolean opponentIsInFOV(Player player, Player opponent) {
        int playerVisionCenterX = Math.min(mapSizeX - 6, Math.max(5, player.getLastLocation().getX()));
        int playerVisionCenterY = Math.min(mapSizeY - 6, Math.max(5, player.getLastLocation().getY()));
        if (playerVisionCenterX - 6 < opponent.getLastLocation().getX() && opponent.getLastLocation().getX() < playerVisionCenterX + 6 &&
                playerVisionCenterY - 6 < opponent.getLastLocation().getY() && opponent.getLastLocation().getY() < playerVisionCenterY + 6) {
            return true;
        }
        return false;
    }

    private Location.UserLocation getRandomLocation() {
        return Location.UserLocation.newBuilder().setX((int) Math.round(Math.random() * Maps.map[0].length)).setY((int) Math.round(Math.random() * Maps.map.length)).build();
    }
}
