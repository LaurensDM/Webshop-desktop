package gui.screens;

import domein.DomeinController;
import gui.ScreenController;
import gui.components.CustomMenu;
import gui.components.LanguageBundle;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import resources.ResourceController;

import java.util.ArrayList;
import java.util.List;

public class addTransportDienst extends MFXScrollPane {
    private BorderPane root;
    private DomeinController dc;
    private CustomMenu navbar;
    private ResourceController rs;
    private TextField nameField;
    private TextField phoneNumbersField;
    private TextField emailAddressesField;
    private ComboBox isActiveComboBox;
    private ListView<String> phoneNumbersList;
    private ListView<String> emailAddressesList;
    private List<String> phoneNumbers = new ArrayList<>();
    private List<String> emailAddresses = new ArrayList<>();
    private Button back;
    private Label titleLabel;
    private Label nameLabel;

    private VBox pane = new VBox();

    public addTransportDienst(BorderPane root, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        this.dc = dc;
        this.navbar = navbar;
        pane.setSpacing(10);
        pane.setPadding(new Insets(10, 550, 100, 550));

        back = new Button(LanguageBundle.getString("AddTransportDienst_back"));
        back.setOnAction(e -> ScreenController.changeToTransportDienstScreen(root, navbar, dc, rs));

        titleLabel = new Label(LanguageBundle.getString("AddTransportDienst_transport"));
        titleLabel.getStyleClass().add("title");

        nameLabel = new Label(LanguageBundle.getString("AddTransportDienst_name"));
        nameField = new TextField();

        Label phoneNumbersLabel = new Label(LanguageBundle.getString("AddTransportDienst_phone"));
        phoneNumbersField = new TextField();

        Button addPhoneNumberButton = new Button(LanguageBundle.getString("AddTransportDienst_add"));
        addPhoneNumberButton.setOnAction(e -> {
            phoneNumbers.add(phoneNumbersField.getText());
            phoneNumbersList.getItems().setAll(phoneNumbers);
            phoneNumbersField.clear();
        });

        phoneNumbersList = new ListView<>();
        phoneNumbersList.setPrefHeight(500);
        phoneNumbersList.getItems().setAll(phoneNumbers);

        Label emailAddressesLabel = new Label(LanguageBundle.getString("AddTransportDienst_email"));
        emailAddressesField = new TextField();

        Button addEmailAddressButton = new Button(LanguageBundle.getString("AddTransportDienst_add"));
        addEmailAddressButton.setOnAction(e -> {
            emailAddresses.add(emailAddressesField.getText());
            emailAddressesList.getItems().setAll(emailAddresses);
            emailAddressesField.clear();
        });

        emailAddressesList = new ListView<>();
        emailAddressesList.setPrefHeight(500);
        emailAddressesList.getItems().setAll(emailAddresses);

        Label isActiveLabel = new Label(LanguageBundle.getString("AddTransportDienst_isActive"));
        isActiveComboBox = new ComboBox<>();
        isActiveComboBox.getItems().addAll("true", "false");
        isActiveComboBox.setValue("true");

        Button addButton = new Button(LanguageBundle.getString("AddTransportDienst_add"));
        addButton.prefWidthProperty().bind(this.widthProperty());
        addButton.setOnAction(e -> addNewTransportDienst());
        //addButton.setPrefWidth(300);


        pane.getChildren().addAll(
                back, titleLabel,
                nameLabel, nameField,
                phoneNumbersLabel, phoneNumbersField, addPhoneNumberButton, phoneNumbersList,
                emailAddressesLabel, emailAddressesField, addEmailAddressButton, emailAddressesList,
                isActiveLabel, isActiveComboBox, addButton
        );
        this.setContent(pane);
        this.setFitToHeight(true);
        this.setFitToWidth(true);
    }

    public void addNewTransportDienst() {
        String name = nameField.getText();
        String isActiveString = (String) isActiveComboBox.getValue();
        boolean isActive = false;
        if (isActiveString.equalsIgnoreCase("true")) {
            isActive = true;
        }

        dc.addTransportService(name, joinStrings(emailAddresses), joinStrings(phoneNumbers), isActive);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Transport service successfully added.");
        alert.showAndWait();

        nameField.clear();
        phoneNumbers.clear();
        emailAddresses.clear();
        phoneNumbersList.getItems().clear();
        emailAddressesList.getItems().clear();
        isActiveComboBox.setValue("true");
    }

    public static String joinStrings(List<String> strings) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            sb.append(strings.get(i));
            if (i < strings.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

}
