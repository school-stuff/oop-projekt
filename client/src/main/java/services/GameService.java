package services;

import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;
import shared.match.location.Location;

import java.io.IOException;

public class GameService {
    private static GameService ourInstance = new GameService();
    private ServerCommunicationService server = ServerCommunicationService.getInstance();
    private ReplaySubject<int[]> replaySubject = ReplaySubject.create();

    public static GameService getInstance() {
        return ourInstance;
    }

    public GameService(){
        getServerConnection();
    }

    public Observable<int[]> getCharacterLocation() {
        return replaySubject;
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
            replaySubject.onNext(location);
        });
    }
}
