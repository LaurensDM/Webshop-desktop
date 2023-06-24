package domein;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Company;
import model.Notification;
import model.User;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

public class DomeinController {

    private static AuthProvider authProvider;
    private PropertyLoader loader;
    private NotificationController notificationController;
    private ProductController productController;
    private CompanyController companyController;

    private AdminController adminController;
    private TransportController transportController;
    private BoxController boxController;
    private OrderController orderController;


    public ObservableList<Company> observableCustomers = FXCollections.observableArrayList();
    public ObservableList<User> observableEmployees = FXCollections.observableArrayList();
    public ObservableList<Product> observableProducts = FXCollections.observableArrayList();
    public ObservableList<Notification> observableNotifications = FXCollections.observableArrayList();
    public ObservableList<TransportDienst> observableTransportServices = FXCollections.observableArrayList();
    public ObservableList<Box> observableBoxes = FXCollections.observableArrayList();
    public ObservableList<Order> observableOrders = FXCollections.observableArrayList();

    public DomeinController() {
        authProvider = new AuthProvider();
        loader = new PropertyLoader();
        notificationController = new NotificationController();
        adminController = new AdminController(this, authProvider);
        transportController = new TransportController(this);
        productController = new ProductController();
        boxController = new BoxController(this);
        companyController = new CompanyController();
        orderController = new OrderController();
    }

    // TODO: make this one work
    public String getVersion() {
        return loader.loadVersion();
    }

    public String getName() {
        return loader.loadName();
    }

    public String getRole() {
        return authProvider.getPermission();
    }

    public void changeRole(User user, String role) throws IOException {
        System.out.printf("INFO -- DomeinController.changeRole() -- email: %s originalRole: %s -> role: %s%n", user.getEmail(), user.getRole(), role);
        adminController.changeRoleEmployee(user, role, authProvider.getToken());
        updateObservableEmployees(user);
    }

    // ----------- AuthProvider methods ----------------
    public void logout() {
        authProvider.logout();
    }

    public void login(String email, String password) throws ConnectException {
        authProvider.login(email, password);
    }

    public String registerUserAndCompany(String email, String password, String username, String companyVAT) throws ConnectException {
        return authProvider.registerUserAndCompany(email, password, username, companyVAT);
    }

    public boolean authenticated() {
        return authProvider.authenticated();
    }

    public String getPermission() {
        return authProvider.getPermission();
    }

    public String getUserName() {
        return authProvider.getName();
    }

    public String getUserEmail() {
        return authProvider.getEmail();
    }

    public boolean checkEmail(String email) {
        return authProvider.checkEmail(email);
    }

    public Boolean getStayLoggedIn() {
        return authProvider.getStayLoggedIn();
    }

    public boolean validatePropertiesToken() {
        return authProvider.validatePropertiesToken();
    }

    public void setStayLoggedIn(Boolean stayLoggedIn) {
        authProvider.setStayLoggedIn(stayLoggedIn);
    }

    // ----------- AdminController methods ----------------
    public String addEmployeeToCompany(User newEmployee, String password) {
        return adminController.addEmployeeToCompany(newEmployee, password, authProvider.getToken());
    }

    public ObservableList<User> getEmployees() throws RuntimeException {
        System.out.printf("INFO -- DomeinController.getEmployees()%n");
        if (observableEmployees.size() == 0) {
            System.out.println("\tWith token (no employees where found locally");
            ArrayList<User> employees = adminController.getEmployees(authProvider.getToken());
            observableEmployees = FXCollections.observableArrayList(employees);
        }
        System.out.printf("\tFound %d employees%n", observableEmployees.size());
        return observableEmployees;
    }

//    public (add employee to observable list if created)

    public ObservableList<Notification> getNotifications() throws RuntimeException {
        System.out.printf("INFO -- DomeinController.getNotifications()%n");
        if (observableNotifications.size() == 0) {
            System.out.println("\tWith token (no notifications where found locally");
            ArrayList<Notification> notifications = notificationController.getNotifications(authProvider.getToken());
            observableNotifications = FXCollections.observableArrayList(notifications);
        }
        System.out.printf("\tFound %d notifications%n", observableNotifications.size());
        return observableNotifications;
    }

    public void addNotification(String username, String text) {
        //notificationController.addNotification(username ,text);
    }

    public ObservableList<Product> getProducts() throws RuntimeException {
        System.out.printf("INFO -- DomeinController.getProducts()%n");
        if (observableProducts.size() == 0) {
            System.out.println("\tWith token (no products where found locally");

            ArrayList<Product> products = productController.getAllProducts();
            observableProducts = FXCollections.observableArrayList(products);
        }
        System.out.printf("\tFound %d Products%n", observableProducts.size());

        return observableProducts;
    }
    public ObservableList<Product> getAllProductsByCompanyId(int id) throws RuntimeException{
        System.out.printf("INFO -- DomeinController.getProductByCompanyId()%n");
        if (observableProducts.size() == 0) {
            System.out.println("\tWith token (no products where found locally");

            ArrayList<Product> products = productController.getAllProductsByCompanyId(id, authProvider.getToken());
            observableProducts = FXCollections.observableArrayList(products);
        }
        System.out.printf("\tFound %d Products%n", observableProducts.size());

        return observableProducts;
    }
    public ProductInfo getProductInfoById(int id) throws RuntimeException{
        System.out.printf("INFO -- DomeinController.getProductInfoById()%n");
        ProductInfo product = productController.GetProductInfoById(id);
        System.out.printf("\tFound %s Products%n", product.toString());

        return product;
    }
    // ----------- Order methods ----------------
    public ObservableList<Order> getOrders() throws RuntimeException {
        System.out.printf("INFO -- DomeinController.getOrders()%n");
        if (observableOrders.size() == 0) {
            System.out.println("\tWith token (no orders where found locally");

            ArrayList<Order> orders = orderController.getAllOrders(authProvider.getToken());
            observableOrders = FXCollections.observableArrayList(orders);
        }
        System.out.printf("\tFound %d Orders%n", observableOrders.size());

        return observableOrders;
    }
    public OrderDetail getOrder(String id) throws RuntimeException {
        System.out.printf("INFO -- DomeinController.getOrder()%n");
            OrderDetail order = orderController.getById(id, authProvider.getToken());
        System.out.printf("\tFound Order%n");

        return order;
    }
    public void editOrder(OrderDetail order) {
        orderController.editOrder(
                order.getId(),
                order.getAankoper(),
                order.getNaamKlant(),
                order.getFromCompanyId(),
                order.getPackagingId(),
                order.getOrderDateTime(),
                order.getTotalPrice(),
                2,
                order.getTransporterId(),
                order.getStreet(),
                order.getStreetNumber(),
                order.getZipCode(),
                order.getCity(),
                order.getCountry(),
                authProvider.getToken()
        );
    }
    public String[] getBackendInfo() {
        JSONObject response = null;
        String[] result = {"", ""};
        try {
            response = ApiCall.get("/health/version", null);
            String version = (String) response.get("version");
            String name = (String) response.get("name");
            result = new String[]{version, name};
        } catch (IOException e) {
            // TODO: trow snackbar on failed api call?
            System.out.println("failed to get backend version");
        }
        return result;
    }

    //no usages
//    public static JSONObject apiCall(String urlString, String token) throws IOException {
//        return ApiCall.get(urlString, token);
//    }


    public Company getCompanyFromAuthProvider() {
        System.out.printf("INFO -- DomeinController.getCompanyFromAuthProvider()%n");
        return authProvider.getCompany();
    }

    public ObservableList<Company> getCustomers(int companyId) {
        List<Company> customers = companyController.getCustomerCompaniesById(companyId, authProvider.getToken());
        observableCustomers = FXCollections.observableArrayList(customers);
        return observableCustomers;
    }

    public void updateObservableEmployees(User updatedUser) {
        System.out.printf("INFO -- DomeinController.updateObservableEmployees()%n");
        observableEmployees = observableEmployees.stream()
                .filter(user -> !user.getId().equals(updatedUser.getId()))
                .collect(FXCollections::observableArrayList, ObservableList::add, ObservableList::addAll);
        observableEmployees.add(updatedUser);


        System.out.println("\tRemoved old user and added updated user");
        System.out.printf("\tnew user added: %s%n", updatedUser);
    }

    // ----------- TransportDienst methods ----------------

    public ObservableList<TransportDienst> getTransportServices() {
        if (observableTransportServices.size() == 0) {
            System.out.println("\tWith token (no transport services where found locally");
            ArrayList<TransportDienst> transportServices = transportController.getTransportServices();
            observableTransportServices = FXCollections.observableArrayList(transportServices);
        }
        System.out.printf("\tFound %d transport services %n", observableTransportServices.size());
        return observableTransportServices;
    }

    public void addTransportService(String name, String emailAdresses, String phoneNumbers, boolean isActive) {
        transportController.addTransportService(name, emailAdresses, phoneNumbers, isActive);
    }

    public void editTransportService(TransportDienst transportDienst) {
        transportController.editTransportService(
                transportDienst.getId(),
                transportDienst.getName(),
                transportDienst.getEmailAddresses(),
                transportDienst.getPhoneNumbers(),
                transportDienst.getIsActive()
        );
        observableTransportServices.replaceAll(transportDienst1 -> transportDienst1.getId() == transportDienst.getId() ? transportDienst : transportDienst1);
    }

    // ----------- Box methods ----------------

    public ObservableList<Box> getBoxes() {
        if (observableBoxes.size() == 0) {
            System.out.println("\tWith token (no boxes where found locally");
            ArrayList<Box> boxes = boxController.getBoxes();
            observableBoxes = FXCollections.observableArrayList(boxes);
        }
        System.out.printf("\tFound %d boxes %n", observableBoxes.size());
        return observableBoxes;
    }

    public void addBox(String name, String type, double width, double height, double length, double price, boolean isActive) {
        boxController.addBox(name, type, width, height, length, price, isActive);
    }

    public void editBox(Box box) {
        boxController.editBox(
                box.getId(),
                box.getName(),
                box.getType(),
                box.getWidth(),
                box.getHeight(),
                box.getLength(),
                box.getPrice(),
                box.getIsActive()
        );
        observableBoxes.replaceAll(box1 -> box1.getId() == box.getId() ? box : box1);
    }

}
