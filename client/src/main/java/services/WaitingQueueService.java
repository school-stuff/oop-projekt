package services;

import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;

public class WaitingQueueService {
    private static WaitingQueueService ourInstance = new WaitingQueueService();
    private ServerCommunicationService server = ServerCommunicationService.getInstance();
    private ReplaySubject<DataInterface> replaySubject = ReplaySubject.create();

    public static WaitingQueueService getInstance() {
        return ourInstance;
    }

    public Observable<DataInterface> getWaitingQueue() {
        getServerConnection();
        return replaySubject;
    }

    public void gameStarted(){
        server.getData("correctTypeData").subscribe(data -> {
            // if server sends info that game has started
            replaySubject.onComplete();
        });
    }

    private void getServerConnection() {
        server.getData("correctTypeData").subscribe(data -> {
            replaySubject.onNext(data);
        });
    }
}
