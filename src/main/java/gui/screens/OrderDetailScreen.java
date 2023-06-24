package gui.screens;

import domein.*;
import gui.ScreenController;
import gui.components.CustomMenu;
import gui.components.LanguageBundle;
import io.github.palexdev.materialfx.controls.*;
import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import mock.transportDienstMock;
import resources.ResourceController;

import static javafx.collections.FXCollections.observableArrayList;

public class OrderDetailScreen extends MFXScrollPane {
    private BorderPane root;
    private CustomMenu navbar;
    private DomeinController dc;
    private ResourceController rs;
    private String orderId;
    private GridPane gridPane;
    private MFXButton processOrderButton = new MFXButton("Process Order");
    private OrderDetail ord;
    private Label sceneTitle = new Label("Order Details");
    AnimationTimer task;
    private  Separator separator;
    private Label itemsTitleLabel = new Label("Items");
    private MFXComboBox processTransportCombo;
    private MFXComboBox processBoxCombo;
    private ObservableList<Integer> transportDienstenLijst = observableArrayList();
    private ObservableList<Integer> boxLijst = observableArrayList();
    private MFXButton backButton = new MFXButton("Back");

    private VBox pane = new VBox();

    public OrderDetailScreen(BorderPane root, CustomMenu navbar, DomeinController dc, ResourceController rs, String orderId) {
        this.dc = dc;
        this.root = root;
        this.rs = rs;
        this.orderId = orderId;
        this.ord = dc.getOrder(orderId);
        this.setContent(pane);
        this.setFitToHeight(true);
        this.setFitToWidth(true);
        setup();
        /*MFXProgressSpinner spinner = new MFXProgressSpinner();
        this.getChildren().add(spinner);
        task = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (Model.orders != null) {
                    setup();
                    task.stop();
                }
            }
        };

        task.start();*/


    }
    private void setup(){
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        try {
            sceneTitle.setText(LanguageBundle.getString("OrderDetailScreen_name") + ord.getNaamKlant());
            sceneTitle.getStyleClass().add("title");
            sceneTitle.setTextAlignment(TextAlignment.CENTER);
            generateOrderDetailPage(ord);
            setupSeperator();
            setupComboTransportDienst();
            setupComboBox();
            setupBackButton();
            setupProcessButton();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            System.out.println(backButton.getText());
            System.out.println(processOrderButton.getText());
            HBox buttonGroup = new HBox(backButton, processOrderButton);
            buttonGroup.setAlignment(Pos.CENTER);
            buttonGroup.setSpacing(10);
            VBox groupOrder = new VBox(sceneTitle, gridPane,processTransportCombo, processBoxCombo ,separator,itemsTitleLabel, buttonGroup);
            groupOrder.setAlignment(Pos.CENTER);
            groupOrder.setSpacing(20);
            pane.getChildren().add(groupOrder);
        }
    }

    private void generateOrderDetailPage(OrderDetail order) {

        Label orderIdLabel = new Label(LanguageBundle.getString("OrderDetailScreen_id"));
        Label orderIdValue = new Label(order.getId());
        gridPane.add(orderIdLabel, 0, 0);
        gridPane.add(orderIdValue, 1, 0);

        Label orderDate = new Label(LanguageBundle.getString("OrderDetailScreen_date"));
        Label orderDateValue = new Label(order.getOrderDateTime());
        gridPane.add(orderDate, 0, 1);
        gridPane.add(orderDateValue, 1, 1);

        Label street = new Label(LanguageBundle.getString("OrderDetailScreen_street"));
        Label streetValue = new Label(order.getStreet());
        gridPane.add(street, 0, 2);
        gridPane.add(streetValue, 1, 2);

        Label houseNr = new Label(LanguageBundle.getString("OrderDetailScreen_streetNr"));
        Label houseNrValue = new Label(order.getStreetNumber());
        gridPane.add(houseNr, 0, 3);
        gridPane.add(houseNrValue, 1, 3);

        Label state = new Label(LanguageBundle.getString("OrderDetailScreen_state"));
        Label stateValue = new Label(order.getOrderStatus());
        gridPane.add(state, 0, 4);
        gridPane.add(stateValue, 1, 4);

        Label customerName = new Label(LanguageBundle.getString("OrderDetailScreen_customer"));
        Label customerNameValue = new Label(String.format(order.getNaamKlant()));
        gridPane.add(customerName, 0, 5);
        gridPane.add(customerNameValue, 1, 5);

        Label buyer = new Label(LanguageBundle.getString("OrderDetailScreen_buyer"));
        Label buyerValue = new Label(order.getAankoper());
        gridPane.add(buyer, 0, 6);
        gridPane.add(buyerValue, 1, 6);

        Label Products = new Label(LanguageBundle.getString("OrderDetailScreen_products"));
        Products.getStyleClass().add("title");
        gridPane.add(Products, 0, 7);

        int number = 8;
        for (ProductInfo product : order.getProducts())
        {

            Label Name = new Label(LanguageBundle.getString("OrderDetailScreen_productName"));
            Label NameValue = new Label(product.getNaam());
            gridPane.add(Name, 0, number);
            gridPane.add(NameValue, 1, number);

            Label Price = new Label(LanguageBundle.getString("OrderDetailScreen_price"));
            Label PriceValue = new Label(Integer.toString(product.getEenheidsprijs()));
            gridPane.add(Price, 2, number);
            gridPane.add(PriceValue, 3, number);

            Label Quantity = new Label(LanguageBundle.getString("OrderDetailScreen_quantity"));
            Label QuantityValue = new Label(Integer.toString((product.getAantal())));
            gridPane.add(Quantity, 4, number);
            gridPane.add(QuantityValue, 5, number);

            Label TotalPrice = new Label(LanguageBundle.getString("OrderDetailScreen_total"));
            Label TotalPriceValue = new Label(Integer.toString(product.getTotalePrijs()));
            gridPane.add(TotalPrice, 6, number);
            gridPane.add(TotalPriceValue, 7, number);
            number++;
        }

    }
    private void setupComboTransportDienst(){
        for (TransportDienst el:dc.getTransportServices()) {
            transportDienstenLijst.add(el.getId());
        }
        processTransportCombo = new MFXComboBox<>();
        processTransportCombo.setDisable(false);
        processTransportCombo.setItems(transportDienstenLijst);
        processTransportCombo.setPromptText(LanguageBundle.getString("OrderDetailScreen_select"));
        processTransportCombo.setPrefWidth(280);
        processTransportCombo.setFloatingText(LanguageBundle.getString("OrderDetailScreen_delivery"));

        processTransportCombo.setOnAction(evt -> {
            rs.playSoundEffect("click");
            processOrderButton.setDisable(false);
            ord.setTransporterId((Integer) processTransportCombo.getValue());
        });
    }
    private void setupComboBox() {
        for (Box el:dc.getBoxes()) {
            boxLijst.add(el.getId());
        }
        processBoxCombo = new MFXComboBox<>();
        processBoxCombo.setDisable(false);
        processBoxCombo.setItems(boxLijst);
        processBoxCombo.setPromptText(LanguageBundle.getString("OrderDetailScreen_selectBox"));
        processBoxCombo.setPrefWidth(280);
        processBoxCombo.setFloatingText(LanguageBundle.getString("OrderDetailScreen_box"));

        processBoxCombo.setOnAction(evt -> {
            rs.playSoundEffect("click");
            processOrderButton.setDisable(false);
            ord.setPackagingId((Integer) processBoxCombo.getValue());

        });
    }
    private void setupBackButton(){
        backButton.setText(LanguageBundle.getString("OrderDetailScreen_back"));
        backButton.setOnAction(evt -> {
            rs.playSoundEffect("click");
            ScreenController.changeToOrderScreen(root, navbar, dc, rs);
        });
    }
    private void setupProcessButton(){
        processOrderButton.setText(LanguageBundle.getString("OrderDetailScreen_process"));
        processOrderButton.setDisable(true);
        processOrderButton.setOnAction(evt -> {
            rs.playSoundEffect("click");
            dc.editOrder(ord);
            ScreenController.changeToOrderScreen(root, navbar, dc, rs);
        });
    }
    private void setupSeperator(){
        separator = new Separator(Orientation.HORIZONTAL);
        separator.setPrefWidth(300);
        itemsTitleLabel = new Label(LanguageBundle.getString("OrderDetailScreen_items"));
    }
    private static Order getObjectById(ObservableList<Order> list, String orderId) {
        return list.stream()
                .filter(obj -> obj.getOrderID().equals(orderId))
                .findFirst()
                .orElse(null);
    }
}
