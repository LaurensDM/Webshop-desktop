package gui.screens;

import domein.ApiCall;
import domein.AuthProvider;
import domein.DomeinController;
import gui.ScreenController;
import gui.components.LanguageBundle;
import gui.popups.Alert;
import gui.components.CustomMenu;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import resources.ResourceController;

import java.net.ConnectException;

public class LoginScreen extends VBox {

    private BorderPane root;
    private DomeinController dc;
    private ResourceController rs;
    private static MFXButton loginBtn;
    private MFXGenericDialog dialogContent = new MFXGenericDialog();
    private MFXStageDialog dialog = new MFXStageDialog();
    private CustomMenu navbar;
    private AnimationTimer task;
    private boolean serverConnected = false;
    private boolean serverTimedOut = false;
    private static Label loginScreenLabel;
    private static MFXTextField email;
    private static MFXPasswordField password;
    private static MFXCheckbox rememberMe;
    private VBox group1;
    private VBox group2;
    private static MFXButton register;


    /**
     * Instantiates a new Login screen.
     */
    public LoginScreen(BorderPane root, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        this.root = root;
        this.navbar = navbar;
        this.dc = dc;
        this.rs = rs;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(50);

        loginScreenLabel = new Label(LanguageBundle.getString("LoginScreen_login"));
        loginScreenLabel.getStyleClass().add("title");
        loginScreenLabel.setTextAlignment(TextAlignment.CENTER);

        Thread pingServer = new Thread(() -> {
            waitForServer();
            createComponents();
        });
        pingServer.start();

        MFXProgressSpinner spinner = new MFXProgressSpinner();
        this.getChildren().addAll(loginScreenLabel, spinner);

        task = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (serverConnected) {
                    if (dc.getStayLoggedIn() && dc.validatePropertiesToken()) {
                        ScreenController.changeToMainScreen(root, navbar, dc, rs);
                        navbar.update(dc);
                    } else {
                        addChildrenToLoginScreen();
                    }
                    task.stop();
                }
                if (serverTimedOut) {
                    Alert.showAlert(root,
                            "ERROR",
                            "Server timed out",
                            "The server is not responding. Please try again later."
                    );
                    clearChildren();
                    task.stop();
                }
            }
        };
        task.start();
    }

    public static void updateText() {
        loginScreenLabel.setText(LanguageBundle.getString("LoginScreen_login"));
        email.setFloatingText(LanguageBundle.getString("LoginScreen_email"));
        password.setFloatingText(LanguageBundle.getString("LoginScreen_password"));
        rememberMe.setText(LanguageBundle.getString("LoginScreen_remember"));
        loginBtn.setText(LanguageBundle.getString("LoginScreen_login"));
        register.setText(LanguageBundle.getString("LoginScreen_register"));
    }

    private void addChildrenToLoginScreen() {
        clearChildren();
        this.getChildren().addAll(loginScreenLabel, group1, group2);
    }

    private void clearChildren() {
        this.getChildren().clear();
    }

    private void waitForServer() {
        serverTimedOut = !ApiCall.waitForServer();
    }

    private void createComponents() {
        email = new MFXTextField();
        password = new MFXPasswordField();
        email.setSelectable(true);
        password.setSelectable(true);
        password.setAllowPaste(true);

        email.setFloatingText(LanguageBundle.getString("LoginScreen_email"));
        password.setFloatingText(LanguageBundle.getString("LoginScreen_password"));

        email.setPrefWidth(200);
        password.setPrefWidth(200);

        rememberMe = new MFXCheckbox(LanguageBundle.getString("LoginScreen_remember"));
        rememberMe.setSelected(dc.getStayLoggedIn());
        rememberMe.setOnAction(evt -> {
            dc.setStayLoggedIn(rememberMe.isSelected());
        });

        group1 = new VBox(email, password, rememberMe);
        group1.setAlignment(Pos.CENTER);
        group1.setSpacing(20);

        loginBtn = new MFXButton(LanguageBundle.getString("LoginScreen_login"));
        loginBtn.setOnAction(evt -> {
            rs.playSoundEffect("click");
            login(email.getText(), password.getText());
        });

        loginBtn.onKeyPressedProperty().set(evt -> {
            if (evt.getCode().toString().equals("ENTER")) {
                login(email.getText(), password.getText());
            }
        });

        register = new MFXButton(LanguageBundle.getString("LoginScreen_register"));
        register.setOnAction(evt -> {
            rs.playSoundEffect("click");
            ScreenController.changeToRegisterScreen(root, navbar, dc, rs);
        });

        group2 = new VBox(loginBtn, register);
        group2.setAlignment(Pos.CENTER);
        group2.setSpacing(20);
        serverConnected = true;
    }

    private void login(String email, String password) {
        try {
            if (email.isEmpty() || password.isEmpty()) {
                throw new IllegalArgumentException("Please fill in all fields");
            }
            dc.login(email, password);
            ScreenController.changeToMainScreen(root, navbar, dc, rs);
            navbar.update(dc);
        } catch (ConnectException ce) {
            Alert.showAlert(root, "ERROR", "Server Error", String.format("Failed to connect to server: %s", ce.getMessage()));
        } catch (Exception e) {
            Alert.showAlert(root, "ERROR", "Login Error", e.getMessage());
        }
    }

}
