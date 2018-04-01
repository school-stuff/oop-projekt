public class LoginDetails {
    private final String username;
    private final String password;

    public LoginDetails(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public int hashCode() {
        // would this be a good idea to add to ./shared ?
        // https://github.com/defuse/password-hashing
    }
}
