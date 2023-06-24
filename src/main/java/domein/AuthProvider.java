package domein;

import main.Main;
import model.Company;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.commons.validator.EmailValidator;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

public class AuthProvider {
    // this has to keep the token of the user that is logged in
    private String token;
    private Boolean stayLoggedIn =  java.util.Objects.equals(Main.settingsProps.get("user.stayLoggedIn"), "true");
    private StateController currentState = new LoggedOutState(this);
    // TODO just store a user object here instead of all these fields
    private String email;
    private String name;
    private String firstName;
    private String lastName;
    private String permission;
    private Integer companyId;

    private final CompanyController companyController = new CompanyController();

    private Company company;

    public String getToken() {
        System.out.println("INFO -- AuthProvider.getToken() -- token: " + token);
        return token;
    }

    public void setToken(String token) {
        System.out.println("INFO -- AuthProvider.setToken() -- token: " + token);
        this.token = token;
        if (stayLoggedIn) {
            Main.settingsProps.setProperty("user.token", token);
            System.out.printf("INFO -- AuthProvider.setToken() -- saving properties file: %s%n", Main.settingsProps.get("user.token"));
            Main.saveProperties();
        }
    }

    public Boolean getStayLoggedIn() {
        System.out.printf("INFO -- AuthProvider.getStayLoggedIn() -- stayLoggedIn: local=%s properties=%s%n", stayLoggedIn, Main.settingsProps.get("user.stayLoggedIn"));
        return stayLoggedIn;
    }

    public void setStayLoggedIn(Boolean stayLoggedIn) {
        this.stayLoggedIn = stayLoggedIn;
        Main.settingsProps.setProperty("user.stayLoggedIn", stayLoggedIn.toString());
        System.out.printf("INFO -- AuthProvider.setStayLoggedIn() -- saving properties file: %s%n", Main.settingsProps.get("user.stayLoggedIn"));
        Main.saveProperties();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void login(String email, String password) throws ConnectException {
        JSONObject response = null;
        try {
            response = ApiCall.login("/user/login", email, password);
            currentState.login();
            setCurrentState(new LoggedInState(this));
        } catch (ConnectException ce) {
            System.out.println("ERROR -- AuthProvider.login() -- " + ce.getMessage());
            throw new ConnectException("Connection error");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR -- AuthProvider.login() -- " + e.getMessage());
            throw new RuntimeException("Invalid credentials");
        }

        String token = (String) response.get("token");
        setToken(token);
        setLocalDecodedUser(token);
        Company company = companyController.getCompanyByToken(getCompanyId(), getToken());
        setCompany(company);
    }

    public void logout() {
        currentState.logout();
        Main.settingsProps.setProperty("user.token", "");
        Main.settingsProps.setProperty("user.stayLoggedIn", "false");
        Main.saveProperties();
        setCurrentState(new LoggedOutState(this));
    }

    public String registerUserAndCompany(String name, String email, String password, String companyVAT) throws ConnectException {
        System.out.printf("INFO -- AuthProvider.registerUserAndCompany() -- name: %s email: %s password: %s companyVAT: %s %n", name, email, "***********", companyVAT);
        JSONObject response = null;
        JSONObject postBody = new JSONObject();
        postBody.put("name", name);
        postBody.put("email", email);
        postBody.put("password", password);
        postBody.put("companyVAT", companyVAT);

        try {
            response = ApiCall.post("/user/register", postBody, null);
        } catch (ConnectException ce) {
            System.out.println("ERROR -- AuthProvider.register() -- " + ce.getMessage());
            throw new ConnectException("Connection error");
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
        String token = (String) response.get("token");
        currentState.login();
        setCurrentState(new LoggedInState(this));
        setToken(token);
        System.out.printf("\tToken generated and set in AuthProvider for new registered user %s%n", getToken());

        setLocalDecodedUser(token);

        Company company = companyController.getCompanyByToken(getCompanyId(), getToken());
        setCompany(company);
        return "success";
    }

    public boolean authenticated() {
        return currentState.authenticated();
    }

    public void setCurrentState(StateController currentState) {
        this.currentState = currentState;
    }

    private JSONObject decodeToken(String token) {
        System.out.printf("INFO -- AuthProvider.decodeToken() -- token: %s%n", token);
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        JSONParser parse = new JSONParser();
        JSONObject data_obj;
        try {
            data_obj = (JSONObject) parse.parse(payload);
        } catch (ParseException e) {
            throw new RuntimeException("Invalid token");
        }
        return data_obj;
    };

    private void setLocalDecodedUser(String token) {
        JSONObject decodedUser = decodeToken(token);
        setEmail(decodedUser.get("email").toString());
        setName(decodedUser.get("name").toString());
        setFirstName(decodedUser.get("firstName").toString());
        setLastName(decodedUser.get("lastName").toString());
        setPermission(decodedUser.get("role").toString());
        setCompanyId(Integer.parseInt(decodedUser.get("companyId").toString()));
    }

    public boolean checkEmail(String email) {
        String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        if (email.isBlank()) {return false;}
        if (email.length() > 128) {return false;}
        // Not sure if this regex works
        return email.matches(emailRegex);
    }

    // should be an alternative to login method!
    public boolean validatePropertiesToken() {
        System.out.printf("INFO -- AuthProvider.validatePropertiesToken()%n");
        Object propertiesToken = Main.settingsProps.get("user.token");
        System.out.printf("\tFound token in properties: %s%n", propertiesToken);
        if (Objects.nonNull(propertiesToken)) {
            if (String.valueOf(propertiesToken).isBlank()) {
                System.out.printf("\tToken is blank%n");
                return false;
            } else {
//                currentState.login();
                JSONObject decodedToken = decodeToken(String.valueOf(propertiesToken));
                System.out.println(decodedToken);
                System.out.printf("\tdecodedToken: %s%n", decodedToken);
                if (decodedToken.get("exp") != null) {
                    System.out.println(decodedToken.get("exp"));
                    Date exp = new Date((long) decodedToken.get("exp")*1000);
                    Date iat = new Date((long) decodedToken.get("iat")*1000);
                    Date now = new Date();
//                    long exp = Long.parseLong(decodedToken.get("exp").toString());
//                    long iat = Long.parseLong(decodedToken.get("iat").toString());
//                    long now = System.currentTimeMillis();
                    System.out.printf("\texp: %s iat: %s now: %s%n", exp, iat, now);
                    if (exp.before(now) || now.before(iat)) {
                        System.out.printf("WARN -- AuthProvider.validatePropertiesToken() -- Token from properties expired or is not yet valid%n");
                        System.out.printf("\texp: %s iat: %s now: %s%n", exp, iat, now);
                        System.out.printf("\tReset token in properties%n");
                        setToken("");
                    } else {
                        System.out.printf("INFO -- AuthProvider.validatePropertiesToken() -- Token from properties is still valid%n");
                        setCurrentState(new LoggedInState(this));
                        setLocalDecodedUser(String.valueOf(propertiesToken));
                        setToken(String.valueOf(propertiesToken));

                        Company company = companyController.getCompanyByToken(getCompanyId(), getToken());
                        setCompany(company);
                        return true;
                    }
                }
            }
        } else {
            System.out.printf("WARN -- AuthProvider.checkIfTokenFromPropertiesIsStillValid() -- Token in properties is null%n");
        }
        return false;
    }

    //No usages?
//    public static Authenticator getAuthenticator() {
//        return new Authenticator() {
//            @Override
//            protected java.net.PasswordAuthentication getPasswordAuthentication() {
//                return new java.net.PasswordAuthentication("user", token.toCharArray());
//            }
//        };
//    }
}
