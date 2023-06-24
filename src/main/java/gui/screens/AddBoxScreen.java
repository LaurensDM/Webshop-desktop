package gui.screens;

import domein.DomeinController;
import gui.ScreenController;
import gui.components.CustomMenu;
import gui.components.LanguageBundle;
import gui.components.NumFieldFX;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import resources.ResourceController;
import javafx.scene.control.*;


public class AddBoxScreen extends MFXScrollPane {

    private BorderPane root;
    private DomeinController dc;
    private ResourceController rs;
    private CustomMenu navbar;

    private TextField name;
    private ComboBox<String> type;
    private TextField width;
    private TextField height;
    private TextField length;
    private TextField price;
    private ComboBox<Boolean> isActive;

    private VBox pane = new VBox();

    public AddBoxScreen(BorderPane root, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        this.root = root;
        this.dc = dc;
        this.rs = rs;
        this.navbar = navbar;
        pane.setSpacing(10);
        pane.setPadding(new Insets(10, 550, 100, 550));

        Button back = new Button(LanguageBundle.getString("AddBoxScreen_back"));
        back.setOnAction(e -> ScreenController.changeToMainScreen(root, navbar, dc, rs));
        Button boxes = new Button(LanguageBundle.getString("AddBoxScreen_boxes"));
        boxes.setOnAction(e -> ScreenController.changeToBoxScreen(root, navbar, dc, rs));

        Label titleLabel = new Label(LanguageBundle.getString("AddBoxScreen_addBox"));
        titleLabel.getStyleClass().add("title");

        Label nameLabel = new Label(LanguageBundle.getString("AddBoxScreen_name"));
        name = new TextField();
        name.setPrefHeight(30);
        name.setPrefWidth(350);
        name.setId("name");

        Label typeLabel = new Label(LanguageBundle.getString("AddBoxScreen_type"));
        type = new ComboBox<>();
        type.getItems().addAll("Standard", "Custom");
        type.setPrefHeight(30);
        type.setPrefWidth(350);
        type.setId("type");

        Label widthLabel = new Label(LanguageBundle.getString("AddBoxScreen_width"));
        width = new TextField();
        width.setPrefHeight(30);
        width.setPrefWidth(350);
        width.setId("width");


        Label heightLabel = new Label(LanguageBundle.getString("AddBoxScreen_height"));
        height = new TextField();
        height.setPrefHeight(30);
        height.setPrefWidth(350);
        height.setId("height");

        Label lengthLabel = new Label(LanguageBundle.getString("AddBoxScreen_length"));
        length = new TextField();
        length.setPrefHeight(30);
        length.setPrefWidth(350);
        length.setId("length");

        Label priceLabel = new Label(LanguageBundle.getString("AddBoxScreen_price"));
        price = new TextField();
        price.setPrefHeight(30);
        price.setPrefWidth(350);
        price.setId("price");

        Label isActiveLabel = new Label(LanguageBundle.getString("AddBoxScreen_active"));
        isActive = new ComboBox<>();
        isActive.getItems().addAll(true, false);
        isActive.setPrefHeight(30);
        isActive.setPrefWidth(350);
        isActive.setId("isActive");

        Button addButton = new Button(LanguageBundle.getString("AddBoxScreen_addBox"));
        addButton.prefWidthProperty().bind(this.widthProperty());
        addButton.setOnAction(e -> addNewBox());
        addButton.setId("addButton");

        pane.getChildren().addAll(
                back, boxes,
                titleLabel,
                nameLabel,
                name,
                typeLabel,
                type,
                widthLabel,
                width,
                heightLabel,
                height,
                lengthLabel,
                length,
                priceLabel,
                price,
                isActiveLabel,
                isActive,
                addButton
        );
        this.setContent(pane);
        this.setFitToHeight(true);
        this.setFitToWidth(true);
    }


    public void addNewBox() {

        String name = this.name.getText();
        String type = this.type.getSelectionModel().getSelectedItem();
        String width = this.width.getText();
        String height = this.height.getText();
        String length = this.length.getText();
        double price = Double.parseDouble(this.price.getText());
        boolean isActive = this.isActive.getSelectionModel().getSelectedItem();

        dc.addBox(name, type, Double.parseDouble(width), Double.parseDouble(height), Double.parseDouble(length), price, isActive);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(LanguageBundle.getString("AddBoxScreen_succes"));
        alert.showAndWait();

        this.name.clear();
        this.type.getSelectionModel().clearSelection();
        this.width.clear();
        this.height.clear();
        this.length.clear();
        this.price.clear();
        this.isActive.getSelectionModel().clearSelection();
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setType(String type) {
        this.type.getSelectionModel().select(type);
    }

    public void setWidth(String width) {
        this.width.setText(width);
    }

    public void setHeight(String height) {
        this.height.setText(height);
    }

    public void setLength(String length) {
        this.length.setText(length);
    }

    public void setPrice(double price) {
        this.price.setText(String.valueOf(price));
    }

    public void setIsActive(boolean isActive) {
        this.isActive.getSelectionModel().select(isActive);
    }


}
