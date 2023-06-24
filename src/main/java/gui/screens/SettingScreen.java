package gui.screens;

import domein.DomeinController;
import gui.company.CompanyCardComponent;
import gui.components.CustomMenu;
import gui.components.LanguageBundle;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXSlider;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import resources.ResourceController;

import java.util.Locale;


public class SettingScreen extends MFXScrollPane {

    private DomeinController dc;

    private ResourceController rs;

    private BorderPane root;

    private CustomMenu menu;

    private VBox pane = new VBox();
    private ComboBox<String> languageComboBox;
    private static Label title;
    private static MFXCheckbox checkbox;
    private static Label soundLabel;
    private static Label languageLabel;

    public SettingScreen(BorderPane root, CustomMenu menu, DomeinController dc, ResourceController rs) {
        this.dc = dc;
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setSpacing(200);
        this.rs = rs;
        this.root = root;
        this.menu = menu;
        this.setContent(pane);
        this.setFitToHeight(true);
        this.setFitToWidth(true);
        setup();
    }

    public void setup() {
         title = new Label(LanguageBundle.getString("SettingScreen_setting"));
        title.getStyleClass().add("title");

        soundLabel = new Label(LanguageBundle.getString("SettingScreen_sound"));

        VBox layoutBox = new VBox();
        MFXSlider slider = new MFXSlider();
        slider.setMin(0);
        slider.setMax(100);
        slider.setValue(rs.getCurrentVolume()*100);
        slider.setOnMouseReleased(e -> {
            System.out.println(slider.getValue());
            rs.changeVolume(slider.getValue());
        });

        languageLabel = new Label("Language");

        languageComboBox = new ComboBox<>();
        HBox.setHgrow(languageComboBox, Priority.ALWAYS);
        languageComboBox.getItems().addAll("en", "nl");
        languageComboBox.setValue("en");
        // voeg een listener toe aan de ComboBox om de taal te wijzigen
        languageComboBox.setOnAction(e -> switchLanguage());
        layoutBox.setAlignment(Pos.CENTER);

        checkbox = new MFXCheckbox(LanguageBundle.getString("SettingScreen_mute"));
        checkbox.setSelected(rs.isMute());
        checkbox.setOnAction(e -> rs.handleMute(checkbox.isSelected()));

        layoutBox.setSpacing(20);

        layoutBox.getChildren().addAll(soundLabel, slider, checkbox, languageLabel, languageComboBox);

        pane.getChildren().addAll(title, layoutBox);
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
        updateText();
        /*LoginScreen.updateText();
        MainScreen.updateText();
        CompanyCardComponent.updateText();
        NotificationScreen.updateText();
        OrderScreen.updateText();
        TransportDienstScreen.updateText();
        RegisterScreen.updateText();
        MyProductsScreen.updateText();*/

    }
    public static void updateText() {
        title.setText(LanguageBundle.getString("SettingScreen_setting"));
        checkbox.setText(LanguageBundle.getString("SettingScreen_mute"));
        soundLabel.setText(LanguageBundle.getString("SettingScreen_sound"));
    }
}
