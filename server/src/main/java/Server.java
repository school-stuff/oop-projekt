import models.MatchModel;
import services.ClientsManager;

public class Server {
    public static void main(String[] args) throws InterruptedException {
        // Definitely not a hotfix <_< >_>
        System.err.close();
        System.setErr(System.out);

        // Init manager to accept new clients
        ClientsManager.getInstance();
        while (true) { // temporary solution (for testing purposes) to let clients accumulate
            Thread.sleep(1000);
            if (ClientsManager.getInstance().getClients().size() > 0) {
                break;
            }
        }
        new MatchModel(ClientsManager.getInstance().getClients());
    }
}
