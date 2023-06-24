package gui.screens;

import domein.DomeinController;
import gui.ScreenController;
import gui.company.CompanyCardComponent;
import gui.components.CustomMenu;
import gui.components.LanguageBundle;
import gui.paper.PaperComponent;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import resources.FileUpload;
import resources.ResourceController;

import java.io.File;

public class MainScreen extends MFXScrollPane {

    private BorderPane root;
    private CustomMenu navbar;
    private DomeinController dc;
    private ResourceController rs;

    private FileChooser fileChooser;

    private VBox pane = new VBox();
    private static PaperComponent mainScreen;
    private static MFXButton settingsBtn;
    private static MFXButton myProductsBtn;
    private static MFXButton btn;


    public MainScreen(BorderPane root, CustomMenu navbar, DomeinController dc, ResourceController rs) {
        this.root = root;
        this.dc = dc;
        this.rs = rs;

//        this.setAlignment(Pos.CENTER);
        pane.setAlignment(Pos.TOP_CENTER);
        pane.setPadding(new Insets(20, 0, 20, 0));

//        fileChooser = new FileChooser();
//        fileChooser.setTitle("Select an image");
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg","*.jpeg", "*.avif", "*.bmp", "*.gif", "*.jfif", "*.pjpeg", "*.pjp", "*.svg", "*.webp"));

//        btn = new MFXButton(LanguageBundle.getString("MainScreen_file"));
//        btn.setOnAction((evt) -> {
//            File file = fileChooser.showOpenDialog(root.getScene().getWindow());
//            try {
//                System.out.println(file.getAbsolutePath());
//                System.out.println(file.getName());
//                new FileUpload(file, "http://localhost:9000/api/company/logo").call();
//            } catch(NullPointerException ne) {
//                System.err.println("No file selected");
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new RuntimeException(e);
//            }
//        });


        VBox wrapper = new VBox();
        wrapper.setAlignment(Pos.TOP_CENTER);
        wrapper.setSpacing(50);
        HBox buttonWrapper = new HBox();
        buttonWrapper.setAlignment(Pos.TOP_CENTER);
        buttonWrapper.setSpacing(50);
//        pane.setMaxWidth(500);
         myProductsBtn = new MFXButton(LanguageBundle.getString("MainScreen_products"));
         settingsBtn = new MFXButton(LanguageBundle.getString("MainScreen_settings"));
         mainScreen = new PaperComponent(LanguageBundle.getString("MainScreen_mainScreen"));

//        mainScreen.setId("mainScreen");
        myProductsBtn.setOnAction((evt) -> {
            rs.playSoundEffect(("click"));
            ScreenController.changeToProductScreen(root, navbar, dc, rs);
        });
        settingsBtn.setOnAction((evt) -> {
            rs.playSoundEffect(("click"));
            ScreenController.changeToSettingScreen(root, navbar, dc, rs);
        });

        Label permissionLabel = new Label(dc.getPermission());
        Label usernameLabel = new Label(dc.getUserName());
        Label emailLabel = new Label(dc.getUserEmail());
        permissionLabel.setTextAlignment(TextAlignment.LEFT);
        usernameLabel.setTextAlignment(TextAlignment.RIGHT);
        emailLabel.setTextAlignment(TextAlignment.RIGHT);
        buttonWrapper.getChildren().addAll(myProductsBtn, settingsBtn);

        CompanyCardComponent companyCardComponent = new CompanyCardComponent(dc);


        wrapper.getChildren().addAll(permissionLabel, emailLabel, usernameLabel, companyCardComponent);
        pane.getChildren().addAll(mainScreen, wrapper, buttonWrapper);

        this.setContent(pane);
        this.setFitToHeight(true);
        this.setFitToWidth(true);
    }
    public static void updateText() {
        btn.setText(LanguageBundle.getString("MainScreen_file"));
         myProductsBtn.setText(LanguageBundle.getString("MainScreen_products"));
         settingsBtn.setText(LanguageBundle.getString("MainScreen_settings"));
        mainScreen = new PaperComponent(LanguageBundle.getString("MainScreen_mainScreen"));
    }
}
