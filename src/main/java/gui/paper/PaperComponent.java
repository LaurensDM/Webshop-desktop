package gui.paper;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PaperComponent extends StackPane {

    private static final String FONT_FAMILY = "Tahoma";
    private static final double FONT_SIZE = 24;
    private static final FontWeight FONT_WEIGHT = FontWeight.BOLD;
    private StringProperty text;

    public PaperComponent(String title) {
        this.setId("PaperComponent");
        Label titleLabel = new Label(title);
//        titleLabel.setFont(Font.font(FONT_FAMILY, FONT_WEIGHT, FONT_SIZE));
//        titleLabel.setTextFill(Color.WHITE);
        this.setMaxWidth(200);
        StackPane.setAlignment(titleLabel, Pos.CENTER);
        this.getChildren().add(titleLabel);
    }
}
