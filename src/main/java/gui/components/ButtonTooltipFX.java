package gui.components;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTooltip;
import javafx.util.Duration;

public class ButtonTooltipFX extends MFXButton {

    private String toolTip;

    public ButtonTooltipFX(String text, String toolTip) {
        super(text);
        this.toolTip = toolTip;
        installTooltipText(toolTip);

    }

    private void installTooltipText(String text) {
        MFXTooltip.of(this, text)
                .install()
                .setShowDelay(Duration.millis(300));
    }

}
