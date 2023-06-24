package gui.components;

import domein.DomeinController;
import gui.ScreenController;
import gui.company.CompanyCardComponent;
import gui.components.IconWrapperTooltipFX;
import gui.screens.*;
import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXTooltip;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import io.github.palexdev.mfxresources.fonts.fontawesome.FontAwesomeSolid;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import resources.ResourceController;

import java.util.Locale;
import java.util.ResourceBundle;

public class CustomMenu extends HBox {

    private Image delawareLogo;
    private String[] backendInfo;
    private MFXIconWrapper menuIconWrapper;
    private MFXFontIcon orderIcon;
    private MFXFontIcon transportIcon;
    private IconWrapperTooltipFX orderWrapper;
    private IconWrapperTooltipFX transportWrapper;
    private MFXFontIcon administratorIcon;
    private IconWrapperTooltipFX administratorWrapper;
    private MFXFontIcon notificationIcon;
    private MFXIconWrapper notificationWrapper;

    private MFXFontIcon customersIcon;
    private MFXIconWrapper customersWrapper;
    private MFXFontIcon boxIcon;
    private MFXIconWrapper boxWrapper;

    private MFXFontIcon logoutIcon;
    private MFXIconWrapper logoutWrapper;
    private DomeinController currentDc;
    private Color toolcolor = Color.WHITE;
    private Label label1;
    private ResourceBundle messages;

    private ComboBox<String> languageComboBox;

    public CustomMenu(BorderPane root, DomeinController dc, ResourceController rc) {
        currentDc = dc;
        HBox buttonGroup = new HBox();
        buttonGroup.setId("buttonGroup");
        buttonGroup.setSpacing(10);
        this.setId("customMenu");
        this.setAlignment(Pos.CENTER);
        buttonGroup.setAlignment(Pos.BOTTOM_RIGHT);


        delawareLogo = new Image(getClass().getResource("/images/delawareIcon.png").toExternalForm());
        ImageView menuIcon = new ImageView();
        menuIcon.setImage(delawareLogo);
        menuIcon.setFitWidth(25);
        menuIcon.setFitHeight(25);
        menuIcon.setOnMouseClicked(evt -> {
            ScreenController.changeToWelcomeScreen(root, this, currentDc, rc);
            enableAllWrappers();
        });
        menuIconWrapper = new MFXIconWrapper(menuIcon, 30);
        HBox.setHgrow(menuIconWrapper, Priority.ALWAYS);
        menuIconWrapper.setMaxWidth(Double.MAX_VALUE);
        menuIconWrapper.setPadding(new Insets(5));
//            wrapper.setBackground(new Background(new BackgroundFill(Color.BLACK, null,null)));
        menuIconWrapper.setAlignment(Pos.TOP_LEFT);
        menuIcon.setOnMouseEntered(evt -> menuIcon.setOpacity(0.7));
        menuIcon.setOnMouseExited(evt -> menuIcon.setOpacity(1));

        backendInfo = currentDc.getBackendInfo();

//        Label title = new Label(String.format("FrontEnd: %s v%s - BackEnd %s v%s", currentDc.getName(), currentDc.getVersion(), backendInfo[1], backendInfo[0]));
        Label title = new Label("delaware shipping");
        HBox.setHgrow(title, Priority.ALWAYS);
        title.setMaxWidth(Double.MAX_VALUE);
        title.setAlignment(Pos.CENTER);
        title.setPadding(new Insets(0, 0, 5, 235));
//            title.setBackground(new Background(new BackgroundFill(Color.WHITE, null,null)));

        administratorIcon = new MFXFontIcon("fas-address-book", 20, Color.WHITE);
        administratorWrapper = new IconWrapperTooltipFX(administratorIcon, 30, "Administrator");
        administratorWrapper.setPadding(new Insets(5));
        administratorWrapper.setAlignment(Pos.TOP_RIGHT);
        administratorWrapper.setOnMouseClicked(evt -> {
            ScreenController.changeToAdministratorScreen(root, this, currentDc, rc);
            clickWrapper(administratorWrapper, administratorIcon);
        });
//        accountWrapper.setVisible(false);

        notificationIcon = new MFXFontIcon("fas-bell", 20, Color.WHITE);
        notificationWrapper = new MFXIconWrapper(notificationIcon, 30);
        notificationWrapper.setPadding(new Insets(5));
        notificationWrapper.setAlignment(Pos.CENTER);

        notificationWrapper.setOnMouseClicked(evt -> {
            ScreenController.changeToNotificationScreen(root, this, currentDc, rc);
            clickWrapper(notificationWrapper, notificationIcon);
        });
        languageComboBox = new ComboBox<>();
        HBox.setHgrow(languageComboBox, Priority.ALWAYS);
        languageComboBox.getItems().addAll("en", "nl");
        languageComboBox.setValue("en");
        // voeg een listener toe aan de ComboBox om de taal te wijzigen
        languageComboBox.setOnAction(e -> switchLanguage());

        // TODO: [UI/UX] add better notifications for logout button (maybe not from materialFX)
        logoutIcon = new MFXFontIcon("fas-arrow-right-to-bracket", 20, Color.WHITE);
        logoutWrapper = new MFXIconWrapper(logoutIcon, 30);
        logoutWrapper.setPadding(new Insets(5));
        logoutWrapper.setAlignment(Pos.CENTER);
        logoutWrapper.setOnMouseClicked(evt -> {
            currentDc.logout();
            update(new DomeinController());
            ScreenController.changeToLoginScreen(root, this, new DomeinController(), new ResourceController());
            // don't allow logout button to be selected (or colored)
            enableAllWrappers();
            logoutWrapper.setDisable(true);
        });


        orderIcon = new MFXFontIcon(FontAwesomeSolid.BOX_OPEN.getDescription(), 20, Color.WHITE);
        orderWrapper = new IconWrapperTooltipFX(orderIcon, 30, "Orders");
        orderWrapper.setPadding(new Insets(5));
        orderWrapper.setAlignment(Pos.CENTER);
        orderWrapper.setOnMouseClicked(evt -> {
            ScreenController.changeToOrderScreen(root, this, currentDc, rc);
            clickWrapper(orderWrapper, orderIcon);
        });


        transportIcon = new MFXFontIcon(FontAwesomeSolid.TRUCK.getDescription(), 20, Color.WHITE);
        transportWrapper = new IconWrapperTooltipFX(transportIcon, 30, "Transport");
        transportWrapper.setPadding(new Insets(5));
        transportWrapper.setAlignment(Pos.CENTER);
        transportWrapper.setOnMouseClicked(evt -> {
            ScreenController.changeToTransportDienstScreen(root, this, currentDc, rc);
            clickWrapper(transportWrapper, transportIcon);
        });

        boxIcon = new MFXFontIcon(FontAwesomeSolid.BOX.getDescription(), 20, Color.WHITE);
        boxWrapper = new IconWrapperTooltipFX(boxIcon, 30, "Packaging");
        boxWrapper.setPadding(new Insets(5));
        boxWrapper.setAlignment(Pos.CENTER);
        boxWrapper.setOnMouseClicked(evt -> {
            ScreenController.changeToAddBoxScreen(root, this, currentDc, rc);
            clickWrapper(boxWrapper, boxIcon);
        });
        boxWrapper.setId("boxWrapper");


        customersIcon = new MFXFontIcon(FontAwesomeSolid.USERS.getDescription(), 20, Color.WHITE);
        customersWrapper = new IconWrapperTooltipFX(customersIcon, 30, "Customers");
        customersWrapper.setPadding(new Insets(5));
        customersWrapper.setAlignment(Pos.CENTER);
        customersWrapper.setOnMouseClicked(evt -> {
            ScreenController.changeToCustomerScreen(root, this, currentDc, rc);
            clickWrapper(customersWrapper, customersIcon);
        });

        HBox.setHgrow(buttonGroup, Priority.ALWAYS);
        buttonGroup.setMaxWidth(Double.MAX_VALUE);
//            buttonGroup.setBackground(new Background(new BackgroundFill(Color.BLUE, null,null)));
//        if (currentDc.getRole()!=null && currentDc.getRole().equals("admin")) {
//            administratorWrapper.setVisible(true);
//            buttonGroup.getChildren().addAll(orderWrapper, accountWrapper, notificationWrapper, logoutWrapper);
//        } else {
//            administratorWrapper.setVisible(false);
//            buttonGroup.getChildren().addAll(orderWrapper, notificationWrapper, logoutWrapper);
//        }
        administratorWrapper.setVisible(currentDc.getRole() != null && currentDc.getRole().equals("admin"));
        buttonGroup.getChildren().addAll(administratorWrapper, customersWrapper, orderWrapper, transportWrapper, boxWrapper, notificationWrapper, logoutWrapper);//customerWrapper
        buttonGroup.setVisible(currentDc.authenticated());
        getChildren().addAll(menuIconWrapper, title, buttonGroup);//languageComboBox

    }

    private void switchLanguage() {
        Locale selectedLocale;
        String selectedLanguage = languageComboBox.getValue();
        if (selectedLanguage.equals("en")) {
            selectedLocale = new Locale("en");
        } else {
            selectedLocale = new Locale("nl");
        }

        // Wijzig de taal in de hele applicatie
        LanguageBundle.setLocale(selectedLocale);

        //update taal van pagina waarop je staat
        LoginScreen.updateText();
        MainScreen.updateText();
        CompanyCardComponent.updateText();
        NotificationScreen.updateText();
        TransportDienstScreen.updateText();
        RegisterScreen.updateText();
        MyProductsScreen.updateText();

    }

    public void update(DomeinController dc) {
        currentDc = dc;
        enableAllWrappers();
        this.getChildren().get(2).setVisible(currentDc.authenticated());
        if (currentDc.getRole() != null && currentDc.getRole().equals("admin")) {
            administratorWrapper.setVisible(true);
//            buttonGroup.getChildren().addAll(orderWrapper, accountWrapper, notificationWrapper, logoutWrapper);
        } else {
            administratorWrapper.setVisible(false);
//            buttonGroup.getChildren().addAll(orderWrapper, notificationWrapper, logoutWrapper);
        }
    }

    private void enableAllWrappers() {
        orderWrapper.setDisable(false);
        orderIcon.setColor(toolcolor);
        transportWrapper.setDisable(false);
        transportIcon.setColor(toolcolor);
        administratorWrapper.setDisable(false);
        administratorIcon.setColor(toolcolor);
        notificationWrapper.setDisable(false);
        notificationIcon.setColor(toolcolor);
        logoutWrapper.setDisable(false);
        logoutIcon.setColor(toolcolor);
        boxWrapper.setDisable(false);
        boxIcon.setColor(toolcolor);
        boxWrapper.setDisable(false);
        boxIcon.setColor(toolcolor);
        customersWrapper.setDisable(false);
        customersIcon.setColor(toolcolor);

    }

    private void clickWrapper(MFXIconWrapper wrapper, MFXFontIcon icon) {
        enableAllWrappers();
        wrapper.setDisable(true);
        icon.setColor(Color.BLACK);
    }
}