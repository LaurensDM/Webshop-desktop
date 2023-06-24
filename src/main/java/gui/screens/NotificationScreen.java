package gui.screens;

import domein.DomeinController;
import gui.components.DataGrid;
import gui.components.LanguageBundle;
import gui.paper.PaperComponent;
import javafx.scene.control.ScrollPane;
import model.Notification;
import gui.components.CustomMenu;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import mock.Model;
import resources.ResourceController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class NotificationScreen extends ScrollPane {
    private BorderPane root;
    private DomeinController dc;
    private ResourceController rs;

    private DataGrid generalNotificationTabel;
    AnimationTimer task;

    private VBox pane = new VBox();
    private static Label generalNotificationsLabel = new Label();
    private static MFXTableColumn<Notification> audienceColumn;
    private static MFXTableColumn<Notification> subjectColumn;
    private static MFXTableColumn<Notification> dateColumn;
    private static MFXTableColumn<Notification> readByColumn;

    private int timerCount = 0;

    public NotificationScreen(BorderPane screen, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        pane.setAlignment(Pos.CENTER);
        this.dc = dc;
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
                if (Model.notifications != null) {
                    setup();
                    task.stop();
                }
            }
        };
        task.start();
    }

    private void setup() {
        generalNotificationsLabel.setText(LanguageBundle.getString("NotificationScreen_notification"));
        generalNotificationsLabel.getStyleClass().add("title");
        generalNotificationsLabel.setTextAlignment(TextAlignment.CENTER);
        try {
            generalNotificationTabel = new DataGrid();
            setupGeneralNotificationTable();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            VBox groupGeneralNotifications = new VBox(generalNotificationsLabel, generalNotificationTabel);
            groupGeneralNotifications.setAlignment(Pos.CENTER);
            groupGeneralNotifications.setSpacing(20);

            pane.getChildren().clear();
            pane.getChildren().add(groupGeneralNotifications);
        }
    }

    private void setupGeneralNotificationTable() {
        audienceColumn = new MFXTableColumn<>(LanguageBundle.getString("NotificationScreen_audience"), true, Comparator.comparing(Notification::getAudience));
        audienceColumn.setRowCellFactory(notification -> new MFXTableRowCell<>(Notification::getAudience));
        audienceColumn.setPrefWidth(100);

        subjectColumn = new MFXTableColumn<>(LanguageBundle.getString("NotificationScreen_subject"), true, Comparator.comparing(Notification::getSubject));
        subjectColumn.setRowCellFactory(notification -> new MFXTableRowCell<>(Notification::getSubject));
        subjectColumn.setPrefWidth(200);

        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

        dateColumn = new MFXTableColumn<>(LanguageBundle.getString("NotificationScreen_date"), true, Comparator.comparing(Notification::getDate));
        dateColumn.setRowCellFactory(rowData -> new MFXTableRowCell<>(notification -> {
            Date date = null;
            try {
                date = inputFormat.parse(notification.getDate());
                return outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return "Error";
            }

        }));
        dateColumn.setPrefWidth(450);

        readByColumn = new MFXTableColumn<>(LanguageBundle.getString("NotificationScreen_read"), true, Comparator.comparing(Notification::getReadBy));
        readByColumn.setRowCellFactory(notification -> new MFXTableRowCell<>(Notification::getReadBy));
        readByColumn.setPrefWidth(280);


        generalNotificationTabel.getTableColumns().addAll(audienceColumn, subjectColumn, dateColumn, readByColumn);
        generalNotificationTabel.getFilters().addAll(
                new StringFilter<>("Audience", Notification::getAudience),
                new StringFilter<>("Read By", Notification::getReadBy),
                new StringFilter<>("Subject", Notification::getSubject)
        );
        generalNotificationTabel.setItems(dc.getNotifications());
    }
    public static void updateText() {
        generalNotificationsLabel.setText(LanguageBundle.getString("NotificationScreen_notification"));
        audienceColumn.setText(LanguageBundle.getString("NotificationScreen_audience"));
        subjectColumn.setText(LanguageBundle.getString("NotificationScreen_subject"));
        dateColumn.setText(LanguageBundle.getString("NotificationScreen_date"));
        readByColumn.setText(LanguageBundle.getString("NotificationScreen_read"));
    }
}
