package gui.company;

import domein.DomeinController;
import gui.components.LanguageBundle;
import gui.paper.PaperComponent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Company;

public class CompanyCardComponent extends GridPane {
    private static Label nameLabel;
    private static Label logoLabel;
    private static Label vatLabel;
    private static Label streetLabel;
    private static Label streetNumLabel;
    private static Label zipLabel;
    private static Label cityLabel;
    private static Label countryLabel;

    public CompanyCardComponent(DomeinController dc) {
        ColumnConstraints labelCol = new ColumnConstraints();
        labelCol.setPercentWidth(50);
        ColumnConstraints valueCol = new ColumnConstraints();
        valueCol.setPercentWidth(50);
        this.getColumnConstraints().addAll(labelCol, valueCol);

        Label companyLabel = new Label("Company Information");
        companyLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 24));
        companyLabel.setTextAlignment(TextAlignment.CENTER);

        Company company = dc.getCompanyFromAuthProvider();
        Text nameText = new Text(company.getName());

        Text logoText = new Text(company.getLogoImg() != null ? "Available" : "Not Available");
        Text vatText = new Text(company.getCountryCode() + company.getVatNumber());
        Text streetText = new Text(company.getStreet());
        Text streetNumText = new Text(company.getStreetNumber());
        Text zipText = new Text(company.getZipCode());
        Text cityText = new Text(company.getCity());
        Text countryText = new Text(company.getCountry());

        nameText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        logoText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        vatText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        streetText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        streetNumText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        zipText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        cityText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        countryText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

         nameLabel = new Label(LanguageBundle.getString("MainScreen_name"));
         logoLabel = new Label(LanguageBundle.getString("MainScreen_logo"));
         vatLabel = new Label(LanguageBundle.getString("MainScreen_VAT"));
         streetLabel = new Label(LanguageBundle.getString("MainScreen_street"));
         streetNumLabel = new Label(LanguageBundle.getString("MainScreen_streetNumber"));
         zipLabel = new Label(LanguageBundle.getString("MainScreen_Zip"));
         cityLabel = new Label(LanguageBundle.getString("MainScreen_city"));
         countryLabel = new Label(LanguageBundle.getString("MainScreen_country"));

        nameLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        logoLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        vatLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        streetLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        streetNumLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        zipLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        cityLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        countryLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        this.add(nameLabel, 0, 0);
        this.add(nameText, 1, 0);
        this.add(logoLabel, 0, 1);
        this.add(logoText, 1, 1);
        this.add(vatLabel, 0, 2);
        this.add(vatText, 1, 2);
        this.add(streetLabel, 0, 3);
        this.add(streetText, 1, 3);
        this.add(streetNumLabel, 0, 4);
        this.add(streetNumText, 1, 4);
        this.add(zipLabel, 0, 5);
        this.add(zipText, 1, 5);
        this.add(cityLabel, 0, 6);
        this.add(cityText, 1, 6);
        this.add(countryLabel, 0, 7);
        this.add(countryText, 1, 7);
        this.setMaxWidth(300);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
    }
    public static void updateText() {
        nameLabel.setText(LanguageBundle.getString("MainScreen_name"));
        logoLabel.setText(LanguageBundle.getString("MainScreen_logo"));
        vatLabel.setText(LanguageBundle.getString("MainScreen_VAT"));
        streetLabel.setText(LanguageBundle.getString("MainScreen_street"));
        streetNumLabel.setText(LanguageBundle.getString("MainScreen_streetNumber"));
        zipLabel.setText(LanguageBundle.getString("MainScreen_Zip"));
        cityLabel.setText(LanguageBundle.getString("MainScreen_city"));
        countryLabel.setText(LanguageBundle.getString("MainScreen_country"));
    }
}