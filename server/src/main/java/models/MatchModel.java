package models;

import battleFieldMap.Maps;
import match.Player;
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
        updateClientDelayThread();
    }

    private Location.UserLocation generateFirstLocation() {
        Location.UserLocation firstLocation = getRandomLocation();
        while (!canGoTo()) {
            firstLocation = getRandomLocation();
        }
        return firstLocation;
    }

    private void updateClientDelayThread() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (players.size() > 1) {
                    for (int i = 0; i < players.size(); i++) {
                        players.get(i).updatePlayerLocation(players.get(i).getLocationRequest());

                        List<Player> playersWithoutThemselves = new ArrayList<>();
                        playersWithoutThemselves.addAll(players.subList(0, i));
                        playersWithoutThemselves.addAll(players.subList(i + 1, players.size()));

                        for (Player opponent : playersWithoutThemselves) {
                            players.get(i).sendOpponentLocation(opponent.getLastLocation());
                        }
                    }
                } else {
                    players.get(0).updatePlayerLocation(players.get(0).getLocationRequest());
                }
            }
        }).start();
    }

    private Location.UserLocation getRandomLocation() {
        return Location.UserLocation.newBuilder().setX((int) Math.round(Math.random() * Maps.map[0].length)).setY((int) Math.round(Math.random() * Maps.map.length)).build();
    }

    public static boolean canGoTo() {
        //TODO: method body
        return true;
    }
}
