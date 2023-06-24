package resources;

import java.util.Properties;

public class StaticLoader {
    public static final Properties appProps = new Properties();
    public static final Properties settingsProps = new Properties();

    public StaticLoader() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println("INFO -- JavaServerMain -- main -- Loading application.properties");
        try (java.io.InputStream resourceStream = loader.getResourceAsStream("application.properties")) {
            appProps.load(resourceStream);
        } catch (Exception e) {
            System.out.println("ERROR -- UserServiceImpl -- UserServiceImpl -- Could not load application.properties;" +
                    "make sure it exists in /resources/application.properties" + e.getMessage());
            System.exit(1);
        }
        System.out.println("INFO -- JavaServerMain -- main -- Loading settings.properties");
        try (java.io.InputStream resourceStream = loader.getResourceAsStream("settings.properties")) {
            settingsProps.load(resourceStream);
        } catch (Exception e) {
            System.out.println("ERROR -- UserServiceImpl -- UserServiceImpl -- Could not load settings.properties;" +
                    "make sure it exists in /resources/settings.properties" + e.getMessage());
            System.exit(1);
        }
        try {
            if (settingsProps.getProperty("user.languagePack") != null) {
                ResourceController.setLanguagePack(settingsProps.getProperty("user.languagePack"));
            } else {
                ResourceController.setLanguagePack("LanguagePack_nl_NL");
            }
        } catch (Exception e) {
            System.out.println("ERROR -- UserServiceImpl -- UserServiceImpl -- Could not load language pack;" +
                    "make sure it exists in /resources/" + settingsProps.getProperty("user.languagePack") + ".properties" + e.getMessage());
            System.exit(1);
        }
    }
}
