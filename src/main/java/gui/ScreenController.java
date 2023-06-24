package gui;

import domein.DomeinController;
import gui.components.CustomMenu;
import gui.screens.*;
import javafx.scene.layout.*;
import resources.ResourceController;

/**
 * The type Screen controller.
 */
public class ScreenController {

    /**
     * The constant screenWidth.
     */
    public static int screenWidth;

    /**
     * Change to welcome screen.
     *
     * @param screen,
     * @param dc,
     * @param rs
     */
    public static void changeToWelcomeScreen(BorderPane screen, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        screen.setCenter(new MainScreen(screen, navbar, dc, rs));
    }

    /**
     * Change to welcome screen.
     *
     * @param screen,
     * @param dc,
     * @param rs
     */
    public static void changeToLoginScreen(BorderPane screen, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        LoginScreen newScreen = new LoginScreen(screen, navbar, dc, rs);
        screen.setCenter(newScreen);
    }

    public static void changeToAddEmployeeScreen(BorderPane screen, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        AddEmployeeScreen newScreen = new AddEmployeeScreen(screen, navbar, dc, rs);
        screen.setCenter(newScreen);
    }

    public static void changeToProductScreen(BorderPane screen, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        screen.setCenter(new MyProductsScreen(screen, navbar, dc, rs));
    }


    public static void changeToRegisterScreen(BorderPane screen, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        screen.setCenter(new RegisterScreen(screen, navbar, dc, rs));
    }

    public static void changeToNotificationScreen(BorderPane screen, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        screen.setCenter(new NotificationScreen(screen, navbar, dc, rs));
    }

    /**
     * Change to account screen.
     *
     * @param screen,
     * @param dc,
     * @param rs
     */
    public static void changeToAdministratorScreen(BorderPane screen, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        screen.setCenter(new AdministratorScreen(screen, navbar, dc, rs));
    }

    public static void changeToOrderScreen(BorderPane screen, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        screen.setCenter(new OrderScreen(screen, navbar, dc, rs));
    }

    public static void changeToMainScreen(BorderPane screen, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        screen.setCenter(new MainScreen(screen, navbar, dc, rs));
    }

    public static void changeToTransportDienstScreen(BorderPane screen, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        screen.setCenter(new TransportDienstScreen(screen, navbar, dc, rs));
    }

    public static void changeToAddTransportDienstScreen(BorderPane screen, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        screen.setCenter(new addTransportDienst(screen, navbar, dc, rs));
    }

    public static void changeToOrderDetailScreen(BorderPane screen, CustomMenu navbar, DomeinController dc, ResourceController rs, String orderId) {
        screen.setCenter(new OrderDetailScreen(screen, navbar, dc, rs, orderId));
    }

    public static void changeToBoxScreen(BorderPane screen, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        screen.setCenter(new BoxScreen(screen, navbar, dc, rs));
    }

    public static void changeToAddBoxScreen(BorderPane screen, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        screen.setCenter(new AddBoxScreen(screen, navbar, dc, rs));
    }


    public static void changeToSettingScreen(BorderPane screen, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        screen.setCenter(new SettingScreen(screen, navbar, dc, rs));
    }

    public static void changeToCustomerScreen(BorderPane screen, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        screen.setCenter(new CustomerScreen(screen, navbar, dc, rs));
    }

}

