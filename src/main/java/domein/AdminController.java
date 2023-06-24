package domein;

import model.Address;
import model.Role;
import model.User;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;

public class AdminController {
    private DomeinController dc;
    private AuthProvider authProvider;

    public AdminController(DomeinController dc, AuthProvider authProvider) {
        this.dc = dc;
        this.authProvider = authProvider;
    }

    public ArrayList<User> getEmployees(String token) {
        System.out.println("INFO -- AdminController.getEmployees()");
        JSONObject jsonObject = null;
        try {
            jsonObject = ApiCall.get("/user/all", token);
        } catch (Exception e) {
            throw new RuntimeException((String) jsonObject.get("error"));
        }
        ArrayList<User> employees = new ArrayList<>();
        for (Object o : (ArrayList) jsonObject.get("employees")) {
            JSONObject jsonUser = (JSONObject) o;
            employees.add(new User (
                    (String) jsonUser.get("id"),
                    (String) jsonUser.get("name"),
                    (String) jsonUser.get("firstName"),
                    (String) jsonUser.get("lastName"),
                    (String) jsonUser.get("email"),
                    Integer.getInteger(String.valueOf(jsonUser.get("companyId"))),
                    Role.valueOf((String) jsonUser.get("role"))
            ));
        }
        return employees;
    }

//    public static void addEmployee(String token, String name, String firstName, String lastName, String email, String password, String role) {
//        System.out.println("INFO -- AdminController.addEmployee() -- token: " + token);
//        JSONObject jsonObject = null;
//        try {
//            jsonObject = ApiCall.post("/user/add", true, token, "name", name, "firstName", firstName, "lastName", lastName, "email", email, "password", password, "role", role);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(jsonObject);
//    }

    public void changeRoleEmployee(User user, String role, String token) throws IOException {
        System.out.printf("INFO -- AdminController.changeRoleEmployee() -- email: %s originalRole: %s -> role: %s%n", user.getEmail(), user.getRole(), role);
        System.out.println("\tUUID:" + user.getId());

        JSONObject tempUser = new JSONObject();
        tempUser.put("id", user.getId());
        tempUser.put("name", user.getName());
        tempUser.put("firstName", user.getFirstName());
        tempUser.put("lastName", user.getLastName());
        tempUser.put("email", user.getEmail());
        tempUser.put("role", role);
        tempUser.put("companyId", user.getCompanyId());

        ApiCall.put(String.format("/user/%s", user.getId()), tempUser, token);
        user.setRole(Role.valueOf(role));

        System.out.printf("\tChanged role for email: %s to %s%n", user.getEmail(), user.getRole());
    }

    public String addEmployeeToCompany(User user, String password, String token) {
        System.out.printf("INFO -- AdminController.createEmployee() -- email: %s%n", user.getEmail());
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("name", user.getName());
        jsonUser.put("firstName", user.getFirstName());
        jsonUser.put("lastName", user.getLastName());
        jsonUser.put("email", user.getEmail());
        jsonUser.put("password", password);
        jsonUser.put("role", user.getRole());

        jsonUser.put("street", user.getAddress().getStreet());
        jsonUser.put("streetNumber", user.getAddress().getStreetNumber());
        jsonUser.put("zipCode", user.getAddress().getZipCode());
        jsonUser.put("city", user.getAddress().getCity());
        jsonUser.put("country", user.getAddress().getCountry());

        JSONObject response = null;
        try {
            response = ApiCall.post("/user/", jsonUser, token);
        } catch (InterruptedException e) {
            System.out.println("ERROR -- AuthProvider.register() -- " + e.getMessage());
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            System.out.println("ERROR -- AuthProvider.register() -- " + e.getMessage());
            e.printStackTrace();
        }

        if (response.get("error") != null) {
            return (String) response.get("error");
        }

        System.out.printf("\tCreated jsonUser: %s%n", user);
        dc.observableEmployees.add(user);
        return "success";
    }
}
