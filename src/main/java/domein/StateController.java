package domein;

public abstract class StateController {

    protected AuthProvider authProvider;

    public StateController(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    public boolean authenticated() {
        return false;
    }

    public void login() {
        throw new IllegalStateException("You are already logged in");
    }

    public void logout() {
        System.out.println("You have logged out");
    }
}
