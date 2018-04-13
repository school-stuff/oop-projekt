package services;

import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;
import shared.match.queue.Queue;

import java.io.IOException;

public class GameService {
    private static GameService ourInstance = new GameService();
    private ServerCommunicationService server = ServerCommunicationService.getInstance();
    private ReplaySubject<String> replaySubject = ReplaySubject.create();

    public static GameService getInstance() {
        return ourInstance;
    }

    public Observable<String> getCharacterLocation() {
        return replaySubject;
    }

    private void getServerConnection() {
        try {
            server.sendData("watchQuery", "locationqueue", Queue.Filters.newBuilder().build());
        } catch (IOException e) {
            // TODO: error handling
        }

        server.watchData("locationqueue").subscribe(data -> {
            // TODO: add case in ServerCommunicationService
        });
    }
}
