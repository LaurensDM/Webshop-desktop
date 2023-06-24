package resources;

import io.github.palexdev.materialfx.MFXResourcesLoader;
import io.github.palexdev.materialfx.css.themes.Theme;
import javafx.scene.paint.Color;

public enum CustomThemes implements Theme {
    CUSTOM("/css/main.css");
    private final String theme;

    CustomThemes(String theme) {
        this.theme = theme;
    }
    /**
     * @return the theme's path/url
     */
    @Override
    public String getTheme() {
        return theme;
    }
    /**
     * Implementations of this should return the loaded theme as a String.
     */
    @Override
    public String loadTheme() {
        if (Helper.isCached(this)) return Helper.getCachedTheme(this);
        return Helper.cacheTheme(this);
    }
}
