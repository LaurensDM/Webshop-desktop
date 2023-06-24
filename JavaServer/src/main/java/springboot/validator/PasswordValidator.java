package springboot.validator;

public class PasswordValidator {
    public static boolean passwordIsValid(String password) {
        if (password.isBlank()) {
            return false;
        } if (password.length() < 8) {
            return false;
        } if (password.length() > 256) {
            return false;
        }
        return true;
    }
}
