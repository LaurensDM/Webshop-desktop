package springboot.validator;

public class NameValidator {
    public static boolean nameIsValid(String name) {
        if (name.isBlank()) {
            return false;
        } if (name.length() > 64) {
            return false;
        }
        return true;
    }
}
