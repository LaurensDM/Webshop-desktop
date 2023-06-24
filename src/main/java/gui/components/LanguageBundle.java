package gui.components;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageBundle {
    private static ResourceBundle messages;
    private static Locale defaultLocale = new Locale("en");

    static {
        messages = ResourceBundle.getBundle("languages/LanguagePack", defaultLocale);
    }

    public static void setLocale(Locale locale) {
        messages = ResourceBundle.getBundle("languages/LanguagePack", locale);
    }

    public static String getString(String key) {
        return messages.getString(key);
    }
}
