package springboot.validator;

import java.util.Arrays;
import java.util.List;

public class RoleValidator {
    public static boolean roleIsValid(String role) {
        List<String> validRoles = Arrays.asList("admin", "employee", "warehouseman", "pending");
        if (role.isBlank()) {
            return false;
        }
        return validRoles.contains(role);
    }
}
