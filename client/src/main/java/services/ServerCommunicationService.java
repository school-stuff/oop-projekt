package services;

import io.reactivex.Observable;

public class ServerCommunicationService {
    private static ServerCommunicationService ourInstance = new ServerCommunicationService();

    public static ServerCommunicationService getInstance() {
        return ourInstance;
    }

    public void sendData(DataInterface data) {
        // sends data to server
    }

    public Observable<DataInterface> getData(String requestData) {
        // gets requested data from server
        return null;
    }
}


