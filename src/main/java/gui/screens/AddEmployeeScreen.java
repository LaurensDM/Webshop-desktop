package gui.screens;

import domein.DomeinController;
import gui.components.LanguageBundle;
import io.github.palexdev.materialfx.controls.*;
import javafx.scene.Node;
import model.Address;
import model.Role;
import model.User;
import gui.ScreenController;
import gui.components.CustomMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Window;
import resources.ResourceController;

import java.util.regex.Pattern;

import static java.lang.Long.parseLong;

public class AddEmployeeScreen extends MFXScrollPane {
    private final BorderPane root;
//    private final CustomMenu navbar;
    private final DomeinController dc;
    private final ResourceController rs;

    private MFXTextField emailTextField, usernameTextField, firstNameTextField,lastNameTextField,
            streetTextField, streetNumberTextField, zipCodeTextField, cityTextField, countryTextField;
    private MFXPasswordField passwordField, passwordConfirmField;

    private MFXComboBox<String> roleComboBox;
    private final GridPane employeeForm;
    private final ObservableList<String> observableRoles = FXCollections.observableArrayList(
            "admin", "warehouseman", "employee", "pending"
    );

    VBox pane = new VBox();

    public AddEmployeeScreen(BorderPane root, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        this.dc = dc;
        this.root = root;
        this.rs = rs;
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(10);
        pane.setPadding(new Insets(25, 25, 25, 25));

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow( Priority.ALWAYS );

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow( Priority.ALWAYS );

//        grid.getColumnConstraints().addAll( new ColumnConstraints( 100 ), col1, new ColumnConstraints( 100 ), col2 );

        Label sceneTitle = new Label(LanguageBundle.getString("AddEmployeeScreen_add"));
        sceneTitle.getStyleClass().add("title");
        sceneTitle.setTextAlignment(TextAlignment.CENTER);

        employeeForm = new GridPane();
        employeeForm.setAlignment(Pos.CENTER);
        employeeForm.setHgap(10);
        employeeForm.setVgap(10);
        employeeForm.setPadding(new Insets(10, 225, 20, 225));

        setupForm(employeeForm);

        MFXButton returnButton = new MFXButton(LanguageBundle.getString("AddEmployeeScreen_return"));
        returnButton.setOnAction(e -> {
            ScreenController.changeToAdministratorScreen(root, navbar, dc, rs);
        });
        MFXButton addEmployeeButton = new MFXButton(LanguageBundle.getString("AddEmployeeScreen_add"));
        handleButton(addEmployeeButton);

        MFXButton resetFormButton = new MFXButton(LanguageBundle.getString("AddEmployeeScreen_reset"));
        resetFormButton.setOnAction(e -> {
            resetForm();
        });

        HBox buttonGroupLeft = new HBox();
        buttonGroupLeft.setSpacing(10);
        buttonGroupLeft.getChildren().addAll(returnButton, addEmployeeButton);
        buttonGroupLeft.setAlignment(Pos.CENTER);

        HBox buttonGroupRight = new HBox();
        buttonGroupRight.setSpacing(10);
        buttonGroupRight.getChildren().addAll(resetFormButton);
        buttonGroupRight.setAlignment(Pos.CENTER);

        pane.getChildren().addAll(sceneTitle, employeeForm, buttonGroupLeft, buttonGroupRight);
        this.setContent(pane);
        this.setFitToHeight(true);
        this.setFitToWidth(true);
    }

    private void setupForm(GridPane grid){
        final int widthInput = 400;
        String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        final Pattern patternEmail = Pattern.compile(regex);
        TextFormatter<?> emailFormatter = new TextFormatter<>(change -> {
            if (patternEmail.matcher(change.getControlNewText()).matches()) {
                emailTextField.setTextFill(Color.BLACK);
                return change;
            } else {
                emailTextField.setTextFill(Color.RED);
                return null;
            }
        });
        TextFormatter<?> passwordFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().length() > 8) {
                passwordField.setTextFill(Color.BLACK);
                return change;
            } else {
                passwordField.setTextFill(Color.RED);
                return null;
            }
        });
        TextFormatter<?> passwordConfirmFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().equals(passwordField.getText())) {
                passwordConfirmField.setTextFill(Color.BLACK);
                return change;
            } else {
                passwordConfirmField.setTextFill(Color.RED);
                return null;
            }
        });

        // User part
        Label employeeLabel = new Label(LanguageBundle.getString("AddEmployeeScreen_details"));
        grid.add(employeeLabel, 0, 0);

        usernameTextField = new MFXTextField();
        usernameTextField.setPrefWidth(widthInput);
        usernameTextField.setFloatingText(LanguageBundle.getString("AddEmployeeScreen_username"));
        grid.add(usernameTextField, 0, 1);

        emailTextField = new MFXTextField();
        emailTextField.setPrefWidth(widthInput);
        emailTextField.setFloatingText(LanguageBundle.getString("AddEmployeeScreen_email"));
        emailTextField.setTextFormatter(emailFormatter);
        grid.add(emailTextField, 1, 1);

        firstNameTextField = new MFXTextField();
        firstNameTextField.setPrefWidth(widthInput);
        firstNameTextField.setFloatingText(LanguageBundle.getString("AddEmployeeScreen_firstname"));
        grid.add(firstNameTextField, 0, 2);

        lastNameTextField = new MFXTextField();
        lastNameTextField.setPrefWidth(widthInput);
        lastNameTextField.setFloatingText(LanguageBundle.getString("AddEmployeeScreen_lastname"));
        grid.add(lastNameTextField, 1, 2);

        // Address part
        Label addressLabel = new Label(LanguageBundle.getString("AddEmployeeScreen_address"));
        grid.add(addressLabel, 0, 3);

        streetTextField = new MFXTextField();
        streetTextField.setFloatingText(LanguageBundle.getString("AddEmployeeScreen_street"));
        streetTextField.setPrefWidth(widthInput);
        grid.add(streetTextField, 0, 4);

        streetNumberTextField = new MFXTextField();
        streetNumberTextField.setFloatingText(LanguageBundle.getString("AddEmployeeScreen_streetnr"));
        streetNumberTextField.setPrefWidth(widthInput);
        grid.add(streetNumberTextField, 1, 4);

        zipCodeTextField = new MFXTextField();
        zipCodeTextField.setFloatingText(LanguageBundle.getString("AddEmployeeScreen_zip"));
        zipCodeTextField.setPrefWidth(widthInput);
        grid.add(zipCodeTextField, 0, 5);

        cityTextField = new MFXTextField();
        cityTextField.setPrefWidth(widthInput);
        cityTextField.setFloatingText(LanguageBundle.getString("AddEmployeeScreen_city"));
        grid.add(cityTextField, 1, 5);

        countryTextField = new MFXTextField();
        countryTextField.setPrefWidth(widthInput);
        countryTextField.setFloatingText(LanguageBundle.getString("AddEmployeeScreen_country"));
        grid.add(countryTextField, 0, 6);

        // Company part
        Label companyLabel = new Label(LanguageBundle.getString("AddEmployeeScreen_company"));
        companyLabel.setPadding(new Insets(20, 0, 20, 0));
        grid.add(companyLabel, 0, 7);

        roleComboBox = new MFXComboBox<>();
        roleComboBox.setItems(observableRoles);
        roleComboBox.setPrefWidth(widthInput);
        roleComboBox.setFloatingText(LanguageBundle.getString("AddEmployeeScreen_select"));
        grid.add(roleComboBox, 1, 7);

        // Password part
        Label passwordLabel = new Label(LanguageBundle.getString("AddEmployeeScreen_passwordDetails"));
        grid.add(passwordLabel, 0, 8);

        passwordField = new MFXPasswordField();
        passwordField.setPrefWidth(widthInput);
        passwordField.setFloatingText(LanguageBundle.getString("AddEmployeeScreen_password"));
        passwordField.allowPasteProperty().setValue(true);
        passwordField.setTextFormatter(passwordFormatter);
        grid.add(passwordField, 0, 9);

        passwordConfirmField = new MFXPasswordField();
        passwordConfirmField.setPrefWidth(widthInput);
        passwordConfirmField.setFloatingText(LanguageBundle.getString("AddEmployeeScreen_confirm"));
        passwordConfirmField.allowPasteProperty().setValue(true);
        passwordConfirmField.setTextFormatter(passwordConfirmFormatter);
        grid.add(passwordConfirmField, 1, 9);
    }

    private void handleButton(MFXButton btn){
        btn.setOnAction(e -> {
            //todo:input velden doorsturen naar back end
            if(firstNameTextField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, employeeForm.getScene().getWindow(), "Form Error!", LanguageBundle.getString("ErrorsAddAdminScreen_firstname"));
                return;
            }
            if(lastNameTextField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, employeeForm.getScene().getWindow(), "Form Error!", LanguageBundle.getString("ErrorsAddAdminScreen_lastname"));
                return;
            }
            if(emailTextField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, employeeForm.getScene().getWindow(), "Form Error!", LanguageBundle.getString("ErrorsAddAdminScreen_email"));
                return;
            }
            if (passwordField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, employeeForm.getScene().getWindow(), "Form Error!", LanguageBundle.getString("ErrorsAddAdminScreen_password"));
                return;
            }
            if (passwordField.getText().length() < 8) {
                showAlert(Alert.AlertType.ERROR, employeeForm.getScene().getWindow(), "Form Error!", LanguageBundle.getString("ErrorsAddAdminScreen_8characters"));
                return;
            }
            if (!passwordField.getText().equals(passwordConfirmField.getText())) {
                showAlert(Alert.AlertType.ERROR, employeeForm.getScene().getWindow(), "Form Error!", LanguageBundle.getString("ErrorsAddAdminScreen_notMatch"));
                return;
            }
            if (roleComboBox.getValue() == null) {
                showAlert(Alert.AlertType.ERROR, employeeForm.getScene().getWindow(), "Form Error!", LanguageBundle.getString("ErrorsAddAdminScreen_select"));
                return;
            }
            try {
                String name = usernameTextField.getText();
                String firstName = firstNameTextField.getText();
                String lastName = lastNameTextField.getText();
                String email = emailTextField.getText();
                Role role = Role.valueOf(roleComboBox.getValue());
                String password = passwordField.getText();

                Address newEmployeeAddress = new Address(
                        streetTextField.getText(),
                        streetNumberTextField.getText(),
                        zipCodeTextField.getText(),
                        cityTextField.getText(),
                        countryTextField.getText()
                );

                User newLocalEmployee = new User(
                        name,
                        firstName,
                        lastName,
                        email,
                        role,
                        newEmployeeAddress
                );
                String response = dc.addEmployeeToCompany(newLocalEmployee, password);
                if (response.equals("success")) {
                    showAlert(
                            Alert.AlertType.CONFIRMATION,
                            employeeForm.getScene().getWindow(),
                            "Employee created!",
                            String.format(
                                    "Employee %s (%s) with role %s has been added to the company",
                                    name,
                                    email,
                                    role
                            )
                    );
                    resetForm();
                } else {
                    gui.popups.Alert.showAlert(root,"ERROR", LanguageBundle.getString("ErrorsAddAdminScreen_creation"), response);
                }
            }
            catch (Exception ex)
            {
                showAlert(Alert.AlertType.ERROR, employeeForm.getScene().getWindow(), "Form Error!", ex.getMessage());
            }


        });

    }
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    // TODO: reset not yet working
    private void resetForm() {
        for (Node node : employeeForm.getChildren()) {
            if (node instanceof MFXTextField) {
                ((MFXTextField) node).setText("");
            }
            if (node instanceof MFXPasswordField) {
                ((MFXPasswordField) node).setText("");
            }
            if (node instanceof MFXComboBox) {
                ((MFXComboBox) node).setValue(null);
            }
        }

    }
}
