import domein.DomeinController;
import domein.TransportDienst;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import resources.StaticLoader;

import java.io.InputStream;
import java.io.InputStream;
import java.util.Properties;

public class AddTransportDienstTest {

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
    public void testAddTransportDienstEnGetTransportDienst(){

        String name = "TransportDienst 1";
        String phoneNumbers = "0477777777, 6546876424";
        String emailAddresses = "test@gmail.com, test2@gmail.com";
        boolean isActive = true;

        dc.addTransportService(name, phoneNumbers, emailAddresses, isActive);

        ObservableList<TransportDienst> transportDiensten = dc.getTransportServices();

        boolean transportDienstFound = false;
        for (TransportDienst transportDienst : transportDiensten) {
            if(transportDienst.getName().equals(name)){
                transportDienstFound = true;
                break;
            }
        }

        Assertions.assertTrue(transportDienstFound, "The added transportDienst was found in the transportDiensten list");

    }

}
