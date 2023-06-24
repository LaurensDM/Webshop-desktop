package gui.screens;

import domein.DomeinController;
import gui.ScreenController;
import gui.components.DataGrid;
import gui.components.LanguageBundle;
import gui.popups.Alert;
import gui.components.CustomMenu;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.StringUtils;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.StringConverter;
import model.Role;
import model.User;
import resources.ResourceController;

import java.io.IOException;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

public class AdministratorScreen extends MFXScrollPane {
    private BorderPane root;
    private CustomMenu navbar;
    private DomeinController dc;
    private ResourceController rs;

    private boolean loadingData = true;
    private Label administratorScreenLabel;
    private VBox groupEmployees;
    private DataGrid employeeTabel = new DataGrid();
    private MFXFilterComboBox<User> employeeEmailsComboBox = new MFXFilterComboBox<>();
    private MFXComboBox<String> roleComboBox;
    private MFXButton changeRoleButton;
    private MFXButton VoegToeButton;
    private AnimationTimer task;

    private VBox pane = new VBox();
    private final ObservableList<String> observableRoles = FXCollections.observableArrayList(
            "admin", "employee", "warehouseman", "pending", "unemployed"
    );

    private ObservableList<User> observableEmployees = FXCollections.observableArrayList();

    public AdministratorScreen(BorderPane root, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        pane.setAlignment(Pos.CENTER);
        this.root = root;
        this.rs = rs;
        this.dc = dc;
        this.navbar = navbar;
        administratorScreenLabel = new Label(LanguageBundle.getString("AdministratorScreen_admin"));
        administratorScreenLabel.getStyleClass().add("title");
        administratorScreenLabel.setTextAlignment(TextAlignment.CENTER);

        Thread loadData = new Thread(() -> {
            createComponents();
//            try {
//                Thread.sleep(5000);
//
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
            getEmployeeData();
        });
        loadData.start();

        MFXProgressSpinner spinner = new MFXProgressSpinner();
        pane.getChildren().add(spinner);
        this.setContent(pane);
        this.setFitToHeight(true);
        this.setFitToWidth(true);
        task = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (!loadingData) {
                    addChildrenToAdministratorScreen();
                    task.stop();
                }
            }
        };
        task.start();

    }

    private void getEmployeeData() {
        observableEmployees = dc.getEmployees();
        employeeTabel.setItems(observableEmployees);
        employeeEmailsComboBox.setItems(observableEmployees);
        loadingData = false;
    }

    private void createComponents() {
        createEmployeeTableComponent();
        createEmployeeComboboxComponent();
        createRoleComboboxComponent();
        createChangeRoleButtonComponent();
        createAddEmployeeButtonComponent();

        groupEmployees = new VBox(administratorScreenLabel, employeeTabel, employeeEmailsComboBox, roleComboBox, changeRoleButton, VoegToeButton);
        groupEmployees.setAlignment(Pos.CENTER);
        groupEmployees.setSpacing(20);
    }

    private void addChildrenToAdministratorScreen() {
        pane.getChildren().clear();
        pane.getChildren().add(groupEmployees);
    }

    private void createEmployeeTableComponent() {
        MFXTableColumn<User> usernameColumn = new MFXTableColumn<>(LanguageBundle.getString("AdministratorScreen_alias"), true, Comparator.comparing(User::getName));
        MFXTableColumn<User> firstNameColumn = new MFXTableColumn<>(LanguageBundle.getString("AdministratorScreen_firstname"), true, Comparator.comparing(User::getFirstName));
        MFXTableColumn<User> lastNameColumn = new MFXTableColumn<>(LanguageBundle.getString("AdministratorScreen_lastname"), true, Comparator.comparing(User::getLastName));
        MFXTableColumn<User> emailColumn = new MFXTableColumn<>(LanguageBundle.getString("AdministratorScreen_email"), true, Comparator.comparing(User::getEmail));
        MFXTableColumn<User> roleColumn = new MFXTableColumn<>(LanguageBundle.getString("AdministratorScreen_role"), true, Comparator.comparing(User::getRole));

        usernameColumn.setRowCellFactory(user -> new MFXTableRowCell<>(User::getName));
        usernameColumn.setPrefWidth(100);

        firstNameColumn.setRowCellFactory(user -> new MFXTableRowCell<>(User::getFirstName));
        firstNameColumn.setPrefWidth(100);

        lastNameColumn.setRowCellFactory(user -> new MFXTableRowCell<>(User::getLastName));
        lastNameColumn.setPrefWidth(200);

        emailColumn.setRowCellFactory(user -> new MFXTableRowCell<>(User::getEmail));
        emailColumn.setPrefWidth(280);

        roleColumn.setRowCellFactory(user -> new MFXTableRowCell<>(User::getRole));
        roleColumn.setAlignment(Pos.CENTER_RIGHT);
        roleColumn.setPrefWidth(130);

        employeeTabel.getSelectionModel().setAllowsMultipleSelection(false);
        employeeTabel.onMouseClickedProperty().set(event -> {
            System.out.println("Table clicked");
            employeeTabel.getSelectionModel().clearSelection();
        });

        employeeTabel.setTableRowFactory(user -> new MFXTableRow<>(employeeTabel, user) {{
            this.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
//                System.out.println(employeeTabel.getSelectionModel().getSelection().values());
//                System.out.println(employeeTabel.getSelectionModel().getSelection().keySet().stream().toList());
                Object object = employeeTabel.getSelectionModel().getSelection().values().stream().toList().get(0);
//                employeeTabel.getChildrenUnmodifiable();
//                System.out.println(object);
//                System.out.println(employeeEmailsComboBox.getItems().indexOf(object));
                int index = employeeEmailsComboBox.getItems().indexOf(object);
                employeeEmailsComboBox.getSelectionModel().selectIndex(employeeEmailsComboBox.getItems().indexOf(object));
            });
        }});
        MFXTableRow row = new MFXTableRow<>(employeeTabel, null);
        row.setMouseTransparent(false);

        employeeTabel.getTableColumns().addAll(usernameColumn, firstNameColumn, lastNameColumn, emailColumn, roleColumn);
        employeeTabel.getFilters().addAll(
                new StringFilter<>("Name", User::getName),
                new StringFilter<>("Email", User::getEmail),
                new StringFilter<>("Role", User::getRole));
    }

    private void createEmployeeComboboxComponent() {
        employeeEmailsComboBox.setFloatingText(LanguageBundle.getString("AdministratorScreen_select"));
        employeeEmailsComboBox.setPrefWidth(280);
        employeeEmailsComboBox.setPromptText(LanguageBundle.getString("AdministratorScreen_selectAE"));
        StringConverter<User> converter = FunctionalStringConverter.to(user -> (user == null) ? "" : user.getEmail());
        Function<String, Predicate<User>> filterFunction = s -> user -> StringUtils.containsIgnoreCase(converter.toString(user), s);
        employeeEmailsComboBox.setConverter(converter);
        employeeEmailsComboBox.setFilterFunction(filterFunction);
        employeeEmailsComboBox.setOnAction(evt -> {
            rs.playSoundEffect("click");
            ObservableList<String> filteredRoles = FXCollections.observableArrayList(observableRoles);
            filteredRoles.remove(employeeEmailsComboBox.getValue().getRole());
            roleComboBox.setItems(filteredRoles);
            roleComboBox.setDisable(false);
            // TODO: this will probably not work with multiple pages
            employeeTabel.scrollTo(employeeEmailsComboBox.getSelectionModel().getSelectedIndex());
            employeeTabel.getSelectionModel().selectIndex(employeeEmailsComboBox.getSelectionModel().getSelectedIndex());
        });
    }

    private void createRoleComboboxComponent() {
        roleComboBox = new MFXComboBox<>();
        roleComboBox.setDisable(true);
        roleComboBox.setItems(observableRoles);
        roleComboBox.setPromptText(LanguageBundle.getString("AdministratorScreen_selectAR"));
        roleComboBox.setPrefWidth(280);
        roleComboBox.setFloatingText(LanguageBundle.getString("AdministratorScreen_change"));


        roleComboBox.setOnAction(evt -> {
            rs.playSoundEffect("click");
            changeRoleButton.setDisable(false);
        });
    }

    private void createChangeRoleButtonComponent() {
        changeRoleButton = new MFXButton(LanguageBundle.getString("AdministratorScreen_change"));
        changeRoleButton.setDisable(true);
        changeRoleButton.setOnAction(evt -> {
            rs.playSoundEffect("click");
            String role = roleComboBox.getValue();
            User user = employeeEmailsComboBox.getValue();
            try {
                dc.changeRole(user, role);
                this.observableEmployees.remove(user);
                user.setRole(Role.valueOf(role));
                this.observableEmployees.add(user);
                employeeTabel.setItems(observableEmployees);
                Alert.showAlert(root, "mfx-info-dialog", LanguageBundle.getString("AdministratorScreen_roleChanged"),
                        LanguageBundle.getString("AdministratorScreen_roleChangedTo") + role);
            } catch (IOException ioe) {
                Alert.showAlert(root, "mfx-error-dialog", "Failed to change role",
                        String.format("Failed to change role to %s for %s: %s", role, user.getEmail(), ioe.getMessage()));
            }
        });
    }

    private void createAddEmployeeButtonComponent() {
        VoegToeButton = new MFXButton(LanguageBundle.getString("AdministratorScreen_add"));
        VoegToeButton.setOnAction(evt -> {
            ScreenController.changeToAddEmployeeScreen(root, navbar, dc, rs);
        });
    }

    private void createEditEmployeeButtonComponent() {
        VoegToeButton = new MFXButton("Edit employee");
        VoegToeButton.setOnAction(evt -> {
            ScreenController.changeToAddEmployeeScreen(root, navbar, dc, rs);
        });
    }
}

