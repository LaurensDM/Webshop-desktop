package gui.popups;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.mfxresources.fonts.IconDescriptor;
import io.github.palexdev.mfxresources.fonts.IconsProviders;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import io.github.palexdev.mfxresources.fonts.fontawesome.FontAwesomeRegular;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Map;

public class Alert {
    private final static String ERROR = "ERROR";
    private final static String INFO = "INFO";
    private final static String CONFIRMATION = "CONFIRMATION";

//    public Alert(MFXGenericDialog dialogContent, MFXStageDialog dialog) {
//        this.dialogContent = dialogContent;
//        this.dialog = dialog;
//    }

    public static void showAlert(Pane root, String alertType, String title, String message) {
        MFXGenericDialog dialogContent = new MFXGenericDialog();
        MFXStageDialog dialog = new MFXStageDialog();
        dialog.setOwnerNode(root);

        dialogContent = MFXGenericDialogBuilder.build()
                .setContentText(message)
                .makeScrollable(true)
                .get();
        dialog = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .setOwnerNode(root)
                .setDraggable(true)
                .setScrimOwner(true)
                .get();
        MFXStageDialog finalDialog = dialog;
        dialogContent.setHeaderText(title);

        MFXFontIcon icon = new MFXFontIcon();
        icon.setIconsProvider(IconsProviders.FONTAWESOME_REGULAR);
        if (alertType.equals(CONFIRMATION)) {
            dialogContent.addActions(
                    Map.entry(new MFXButton("Confirm"), event -> {
                        finalDialog.close();
                    }),
                    Map.entry(new MFXButton("Cancel"), event -> finalDialog.close())
            );
            icon.setDescription(FontAwesomeRegular.CIRCLE_CHECK.getDescription());
            icon.setColor(Color.GREEN);
        } else if (alertType.equals(ERROR)) {
            dialogContent.addActions(
                    Map.entry(new MFXButton("Close"), event -> finalDialog.close())
            );
            icon.setDescription(FontAwesomeRegular.CIRCLE_XMARK.getDescription());
            icon.setColor(Color.RED);
        } else if (alertType.equals(INFO)) {
            dialogContent.addActions(
                    Map.entry(new MFXButton("Close"), event -> finalDialog.close())
            );
            icon.setDescription(FontAwesomeRegular.NOTE_STICKY.getDescription());
            icon.setColor(Color.BLUE);
        }
        dialogContent.onKeyPressedProperty().set(event -> {
            if (event.getCode().toString().equals("ESCAPE")) {
                finalDialog.close();
            }
        });


        icon.setSize(30);
        dialogContent.setContentText(message);
        dialogContent.setHeaderIcon(icon);
        dialog.setTitle(title);
        dialog.showDialog();
    }
}
