package gui.screens;

import domein.DomeinController;
import domein.Order;
import domein.OrderDetail;
import gui.ScreenController;
import gui.components.CustomMenu;
import gui.components.DataGrid;
import gui.components.LanguageBundle;
import gui.popups.Alert;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import mock.Model;
import resources.ResourceController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;


public class OrderScreen extends MFXScrollPane {
    private BorderPane root;
    private CustomMenu navbar;
    private DomeinController dc;
    private ResourceController rs;
    private DataGrid orderTabel;
    private static MFXComboBox processOrderCombo = new MFXComboBox<>();
    private static MFXButton processOrderButton = new MFXButton();
    AnimationTimer task;
    private OrderDetail order;
    private static Label orderPanelLabel = new Label();
    private static MFXTableColumn<Order> orderIDColumn;
    private static MFXTableColumn<Order> bedrijfColumn;
    private static MFXTableColumn<Order> dateColumn;
    private static MFXTableColumn<Order> statusColumn;

    private VBox pane = new VBox();

    public OrderScreen(BorderPane root, CustomMenu navbar, DomeinController dc, ResourceController rs) {
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
                if (dc.getOrders() != null) {
                    setup();
                    task.stop();
                }
            }
        };

        task.start();
    }

    private void setup() {
        try {
            orderTabel = new DataGrid();
            setupOrderTable();
            setupCombo();
            setupButton();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            orderPanelLabel.setText(LanguageBundle.getString("OrderScreen_order"));
            orderPanelLabel.getStyleClass().add("title");
            orderPanelLabel.setTextAlignment(TextAlignment.CENTER);
            VBox groupOrder = new VBox(orderPanelLabel, orderTabel,processOrderCombo,processOrderButton);
            groupOrder.setAlignment(Pos.CENTER);
            groupOrder.setSpacing(20);
            pane.getChildren().clear();
            pane.getChildren().add(groupOrder);
        }
    }
    private void setupCombo(){
        processOrderCombo.setDisable(false);
        processOrderCombo.setItems(OrderList());
        processOrderCombo.setPrefWidth(280);
        processOrderCombo.setFloatingText(LanguageBundle.getString("OrderScreen_select"));


        processOrderCombo.setOnAction(evt -> {
            rs.playSoundEffect("click");
            order = dc.getOrder((String) processOrderCombo.getValue());
            if(order.getOrderStatus().equals("Geplaatst"))
            processOrderButton.setDisable(false);

            System.out.println(dc.getOrder((String) processOrderCombo.getValue()));
            System.out.println(order);
        });
    }
    private void setupButton(){
        processOrderButton.setText(LanguageBundle.getString("OrderScreen_process"));
        processOrderButton.setDisable(true);
        processOrderButton.setOnAction(evt -> {
            rs.playSoundEffect("click");
            String orderId = (String) processOrderCombo.getValue();
            if(order.getOrderStatus()!=null) {
                if (order.getOrderStatus().equals("verwerkt")) {
                    Alert.showAlert(root, "WARNING", "Already processed", "This Order is already processed. Are you sure?");
                }
                ScreenController.changeToOrderDetailScreen(root, navbar, dc, rs, orderId);
            }
            System.out.println(order.toString());
            System.out.println(order.getOrderStatus());

        });
    }
    private ObservableList<String> OrderList(){
        ObservableList<Order> orders = dc.getOrders();
        ObservableList<String> order = FXCollections.observableArrayList();
        String arr[] = new String[orders.size()];

        for (int i = 0; orders.size()>i;i++){
            if(!Arrays.stream(arr).anyMatch(orders.get(i).getOrderID()::equals)) {
                arr[i] = orders.get(i).getOrderID();
                order.add(orders.get(i).getOrderID());
            }
        }
        return order;
    }
    private void setupOrderTable() {
        orderIDColumn = new MFXTableColumn<>(LanguageBundle.getString("OrderScreen_id"), true, Comparator.comparing(Order::getOrderID));
        bedrijfColumn = new MFXTableColumn<>(LanguageBundle.getString("OrderScreen_company"), true, Comparator.comparing(Order::getNaamKlant));
        dateColumn = new MFXTableColumn<>(LanguageBundle.getString("OrderScreen_date"), true, Comparator.comparing(Order::getOrderDate));
        statusColumn = new MFXTableColumn<>(LanguageBundle.getString("OrderScreen_state"), true, Comparator.comparing(Order::getStatus));

        orderIDColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Order::getOrderID));
        orderIDColumn.setPrefWidth(200);

        bedrijfColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Order::getNaamKlant));
        bedrijfColumn.setPrefWidth(100);



        dateColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Order::getFormattedDate));
        dateColumn.setPrefWidth(200);

        statusColumn.setRowCellFactory(order -> new MFXTableRowCell<>(Order::getStatus));
        statusColumn.setAlignment(Pos.CENTER_RIGHT);
        statusColumn.setPrefWidth(100);


        orderTabel.getTableColumns().addAll(orderIDColumn, bedrijfColumn, dateColumn,  statusColumn);
        orderTabel.getFilters().addAll(
                new StringFilter<>("ID", Order::getOrderID),
                new StringFilter<>("Status", Order::getStatus)

        );
        orderTabel.setItems(dc.getOrders());
    }
    /*
    public static void updateText() {
        orderPanelLabel.setText(LanguageBundle.getString("OrderScreen_order"));
        processOrderButton.setText(LanguageBundle.getString("OrderScreen_process"));
        processOrderCombo.setFloatingText(LanguageBundle.getString("OrderScreen_select"));
        orderIDColumn.setText(LanguageBundle.getString("OrderScreen_id"));
        bedrijfColumn.setText(LanguageBundle.getString("OrderScreen_company"));
        aankoperColumn.setText(LanguageBundle.getString("OrderScreen_purchaser"));
        straatColumn.setText(LanguageBundle.getString("OrderScreen_street"));
        huisnrColumn.setText(LanguageBundle.getString("OrderScreen_nr"));
        trackAndTraceColumn.setText(LanguageBundle.getString("OrderScreen_track"));
        statusColumn.setText(LanguageBundle.getString("OrderScreen_state"));
    }*/
    private static Order getObjectById(ObservableList<Order> list, String orderId) {
        return list.stream()
                .filter(obj -> obj.getOrderID().equals(orderId))
                .findFirst()
                .orElse(null);
    }
}