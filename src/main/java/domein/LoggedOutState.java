package domein;

public class LoggedOutState extends StateController {
    public LoggedOutState(AuthProvider authProvider) {
        super(authProvider);
    }

    @Override
    public void login() {
        System.out.printf("INFO -- LoggedOutState.login() -- you are now logged in%n");
    }

    @Override
    public boolean authenticated() {
        return false;
    }
}
