public class ServerConnectionService <T, K> {
    private static ServerConnectionService ourInstance = new ServerConnectionService();

    public static ServerConnectionService getInstance() {
        return ourInstance;
    }

    public void sendData(T data) {
        // sends data to server
    }

    public K getData(String requestData) {
        // gets requested data from server
        return null;
    }
}
