import domein.DomeinController;
import domein.Order;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import resources.StaticLoader;
import org.junit.Test;

import java.io.InputStream;
import java.util.Properties;

public class GetOrdersTest {

    private DomeinController dc;

    public static final Properties appProps = new Properties();

    @Before
    public void setUp(){
        dc = new DomeinController();

        StaticLoader sl = new StaticLoader();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println("INFO -- JavaServerMain -- main -- Loading application.properties");
        try (InputStream resourceStream = loader.getResourceAsStream("application.properties")) {
            appProps.load(resourceStream);
        } catch (Exception e) {
            System.out.println("ERROR -- UserServiceImpl -- UserServiceImpl -- Could not load application.properties;" +
                    "make sure it exists in /resources/application.properties" + e.getMessage());
            System.exit(1);
        }

    }

    @Test
    public void testGetOrders(){

        ObservableList<Order> orders = dc.getOrders();

        Assertions.assertNotNull(orders, "The list of orders should not be null");


    }

}
