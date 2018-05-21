package models;

import battleFieldMap.Maps;
import enums.Item;
import match.ItemHandler;
import match.Player;
import services.MapService;
import services.QueryHandler;
import shared.match.location.Location;
import shared.match.player.Action;
import shared.match.player.Health;
import shared.match.player.Inventory;
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
            updatePlayerLocation(player, generateFirstLocation(player));
            player.updatePlayerInventory(Inventory.InventoryData.newBuilder().build());
            player.updatePlayerHealth(Health.HealthData.newBuilder().setHealth(100).setArmor(0).build());
            createObservers(player);
        }
        updateClientDelayThread();
    }

    private void createObservers(Player player) {
        player.getPlayerSocket().locationRequest().subscribe(data -> {
            player.setLocationRequest((Location.UserLocation) data);
        });

        player.getPlayerSocket().actionRequest().subscribe(data -> {
            commitAction(player, (Action.ActionData) data);
        });

    }

    private Location.UserLocation generateFirstLocation(Player player) {
        Location.UserLocation firstLocation = getRandomLocation();
        while (!canGoTo(player, firstLocation)) {
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
        for (Player player : players) {
            updatePlayerLocation(player, player.getLocationRequest());
            updatePlayerOpponents(player);

            player.sendRenderedItems(itemHandler.getItemsToRender(player.getLocationRequest().getX(),
                    player.getLocationRequest().getY()));
        }
    }

    private void updatePlayerLocation(Player player, Location.UserLocation location) {
        if (player.getLocationRequest() == null) {
            player.setLastLocation(location);
        } else {
            player.setLastLocation(player.getLocationRequest());
        }
        if (canGoTo(player, location)) {
            player.setLocationRequest(location);
            player.getPlayerSocket().updateLocation(location);
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

    private boolean canGoTo(Player player, Location.UserLocation location) {
        if (MapService.canGoToLocation(location.getX(), location.getY()) &&
                opponentOnSquareNextMove(player, location.getX(), location.getY()) == null) {
            return true;
        }
        return false;
    }

    private Player opponentOnSquareNextMove(Player player, int x, int y) {
        for (Player opponent : players) {
            if (!opponent.equals(player) && opponent.getLocationRequest() != null) {
                if (opponent.getLocationRequest().getX() == x &&
                        opponent.getLocationRequest().getY() == y) {
                    return opponent;
                }
            }
        }
        return null;
    }

    private Player opponentOnSquareCurrentMove(Player player, int x, int y) {
        for (Player opponent : players) {
            if (!opponent.equals(player) && opponent.getLastLocation() != null) {
                if (opponent.getLastLocation().getX() == x &&
                        opponent.getLastLocation().getY() == y) {
                    return opponent;
                }
            }
        }
        return null;
    }

    private void commitAction(Player player, Action.ActionData data) {
        int equippedItemId = 0;
        switch (data.getEquippedSlot()) {
            case (0):
                equippedItemId = player.getInventoryData().getItems().getFirstItemId();
                break;
            case (1):
                equippedItemId = player.getInventoryData().getItems().getSecondItemId();
                break;
            case (2):
                equippedItemId = player.getInventoryData().getItems().getThirdItemId();
                break;
            case (3):
                equippedItemId = player.getInventoryData().getItems().getFourthItemId();
                break;
            case (4):
                equippedItemId = player.getInventoryData().getItems().getFifthItemId();
                break;
            case (5):
                equippedItemId = player.getInventoryData().getItems().getSixthItemId();
                break;
        }
        if (Item.fromId(equippedItemId).getType().equals("Consumable")) {
            // TODO: consume the consumable
            return;
        }
        Player target;
        if ((target = opponentOnSquareCurrentMove(player, player.getLastLocation().getX() + data.getX(), player.getLastLocation().getY() + data.getY())) != null) {
            int targetHealth = target.getHealthData().getHealth();
            int targetArmor = target.getHealthData().getArmor();
            targetHealth -= Item.fromId(equippedItemId).getBaseDamage();
            target.updatePlayerHealth(Health.HealthData.newBuilder()
                    .mergeFrom(target.getHealthData())
                    .setHealth(targetHealth).build());
        } else if (Item.fromId(equippedItemId).getType().equals("Utility")) {
            // TODO: use utility
        }
    }

    private boolean opponentIsInFOV(Player player, Player opponent) {
        int playerVisionCenterX = Math.min(mapSizeX - 6, Math.max(5, player.getLastLocation().getX()));
        int playerVisionCenterY = Math.min(mapSizeY - 6, Math.max(5, player.getLastLocation().getY()));
        return playerVisionCenterX - 6 < opponent.getLastLocation().getX() && opponent.getLastLocation().getX() < playerVisionCenterX + 6 &&
                playerVisionCenterY - 6 < opponent.getLastLocation().getY() && opponent.getLastLocation().getY() < playerVisionCenterY + 6;
    }

    private Location.UserLocation getRandomLocation() {
        return Location.UserLocation.newBuilder().setX((int) Math.round(Math.random() * Maps.map[0].length)).setY((int) Math.round(Math.random() * Maps.map.length)).build();
    }


}
