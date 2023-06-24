package domein;

public class LoggedInState extends StateController {
    public LoggedInState(AuthProvider authProvider) {
        super(authProvider);
    }

    @Override
    public void login() {
        throw new IllegalStateException("You are already logged out");
    }

    @Override
    public boolean authenticated() {
        return true;
    }
}
