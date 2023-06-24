package main;

import domein.DomeinController;
import gui.components.CustomMenu;
import gui.screens.LoginScreen;
import gui.ScreenController;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import resources.ResourceController;
import resources.StaticLoader;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Properties;

public class Main extends Application {
    public static final Properties appProps = new Properties();
    public static final Properties settingsProps = new Properties();

    @Override
    public void start(Stage primaryStage) {
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
        System.out.println("INFO -- JavaServerMain -- main -- Loading settings.properties");
        try (InputStream resourceStream = loader.getResourceAsStream("settings.properties")) {
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

//            String translation = ResourceController.getTaalPakket("LanguageSelection");
//            System.out.println(translation);
            primaryStage.setOnCloseRequest(evt -> System.exit(0));
            SecureRandom sr = new SecureRandom();
            DomeinController dc = new DomeinController();
            ResourceController rc = new ResourceController();
            BorderPane root = new BorderPane();
//            if (settingsProps.getProperty("user.stayLoggedIn") == "true") {
//                dc.validatePropertiesToken();
//            } else {
//                System.out.println(settingsProps.getProperty("user.stayLoggedIn").getClass());
//            }
            CustomMenu menu = new CustomMenu(root, dc, rc);
            LoginScreen screen = new LoginScreen(root, menu, dc, rc);
            root.getStyleClass().add("rootPane");

            root.setCenter(screen);
            Scene scene = new Scene(root, 1366, 768);
            MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
            scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("delaware shipping");
            root.setTop(menu);
            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                primaryStage.initStyle(javafx.stage.StageStyle.UNIFIED);
                primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/delawareIcon.png")));

                // Because Apple is special; you need a special way to set the icon
                final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
                java.awt.Image image = defaultToolkit.getImage(getClass().getResource("/images/delawareIcon.png"));
                final Taskbar taskbar = Taskbar.getTaskbar();

                taskbar.setIconImage(image);
            } else {
//                primaryStage.initStyle(javafx.stage.StageStyle.UNIFIED);
                primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/delawareIcon.png")));
//                primaryStage.setMaximized(true);
            }
            primaryStage.show();

//            rc.playMusic();
            ScreenController.screenWidth = (int) screen.getScene().getWidth();
//		rowMultiplier = screen.getScene().getWidth()/screen.getScene().getHeight();
            MediaView mediaview = new MediaView(rc.getMediaplayer());
            ((BorderPane) scene.getRoot()).getChildren().add(mediaview);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }


    public static void saveProperties() {
        try {
            Main.settingsProps.store(new FileOutputStream("settings.properties"), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}