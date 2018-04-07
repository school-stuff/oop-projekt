import services.ClientsManager;

public class Server {
    public static void main(String[] args) {
        // Definitely not a hotfix <_< >_>
        System.err.close();
        System.setErr(System.out);

        // Init manager to accept new clients
        ClientsManager.getInstance();
    }
}
