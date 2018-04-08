package services;

import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;
import shared.match.queue.Queue;

import java.io.IOException;
import java.util.List;

public class WaitingQueueService {
    private static WaitingQueueService ourInstance = new WaitingQueueService();
    private ServerCommunicationService server = ServerCommunicationService.getInstance();
    private ReplaySubject<List<Queue.Person>> replaySubject = ReplaySubject.create();

    public static WaitingQueueService getInstance() {
        return ourInstance;
    }

    public WaitingQueueService() {
        getServerConnection();
    }

    public Observable<List<Queue.Person>> getWaitingQueue() {
        return replaySubject;
    }

    private void gameStarted(){
        // if server sends info that game has started
        replaySubject.onComplete();
    }

    private void getServerConnection() {
        // Ask for server to start sending queue
        try {
            server.sendData("watchQuery", "matchQueue", Queue.Filters.newBuilder().build());
        } catch (IOException e) {
            // TODO: error handling
        }

        server.watchData("matchQueue").subscribe(data -> {
            Queue.MatchQueue result = (Queue.MatchQueue) data;
            replaySubject.onNext(result.getPersonsList());

            if (result.getStatus() == Queue.MatchQueue.Status.Closed) {
                replaySubject.onComplete();
            }
        });
    }
}
