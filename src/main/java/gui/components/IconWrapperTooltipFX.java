package gui.components;

import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXTooltip;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.util.Duration;

public class IconWrapperTooltipFX extends MFXIconWrapper {

    private String toolTip;

    public IconWrapperTooltipFX(MFXFontIcon icon, int size, String toolTip) {
        super(icon, size);
        this.toolTip = toolTip;
        installTooltipText(toolTip);

    }

    private void installTooltipText(String text) {
        MFXTooltip.of(this, text)
                .install()
                .setShowDelay(Duration.millis(300));
    }
}
