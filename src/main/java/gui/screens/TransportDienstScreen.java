package gui.screens;

import domein.DomeinController;
import domein.TransportDienst;
import gui.ScreenController;
import gui.components.CustomMenu;
import gui.components.LanguageBundle;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.enums.FloatMode;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.Callback;
import mock.transportDienstMock;
import model.User;
import resources.ResourceController;
import io.github.palexdev.materialfx.controls.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TransportDienstScreen extends MFXScrollPane {

    private BorderPane root;
    private DomeinController dc;
    private ResourceController rs;
    private CustomMenu navbar;
    private TableView<TransportDienst> transportDienstTable;
    private Integer id;
    private static MFXTextField name;
    private MFXTextField trackAndTraceCode;
    private static MFXComboBox<Boolean> isActive;
    private MFXListView<String> phoneNumbers;
    private MFXListView<String> emailAddresses;
    private static MFXButton EditButton;
    private static Label phoneNumbersLabel;
    private static Label emailAddressesLabel;
    private static TableColumn<TransportDienst, String> name_tabel;
    private static TableColumn<TransportDienst, Boolean> isActive_tabel;
    private ObservableList<TransportDienst> observableTransportDiensten = FXCollections.observableArrayList();
    private static MFXButton NewTransportDienst;
    private static Label titleLabel;
    private Label nameLabel;
    private Label isActiveLabel;

    private VBox pane = new VBox();


    public TransportDienstScreen(BorderPane root, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        this.dc = dc;
        this.root = root;
        this.rs = rs;
        this.navbar = navbar;
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(50);
        observableTransportDiensten = dc.getTransportServices();
        titleLabel = new Label(LanguageBundle.getString("TransportDienstScreen_transport"));
        titleLabel.getStyleClass().add("title");
        NewTransportDienst = new MFXButton(LanguageBundle.getString("TransportDienstScreen_new"));
        NewTransportDienst.setPrefSize(100, 40);
        NewTransportDienst.setOnAction(event -> {
            ScreenController.changeToAddTransportDienstScreen(root, navbar, dc, rs);
        });

        VBox container = new VBox(titleLabel, NewTransportDienst, new SplitPane(table(), textFields()));
        container.setSpacing(20);
        container.setAlignment(Pos.CENTER);
        container.setFillWidth(true);
        pane.getChildren().add(container);
        this.setContent(pane);
        this.setFitToHeight(true);
        this.setFitToWidth(true);
    }

    private VBox table() {
        try {
            transportDienstTable = new TableView<>();
            setupTable();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            VBox groupTD = new VBox(transportDienstTable);
            groupTD.setAlignment(Pos.CENTER_LEFT);
            groupTD.setSpacing(20);
            groupTD.setPadding(new Insets(50, 50, 50, 50));
            groupTD.setVgrow(transportDienstTable, Priority.ALWAYS);
            return groupTD;
        }
    }

    private void setupTable() {
        name_tabel = new TableColumn<>(LanguageBundle.getString("TransportDienstScreen_Name"));
        name_tabel.setCellValueFactory(new PropertyValueFactory<>("Name"));
        name_tabel.setPrefWidth(400);

        isActive_tabel = new TableColumn<>(LanguageBundle.getString("TransportDienstScreen_isActive"));
        isActive_tabel.setCellValueFactory(new PropertyValueFactory<>("isActive"));
        isActive_tabel.setPrefWidth(262);

        transportDienstTable.getColumns().addAll(name_tabel, isActive_tabel);
        transportDienstTable.setItems(observableTransportDiensten/*transportDienstMock.transportDiensten*/);

        transportDienstTable.setRowFactory(tv -> {
            TableRow<TransportDienst> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    TransportDienst transportDienst = row.getItem();
                    updateTextFields(transportDienst);
                }
            });
            return row;
        });

    }

    private void updateTextFields(TransportDienst transportDienst) {
        id = transportDienst.getId();
        name.setText(transportDienst.getName());
        //trackAndTraceCode.setText(transportDienst.getTrackAndTraceCode());
        isActive.setValue(transportDienst.getIsActive());
        phoneNumbers.setItems(FXCollections.observableArrayList(splitString(transportDienst.getPhoneNumbers())));
        emailAddresses.setItems(FXCollections.observableArrayList(splitString(transportDienst.getEmailAddresses())));
    }

    public List<String> splitString(String inputString) {
        List<String> outputList = new ArrayList<>();
        String[] splitArray = inputString.split(",");
        for (String str : splitArray) {
            outputList.add(str.trim());
        }
        return outputList;
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

    private VBox textFields() {
        VBox textFields = new VBox();

        nameLabel = new Label("name");
        isActiveLabel = new Label("isActive");

        Label transportDienstenOverview = new Label("Transport Diensten Overview");
        transportDienstenOverview.setStyle("-fx-font-size: 24px; -fx-font-family: Arial; -fx-font-weight: bold; -fx-text-fill: #000;");

        name = new MFXTextField(LanguageBundle.getString("TransportDienstScreen_Name"), LanguageBundle.getString("TransportDienstScreen_name"));
        name.setPrefWidth(350);
        name.setPrefHeight(40);
        name.setFloatMode(FloatMode.ABOVE);

        /*
        trackAndTraceCode = new MFXTextField("Track & Trace", "trackAndTraceCode");
        trackAndTraceCode.setPrefWidth(350);
        trackAndTraceCode.setPrefHeight(40);
        trackAndTraceCode.setFloatMode(FloatMode.ABOVE);
         */

        isActive = new MFXComboBox<>();
        isActive.getItems().addAll(true, false);
        isActive.setPrefHeight(30);
        isActive.setPromptText(LanguageBundle.getString("TransportDienstScreen_active"));

        phoneNumbersLabel = new Label(LanguageBundle.getString("TransportDienstScreen_phone"));

        phoneNumbers = new MFXListView<>();
        phoneNumbers.setPrefSize(350, 100);

        emailAddressesLabel = new Label(LanguageBundle.getString("TransportDienstScreen_email"));

        emailAddresses = new MFXListView<>();
        emailAddresses.setPrefSize(350, 100);

        EditButton = new MFXButton(LanguageBundle.getString("TransportDienstScreen_edit"));
        EditButton.setPrefSize(100, 40);
        EditButton.setOnAction(event -> {
            System.out.println("Edit");
            String TDName = name.getText();
            //String TDTrackAndTraceCode = trackAndTraceCode.getText();
            Boolean TDIsActive = isActive.getValue();
            ObservableList<String> TDPhoneNumbers = phoneNumbers.getItems();
            ObservableList<String> TDEmailAddresses = emailAddresses.getItems();

            TransportDienst transportDienstEdit = new TransportDienst(id, TDName, joinStrings(TDPhoneNumbers), joinStrings(TDEmailAddresses), TDIsActive);
            dc.editTransportService(transportDienstEdit);

            name.clear();
            //trackAndTraceCode.clear();
            isActive.setValue(null);
            phoneNumbers.getItems().clear();
            emailAddresses.getItems().clear();
        });

        textFields.getChildren().addAll(nameLabel, name, /*trackAndTraceCode,*/ isActiveLabel, isActive, phoneNumbersLabel, phoneNumbers, emailAddressesLabel, emailAddresses, EditButton);
        textFields.setSpacing(20);
        textFields.setAlignment(Pos.CENTER);
        textFields.setPadding(new Insets(40, 40, 40, 40));

        return textFields;
    }

    public static void updateText() {
        titleLabel.setText(LanguageBundle.getString("TransportDienstScreen_transport"));
        NewTransportDienst.setText(LanguageBundle.getString("TransportDienstScreen_new"));
        name.setPromptText(LanguageBundle.getString("TransportDienstScreen_Name"));
        name.setText(LanguageBundle.getString("TransportDienstScreen_Name"));
        name_tabel.setText(LanguageBundle.getString("TransportDienstScreen_Name"));
        isActive_tabel.setText(LanguageBundle.getString("TransportDienstScreen_isActive"));
        emailAddressesLabel.setText(LanguageBundle.getString("TransportDienstScreen_email"));
        phoneNumbersLabel.setText(LanguageBundle.getString("TransportDienstScreen_phone"));
        isActive.setPromptText(LanguageBundle.getString("TransportDienstScreen_active"));
        EditButton.setText(LanguageBundle.getString("TransportDienstScreen_edit"));
    }

    public void updateTransportDiensten() {
        ObservableList<TransportDienst> transportDienstItems = FXCollections.observableArrayList(dc.getTransportServices());
        transportDienstTable.setItems(transportDienstItems);
    }

}
