import domein.Box;
import domein.DomeinController;
import gui.components.CustomMenu;
import gui.screens.AddBoxScreen;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.Main;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import resources.ResourceController;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.testfx.api.FxToolkit;
//import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.api.FxRobot;
import org.junit.jupiter.api.BeforeEach;
import javafx.stage.Stage;


import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import static org.testfx.matcher.control.ComboBoxMatchers.hasSelectedItem;
import org.testfx.api.FxRobot;
import resources.StaticLoader;

import java.io.InputStream;
import java.util.Properties;

public class AddBoxTest {

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
    public void testAddBoxEnGetBox(){

        String name = "Box 1";
        String type = "Standard";
        double width = 10.0;
        double height = 20.0;
        double length = 30.0;
        double price = 50.0;
        boolean isActive = true;
        dc.addBox(name, type, width, height, length, price, isActive);

        ObservableList<Box> boxes = dc.getBoxes();

        boolean boxFound = false;
        for (Box box : boxes) {
            if(box.getName().equals(name)){
                boxFound = true;
                break;
            }
        }

        Assertions.assertTrue(boxFound, "The added box was found in the boxes list");

    }

    /*private Stage primaryStage;

    @BeforeAll
    public static void setUp() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Main.class);
    }

    @Test
    public void testAddNewBox() {
        FxRobot robot = new FxRobot();

        //navigeren naar AddBoxScreen
        robot.clickOn("#boxWrapper");

        robot.clickOn("#name").write("Box 1");
        robot.clickOn("#type").clickOn("Standard");
        robot.clickOn("#width").write("10");
        robot.clickOn("#height").write("20");
        robot.clickOn("#length").write("30");
        robot.clickOn("#price").write("50.0");
        robot.clickOn("#isActive").clickOn("true");
        robot.clickOn("#addButton");

        verifyThat(".dialog-pane", hasText("Success"));

        verifyThat("#name", hasText(""));
        verifyThat("#type", hasSelectedItem("Standard"));
        verifyThat("#width", hasText(""));
        verifyThat("#height", hasText(""));
        verifyThat("#length", hasText(""));
        verifyThat("#price", hasText(""));
        verifyThat("#isActive", hasSelectedItem("true"));
    }*/

}

