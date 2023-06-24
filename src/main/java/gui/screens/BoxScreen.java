package gui.screens;

import domein.Box;
import domein.DomeinController;
import domein.TransportDienst;
import gui.ScreenController;
import gui.components.CustomMenu;
import gui.components.LanguageBundle;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import resources.ResourceController;

public class BoxScreen extends MFXScrollPane {

    private BorderPane root;
    private DomeinController dc;
    private ResourceController rs;
    private CustomMenu navbar;
    private TableView<Box> boxTable;
    private Integer id;
    private MFXTextField name;
    private MFXComboBox<String> type;
    private MFXTextField width;
    private MFXTextField height;
    private MFXTextField length;
    private MFXTextField price;
    private MFXComboBox<Boolean> isActive;
    private MFXButton EditButton;
    private ObservableList<Box> observableBoxes = FXCollections.observableArrayList();
    private Label nameLabel;
    private Label typeLabel;
    private Label widthLabel;
    private Label heightLabel;
    private Label lengthLabel;
    private Label priceLabel;
    private Label isActiveLabel;

    private VBox pane = new VBox();

    public BoxScreen(BorderPane root, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        this.root = root;
        this.dc = dc;
        this.rs = rs;
        this.navbar = navbar;

        observableBoxes = dc.getBoxes();

        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(50);
        Label titleLabel = new Label(LanguageBundle.getString("BoxScreen_boxes"));
        titleLabel.getStyleClass().add("title");

        MFXButton NewBox = new MFXButton(LanguageBundle.getString("BoxScreen_new"));
        NewBox.setPrefSize(100, 40);
        NewBox.setOnAction(event -> {
            ScreenController.changeToAddBoxScreen(root, navbar, dc, rs);
        });

        VBox container = new VBox(titleLabel, NewBox, new SplitPane(table(), textFields()));
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
            boxTable = new TableView<>();
            setupTable();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            VBox groupBox = new VBox(boxTable);
            groupBox.setAlignment(Pos.CENTER_LEFT);
            groupBox.setSpacing(20);
            groupBox.setPadding(new Insets(50, 50, 50, 50));
            groupBox.setVgrow(boxTable, Priority.ALWAYS);
            return groupBox;
        }
    }

    private void setupTable() {
        TableColumn<Box, String> name = new TableColumn<>(LanguageBundle.getString("BoxScreen_name"));
        name.setCellValueFactory(new PropertyValueFactory<>(LanguageBundle.getString("BoxScreen_name")));
        name.setPrefWidth(100);

        TableColumn<Box, String> type = new TableColumn<>(LanguageBundle.getString("BoxScreen_type"));
        type.setCellValueFactory(new PropertyValueFactory<>(LanguageBundle.getString("BoxScreen_type")));
        type.setPrefWidth(100);

        TableColumn<Box, Double> width = new TableColumn<>(LanguageBundle.getString("BoxScreen_width"));
        width.setCellValueFactory(new PropertyValueFactory<>(LanguageBundle.getString("BoxScreen_width")));
        width.setPrefWidth(70);

        TableColumn<Box, Double> height = new TableColumn<>(LanguageBundle.getString("BoxScreen_height"));
        height.setCellValueFactory(new PropertyValueFactory<>(LanguageBundle.getString("BoxScreen_height")));
        height.setPrefWidth(70);

        TableColumn<Box, Double> length = new TableColumn<>(LanguageBundle.getString("BoxScreen_length"));
        length.setCellValueFactory(new PropertyValueFactory<>(LanguageBundle.getString("BoxScreen_length")));
        length.setPrefWidth(70);

        TableColumn<Box, Double> price = new TableColumn<>(LanguageBundle.getString("BoxScreen_price"));
        price.setCellValueFactory(new PropertyValueFactory<>(LanguageBundle.getString("BoxScreen_price")));
        price.setPrefWidth(100);

        TableColumn<Box, Boolean> isActive = new TableColumn<>(LanguageBundle.getString("BoxScreen_active"));
        isActive.setCellValueFactory(new PropertyValueFactory<>(LanguageBundle.getString("BoxScreen_active")));
        isActive.setPrefWidth(80);

        boxTable.getColumns().addAll(name, type, width, height, length, price, isActive);

        boxTable.setItems(observableBoxes);

        boxTable.setRowFactory(tv -> {
            TableRow<Box> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    Box box = row.getItem();
                    updateTextFields(box);
                }
            });
            return row;
        });
    }

    private void updateTextFields(Box box) {
        id = box.getId();
        name.setText(box.getName());
        type.setText(box.getType());

        width.setText(String.format("%.2f",box.getWidth()) );
        height.setText(String.format("%.2f",box.getHeight()));
        length.setText(String.format("%.2f",box.getLength()));

        StringConverter<Double> converter = new DoubleStringConverter();
        double value = box.getPrice();
        String stringValue = converter.toString(value); // "10.5"
        price.setText(stringValue);
        isActive.setValue(box.getIsActive());
    }

    private VBox textFields() {
        VBox textFields = new VBox();

        //labels
        nameLabel = new Label(LanguageBundle.getString("BoxScreen_name"));
        typeLabel = new Label(LanguageBundle.getString("BoxScreen_type"));
        widthLabel = new Label(LanguageBundle.getString("BoxScreen_width"));
        heightLabel = new Label(LanguageBundle.getString("BoxScreen_height"));
        lengthLabel = new Label(LanguageBundle.getString("BoxScreen_length"));
        priceLabel = new Label(LanguageBundle.getString("BoxScreen_price"));
        isActiveLabel = new Label(LanguageBundle.getString("BoxScreen_active"));

        name = new MFXTextField(LanguageBundle.getString("BoxScreen_name"), "name");
        name.setPrefWidth(350);
        name.setPrefHeight(40);

        type = new MFXComboBox<>();
        type.getItems().addAll("standard", "custom");
        type.setPrefWidth(350);
        type.setPrefHeight(40);

        width = new MFXTextField(LanguageBundle.getString("BoxScreen_width"), "width");
        width.setPrefWidth(100);
        width.setPrefHeight(40);
        height = new MFXTextField(LanguageBundle.getString("BoxScreen_height"), "height");
        height.setPrefWidth(100);
        height.setPrefHeight(40);
        length = new MFXTextField(LanguageBundle.getString("BoxScreen_length"), "length");
        length.setPrefWidth(100);
        length.setPrefHeight(40);

        price = new MFXTextField(LanguageBundle.getString("BoxScreen_price"), "price");
        price.setPrefWidth(350);
        price.setPrefHeight(40);

        isActive = new MFXComboBox<>();
        isActive.getItems().addAll(true, false);
        isActive.setPrefWidth(350);
        isActive.setPrefHeight(40);
        isActive.setPromptText("Active?");

        EditButton = new MFXButton(LanguageBundle.getString("BoxScreen_edit"));
        EditButton.setPrefSize(100, 40);
        EditButton.setOnAction(e -> {
            String BoxName = name.getText();
            String BoxType = type.getText();
            String BoxWidth = width.getText();
            String BoxHeight = height.getText();
            String BoxLength = length.getText();
            double BoxPrice = Double.parseDouble(price.getText());
            boolean BoxIsActive = isActive.getValue();

            Box boxEdit = new Box(id, BoxName, BoxType, Double.parseDouble(BoxWidth) ,Double.parseDouble(BoxHeight) , Double.parseDouble(BoxLength), BoxPrice, BoxIsActive);
            dc.editBox(boxEdit);

            name.clear();
            type.clear();
            width.clear();
            height.clear();
            length.clear();
            price.clear();
            isActive.clear();
        });

        textFields.getChildren().addAll(nameLabel, name, typeLabel, type, widthLabel, width, heightLabel, height, lengthLabel, length, priceLabel, price, isActiveLabel, isActive, EditButton);
        textFields.setSpacing(20);
        textFields.setAlignment(Pos.CENTER);
        textFields.setPadding(new Insets(40, 40, 40, 40));

        return textFields;
    }

    public String[] splitOnX(String input) {
        return input.split("x");
    }

}
