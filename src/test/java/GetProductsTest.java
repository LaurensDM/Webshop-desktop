import domein.DomeinController;
import domein.Order;
import domein.Product;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import resources.StaticLoader;

import java.io.InputStream;
import java.util.Properties;

public class GetProductsTest {

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
    public void testGetProducts(){

        ObservableList<Product> products = dc.getProducts();

        Assertions.assertNotNull(products, "The list of products should not be null");


    }


}
