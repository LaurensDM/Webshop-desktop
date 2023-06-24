package domein;

import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {
    private Properties properties;

    public PropertyLoader() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String loadVersion() {
        return properties.getProperty("application.version");
    }

    public String loadName() {
        return properties.getProperty("application.name");
    }

}
