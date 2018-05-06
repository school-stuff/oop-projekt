package services;

import game.Player;
import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;
import shared.match.location.Location;
import shared.match.opponent.Alive;
import shared.match.player.Health;
import shared.match.player.Inventory;

import java.io.IOException;

public class GameService {
    private static GameService ourInstance = new GameService();
    private ServerCommunicationService server = ServerCommunicationService.getInstance();
    private ReplaySubject<int[]> locationReplaySubject = ReplaySubject.create();

    public static GameService getInstance() {
        return ourInstance;
    }

    public GameService() {
        getServerConnection();
    }

    public Observable<int[]> getCharacterLocation() {
        return locationReplaySubject;
    }

    private void getServerConnection() {
        try {
            server.sendData("watchQuery", "matchLocation", Location.Filters.newBuilder().build());
        } catch (IOException e) {
            // TODO: error handling
        }

        server.watchData("matchLocation").subscribe(data -> {
            int[] location = new int[2];
            Location.LocationData result = (Location.LocationData) data;
            location[0] = result.getUserLocation().getX();
            location[1] = result.getUserLocation().getY();
            locationReplaySubject.onNext(location);
        });

        server.watchData("matchHealth").subscribe(data -> {
            Health.HealthData result = (Health.HealthData) data;
            Player.health.onNext(result.getHealth());
            Player.armor.onNext(result.getArmor());
        });

        server.watchData("matchInventory").subscribe(data -> {
            Inventory.InventoryData result = (Inventory.InventoryData) data;
            int[] inventory = new int[6];
            inventory[0] = result.getItems().getFirstItemId();
            inventory[1] = result.getItems().getSecondItemId();
            inventory[2] = result.getItems().getThirdItemId();
            inventory[3] = result.getItems().getFourthItemId();
            inventory[4] = result.getItems().getFifthItemId();
            inventory[5] = result.getItems().getSixthItemId();
            Player.inventory.onNext(inventory);
        });

        server.watchData("matchOpponentAlive").subscribe(data -> {
            Alive.AliveData result = (Alive.AliveData) data;
            Player.opponentsAlive.onNext(result.getPlayersAlive());
        });
    }

    public void sendLocationRequest(Location.UserLocation location) throws IOException {
        server.sendData("?", "?", location);
    }
}
