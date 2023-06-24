import domein.AuthProvider;
import domein.AuthUser;
import junit.framework.Assert;
import org.junit.Test;
import resources.StaticLoader;

import java.net.ConnectException;

public class AuthProviderTest {
    private AuthProvider authProvider;
    @Test
    public void testCheckEmailFalse() {
        authProvider = new AuthProvider();
        String email = "random@mail";
        Assert.assertFalse(authProvider.checkEmail(email));

    }

    @Test
    public void testCheckStateChange() throws ConnectException {
        StaticLoader appProps = new StaticLoader();
        authProvider = new AuthProvider();
        authProvider.login("qwertic@qwict.com", "qwertic@qwict.com");
        Assert.assertTrue(authProvider.authenticated());
        Assert.assertEquals(authProvider.getName(), "qwertic");

    }

}
