package gui.screens;

import domein.DomeinController;
import domein.Order;
import domein.Product;
import gui.ScreenController;
import gui.components.CustomMenu;
import gui.components.DataGrid;
import gui.components.LanguageBundle;
import gui.paper.PaperComponent;
import gui.popups.Alert;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import mock.Model;
import resources.ResourceController;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MyProductsScreen extends MFXScrollPane {
    private BorderPane root;
    private CustomMenu navbar;
    private DomeinController dc;
    private ResourceController rs;
    private DataGrid productTable;
    private ObservableList<Product> productList;
    private static Label productPanelLabel;

    AnimationTimer task;


    private VBox pane = new VBox();

    public MyProductsScreen(BorderPane root, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        this.dc = dc;
        pane.setAlignment(Pos.CENTER);
        this.root = root;
        this.rs = rs;
        MFXProgressSpinner spinner = new MFXProgressSpinner();
        pane.getChildren().add(spinner);
        this.setContent(pane);
        this.setFitToHeight(true);
        this.setFitToWidth(true);


        task = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (productList != null) {
                    setup();
                    task.stop();
                }
            }
        };
        this.productList = dc.getProducts();
        task.start();
    }

    private void setup() {
        try {
            productTable = new DataGrid();
            setupProductTable();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            productPanelLabel = new Label(LanguageBundle.getString("MyProductScreen_product"));
            productPanelLabel.getStyleClass().add("title");

            productPanelLabel.setTextAlignment(TextAlignment.CENTER);
            VBox groupOrder = new VBox(productPanelLabel, productTable);
            groupOrder.setAlignment(Pos.CENTER);
            groupOrder.setSpacing(20);
            pane.getChildren().clear();
            pane.getChildren().add(groupOrder);
        }
    }

    private void setupProductTable() {
        MFXTableColumn<Product> id = new MFXTableColumn<>("id", true, Comparator.comparing(Product::getId));
        MFXTableColumn<Product> companyId = new MFXTableColumn<>(LanguageBundle.getString("MyProductScreen_bedrijf"), true, Comparator.comparing(Product::getCompanyId));
        MFXTableColumn<Product> image = new MFXTableColumn<>(LanguageBundle.getString("MyProductScreen_image"), true, Comparator.comparing(Product::getImage));
        MFXTableColumn<Product> stock = new MFXTableColumn<>(LanguageBundle.getString("MyProductScreen_stock"), true, Comparator.comparing(Product::getStock));


        id.setRowCellFactory(order -> new MFXTableRowCell<>(Product::getId));
        id.setPrefWidth(50);

        companyId.setRowCellFactory(order -> new MFXTableRowCell<>(Product::getCompanyId));
        companyId.setPrefWidth(200);

        image.setRowCellFactory(order -> new MFXTableRowCell<>(Product::getImage));
        image.setPrefWidth(250);

        stock.setRowCellFactory(order -> new MFXTableRowCell<>(Product::getStock));
        stock.setPrefWidth(250);


        productTable.getTableColumns().addAll(id, companyId, image, stock);
        productTable.getFilters().addAll(
                new IntegerFilter<>("id", Product::getId),
                new IntegerFilter<>(LanguageBundle.getString("MyProductScreen_bedrijf"), Product::getCompanyId),
                new StringFilter<>(LanguageBundle.getString("MyProductScreen_image"), Product::getImage),
                new IntegerFilter<>(LanguageBundle.getString("MyProductScreen_stock"), Product::getStock)
        );
        productTable.setItems(productList);
    }
    public static void updateText() {
        productPanelLabel.setText(LanguageBundle.getString("MyProductScreen_product"));
    }

}