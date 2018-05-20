package services;


import com.google.protobuf.AbstractMessage;
import battlefield.BattleFieldMap;
import game.Player;
import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;
import shared.match.location.Location;
import shared.match.opponent.Alive;
import shared.match.player.Health;
import shared.match.player.Inventory;

public class GameService {
    private static GameService ourInstance = new GameService();
    private ServerCommunicationService server = ServerCommunicationService.getInstance();
<<<<<<< HEAD
    private ReplaySubject<AbstractMessage> locationReplaySubject = ReplaySubject.create();
    private ReplaySubject<AbstractMessage> opponentLocationReplaySubject = ReplaySubject.create();
    private ReplaySubject<AbstractMessage> itemReplaySubject = ReplaySubject.create();
=======
>>>>>>> aea0aba6b4d634c771bf16742cc029a5ba2a4247

    public static GameService getInstance() {
        return ourInstance;
    }

    private final ReplaySubject<AbstractMessage> locationReplaySubject = ReplaySubject.create();
    private final ReplaySubject<AbstractMessage> opponentLocationReplaySubject = ReplaySubject.create();

<<<<<<< HEAD
    public Observable<AbstractMessage> getOpponentLocation() {
        return opponentLocationReplaySubject;
    }

    public Observable<AbstractMessage> getItem() {
        return itemReplaySubject;
    }

    private void getServerConnection() {
        server.sendData("watchQuery", "matchLocation", Location.Filters.newBuilder().build());
        server.sendData("watchQuery", "opponentLocation", Location.Filters.newBuilder().build());
=======
    public GameService() {
        sendWatchQuery("matchLocation");
        sendWatchQuery("opponentLocation");
        sendWatchQuery("matchHealth");
>>>>>>> aea0aba6b4d634c771bf16742cc029a5ba2a4247

        server.watchData("matchLocation").subscribe(data -> {
            locationReplaySubject.onNext(data);
        });

        server.watchData("opponentLocation").subscribe(data -> {
            opponentLocationReplaySubject.onNext(data);
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

        server.watchData("itemData").subscribe(data -> {
            System.out.println("got item");
            itemReplaySubject.onNext(data);
        });
    }


    public Observable<AbstractMessage> getCharacterLocation() {
        return locationReplaySubject;
    }

    public Observable<AbstractMessage> getOpponentLocation() {
        return opponentLocationReplaySubject;
    }

    private void sendWatchQuery(String requestName) {
        server.sendData("watchQuery", requestName, Location.Filters.newBuilder().build());
    }

    public void sendLocationRequest(int x, int y) {
        if (BattleFieldMap.canGoToSquare(x, y)) {
            Location.UserLocation location = Location.UserLocation.newBuilder().setX(x).setY(y).build();
            server.sendData("mutation", "matchLocation", location);
        }
    }
}
