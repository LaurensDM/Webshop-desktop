package gui.screens;

import domein.DomeinController;
import gui.ScreenController;
import gui.components.LanguageBundle;
import gui.popups.Alert;
import gui.components.CustomMenu;
import io.github.palexdev.materialfx.controls.*;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import resources.ResourceController;

import java.net.ConnectException;

public class RegisterScreen extends MFXScrollPane {

    private BorderPane root;
    private CustomMenu navbar;
    private DomeinController dc;
    private ResourceController rs;
    private AnimationTimer task;
    private static Label registerScreenLabel = new Label();
    private static MFXTextField username;
    private static MFXTextField email;
    private static MFXPasswordField password;
    private static MFXPasswordField passwordConfirm;
    private static MFXTextField companyVAT;
    private static MFXCheckbox rememberMe;
    private static MFXButton register;
    private static MFXButton back;

    private VBox pane = new VBox();

    /**
     * Instantiates a new Register screen.
     */
    public RegisterScreen(BorderPane root, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        this.root = root;
        this.navbar = navbar;
        this.dc = dc;
        this.rs = rs;
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(50);


        registerScreenLabel.setText(LanguageBundle.getString("RegisterScreen_register"));
        registerScreenLabel.getStyleClass().add("title");
        registerScreenLabel.setTextAlignment(TextAlignment.CENTER);


        username = new MFXTextField();
        username.setFloatingText(LanguageBundle.getString("RegisterScreen_username"));
        username.setPrefWidth(250);

        email = new MFXTextField();
        email.setFloatingText(LanguageBundle.getString("RegisterScreen_email"));
        email.setPrefWidth(250);

        password = new MFXPasswordField();
        password.setFloatingText(LanguageBundle.getString("RegisterScreen_password"));
        password.setPrefWidth(250);
        password.setSelectable(true);
        password.setAllowPaste(true);

        passwordConfirm = new MFXPasswordField();
        passwordConfirm.setFloatingText(LanguageBundle.getString("RegisterScreen_confirm"));
        passwordConfirm.setPrefWidth(250);
        passwordConfirm.setSelectable(true);
        passwordConfirm.setAllowPaste(true);

        companyVAT = new MFXTextField();
        companyVAT.setFloatingText(LanguageBundle.getString("RegisterScreen_vat"));
        companyVAT.setPrefWidth(250);


        rememberMe = new MFXCheckbox(LanguageBundle.getString("RegisterScreen_remember"));
        rememberMe.setSelected(dc.getStayLoggedIn());
        rememberMe.setOnAction(evt -> {
            dc.setStayLoggedIn(rememberMe.isSelected());
        });

        VBox group1 = new VBox(username, email, password, passwordConfirm, companyVAT, rememberMe);
        group1.setAlignment(Pos.CENTER);
        group1.setSpacing(15);


        register = new MFXButton(LanguageBundle.getString("RegisterScreen_register"));
        register.setOnAction(evt -> {
            rs.playSoundEffect("click");
//            dc.register(username.getText(), email.getText(), password.getText(), passwordConfirm.getText());
            register(username.getText(), email.getText(), password.getText(), passwordConfirm.getText(), companyVAT.getText());
        });


        back = new MFXButton(LanguageBundle.getString("RegisterScreen_back"));
        back.setOnAction(evt -> {
            rs.playSoundEffect("click");
            ScreenController.changeToLoginScreen(root, navbar,dc, rs);
        });


        VBox group2 = new VBox(register, back);
        group2.setAlignment(Pos.CENTER);
        group2.setSpacing(20);

        pane.getChildren().addAll(registerScreenLabel, group1, group2);
        this.setContent(pane);
        this.setFitToHeight(true);
        this.setFitToWidth(true);
    }

    private void register(String username, String email, String password, String passwordConfirm, String companyVAT) {
        System.out.printf("INFO -- RegisterScreen.register() -- username: %s, email: %s, password: %s, passwordConfirm: %s, companyVAT: %s%n", username, email, password, passwordConfirm, companyVAT);
        // TODO: after register button is pressed show a loader until the user and company are registered
//        Thread registerAdministratorAndCompany = new Thread(() -> {
//            // TODO: register logic here
//        });
//        registerAdministratorAndCompany.start();
//
//        MFXProgressSpinner spinner = new MFXProgressSpinner();
//        this.getChildren().addAll(spinner);
//        task = new AnimationTimer() {
//            @Override
//            public void handle(long l) {
//                if (userAndCompanyRegistered) {
//                    task.stop();
//                }
//            }
//        };
//        task.start();

        if (username.isBlank()) {
            Alert.showAlert(root,"ERROR", "Username is empty", "Please enter a username.");
            return;
        }

        if (email.isBlank() && dc.checkEmail(email)) {
            Alert.showAlert(root,"ERROR", "Email is invalid or empty", "Please enter a valid email address.");
            return;
        }

        if (password.isBlank()) {
            Alert.showAlert(root,"ERROR", "Password is empty", "Please enter a password.");
            return;
        }

        if (passwordConfirm.isBlank()) {
            Alert.showAlert(root,"ERROR", "Password confirmation is empty", "Please also fill in the password confirmation.");
            return;
        }

        if (companyVAT.isBlank()) {
            Alert.showAlert(root,"ERROR", "VAT number is empty", "Please enter a VAT number.");
            return;
        }

        if (password.equals(passwordConfirm) && !password.isBlank()) {
            String response;
            try {
                response = dc.registerUserAndCompany(username, email, password, companyVAT);
                dc.addNotification(username, "Welcome to the application! You have successfully registered your account.");
            } catch (ConnectException e) {
                Alert.showAlert(root,"ERROR", "Connection error", "Could not connect to the server. Please try again later.");
                return;
            }
            if (response == "success") {
                ScreenController.changeToMainScreen(root, navbar, dc, rs);
                navbar.update(dc);
            } else {
                Alert.showAlert(root,"ERROR", "Registration failed", response);
            }
        } else {
            Alert.showAlert(root, "ERROR", "Passwords mismatch", "The passwords you entered do not match. Please try again.");
        }
    }
    public static void updateText() {
        registerScreenLabel.setText(LanguageBundle.getString("RegisterScreen_register"));
        username.setFloatingText(LanguageBundle.getString("RegisterScreen_username"));
        email.setFloatingText(LanguageBundle.getString("RegisterScreen_email"));
        password.setFloatingText(LanguageBundle.getString("RegisterScreen_password"));
        passwordConfirm.setFloatingText(LanguageBundle.getString("RegisterScreen_confirm"));
        companyVAT.setFloatingText(LanguageBundle.getString("RegisterScreen_vat"));
        rememberMe.setText(LanguageBundle.getString("RegisterScreen_remember"));
        register.setText(LanguageBundle.getString("RegisterScreen_register"));
        back.setText(LanguageBundle.getString("RegisterScreen_back"));
    }
}
