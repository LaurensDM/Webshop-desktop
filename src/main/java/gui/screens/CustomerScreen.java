package gui.screens;

import domein.DomeinController;
import domein.Product;
import gui.components.CustomMenu;
import gui.components.DataGrid;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import model.Company;
import resources.ResourceController;

import java.util.Comparator;
import java.util.List;

public class CustomerScreen extends MFXScrollPane {

    private BorderPane root;
    private CustomMenu navbar;
    private DomeinController dc;
    private ResourceController rs;
    private DataGrid customerTable;
    private ObservableList<Company> companyList;

    AnimationTimer task;


    private VBox pane = new VBox();

    public CustomerScreen(BorderPane root, CustomMenu navbar, DomeinController dc, ResourceController rs) {
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
                if (companyList != null) {
                    setup();
                    task.stop();
                }
            }
        };
        this.companyList = dc.getCustomers(dc.getCompanyFromAuthProvider().getId());
        task.start();
    }

    private void setup() {
        try {
            customerTable = new DataGrid();
            setupProductTable();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Label CustomerPanelLabel = new Label("Customer Panel");
            CustomerPanelLabel.getStyleClass().add("title");
            CustomerPanelLabel.setTextAlignment(TextAlignment.CENTER);
            VBox groupOrder = new VBox(CustomerPanelLabel, customerTable);
            groupOrder.setAlignment(Pos.CENTER);
            groupOrder.setSpacing(20);
            pane.getChildren().clear();
            pane.getChildren().add(groupOrder);
        }
    }

    private void setupProductTable() {
        MFXTableColumn<Company> name = new MFXTableColumn<>("Name", true, Comparator.comparing(Company::getName));
        MFXTableColumn<Company> country = new MFXTableColumn<>("Country", true, Comparator.comparing(Company::getCountry));
        MFXTableColumn<Company> countrycode = new MFXTableColumn<>("Countrycode", true, Comparator.comparing(Company::getCountryCode));
        MFXTableColumn<Company> city = new MFXTableColumn<>("City", true, Comparator.comparing(Company::getCity));
        MFXTableColumn<Company> zipcode = new MFXTableColumn<>("Zipcode", true, Comparator.comparing(Company::getZipCode));
        MFXTableColumn<Company> street = new MFXTableColumn<>("Street", true, Comparator.comparing(Company::getStreet));
        MFXTableColumn<Company> street_number = new MFXTableColumn<>("Street Number", true, Comparator.comparing(Company::getStreetNumber));


        name.setRowCellFactory(company -> new MFXTableRowCell<>(Company::getName));
        name.setPrefWidth(120);

        country.setRowCellFactory(company -> new MFXTableRowCell<>(Company::getCity));
        country.setPrefWidth(120);

        countrycode.setRowCellFactory(company -> new MFXTableRowCell<>(Company::getCountryCode));
        countrycode.setPrefWidth(50);

        city.setRowCellFactory(company -> new MFXTableRowCell<>(Company::getCountry));
        city.setPrefWidth(150);

        zipcode.setRowCellFactory(company -> new MFXTableRowCell<>(Company::getZipCode));
        zipcode.setPrefWidth(120);

        street.setRowCellFactory(company -> new MFXTableRowCell<>(Company::getStreet));
        street.setPrefWidth(150);

        street_number.setRowCellFactory(company -> new MFXTableRowCell<>(Company::getStreetNumber));
        street_number.setPrefWidth(40);


        customerTable.getTableColumns().addAll(name, country, countrycode, city, zipcode, street, street_number);
        customerTable.getFilters().addAll(
                new StringFilter<>("name", Company::getName),
                new StringFilter<>("Countrycode", Company::getCountryCode),
                new StringFilter<>("image", Company::getCountry)

        );
        customerTable.setItems(companyList);
    }

}
