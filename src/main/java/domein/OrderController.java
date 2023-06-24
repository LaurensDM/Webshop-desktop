package domein;

import model.Company;
import org.apache.commons.configuration2.plist.Token;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderController {
    public ArrayList<Order> getAllOrders(String token) {
        System.out.printf("INFO -- OrderController.getAllOrders() --%n");

        JSONObject json = null;
        try {
            json = ApiCall.get("/order/all", token);

        } catch (Exception ie) {
            System.out.printf("Error while getting orders: %s%n", ie);
        }

        System.out.printf("\tFound orders: %s", json.toJSONString());

        ArrayList<Order> orders = new ArrayList<>();


        for (Object o : (ArrayList) json.get("Orders")) {
            JSONObject json2 = null;


            JSONObject jsonOrder = (JSONObject) o;
            try {
                json2 = ApiCall.get("/companies/" + ((Long) jsonOrder.get("klantId")).intValue(), token);

            } catch (Exception ie) {
                System.out.printf("Error while getting company: %s%n", ie);
            }
            System.out.printf("\tFound company: %d", ((Long) jsonOrder.get("klantId")).intValue());
            JSONObject jsonCustomer = (JSONObject) json2.get("company");
            System.out.printf("\tFound company: %s", json2.toJSONString());
            if(json2!=null){
            orders.add(new Order(
                    (String) (jsonCustomer.get("name")),
                    (String) jsonOrder.get("orderId"),
                    (String) jsonOrder.get("date"),
                    ((Long) jsonOrder.get("status")).intValue()
                    ));
            }
        }
        return orders;
    }
    public OrderDetail getById(String id, String token) {
        System.out.printf("INFO -- OrderController.getById()%n");
        JSONObject json = null;
        JSONObject json2 = null;
        JSONObject json3=null;

        try {
            json = ApiCall.get("/order/details/" + id, token);
        } catch (Exception ie) {
            System.out.printf("Error while getting order: %s%n", ie);
        }

        System.out.println(json.toJSONString());
        JSONObject jsonOrder = (JSONObject) json.get("OrderDetail");
        try {
            json2 = ApiCall.get("/companies/" + ((Long) jsonOrder.get("customerId")).intValue(), token);
            json3 = ApiCall.get("/product/all/" + ((Long) jsonOrder.get("fromCompanyId")).intValue(), token);
        } catch (Exception ie) {
            System.out.printf("Error while getting company: %s%n", ie);
        }

        JSONObject jsonCustomer = (JSONObject) json2.get("company");


        System.out.printf("\tFound company: %d", ((Long) jsonOrder.get("customerId")).intValue());
        System.out.printf("\tFound company: %s", json2.toJSONString());


        List<ProductInfo> products = new ArrayList<>();

        for (Object o :  (ArrayList) jsonOrder.get("products")) {
            System.out.println(o.toString());
            System.out.println("Extract products --- "+o.toString());
            JSONObject json4 = (JSONObject) o;
            products.add(new ProductInfo(
                    (String) json4.get("naam"),
                    ((Long) json4.get("eenheidsprijs")).intValue(),
                    ((Long) json4.get("totalePrijs")).intValue(),
                    ((Long) json4.get("aantal")).intValue()
            ));
        }
        OrderDetail order = new OrderDetail(
                (String) jsonOrder.get("id"),
                (String) jsonOrder.get("buyerId"),
                (String) jsonCustomer.get("name"),
                ((Long) jsonOrder.get("fromCompanyId")).intValue(),
                ((Long) jsonOrder.get("packagingId")).intValue(),
                (String) jsonOrder.get("orderDateTime"),
                0,
                //((Long) jsonOrder.get("totalPrice")).doubleValue(),
                ((Long) jsonOrder.get("orderStatus")).intValue(),
                0,
                //((Long) jsonOrder.get("transporterId")).intValue(),
                (String) jsonCustomer.get("street"),
                (String) jsonCustomer.get("streetNumber"),
                (String) jsonCustomer.get("zipCode"),
                (String) jsonCustomer.get("city"),
                (String) jsonCustomer.get("country"),
                products
        );
        System.out.println("juiste order: "+order);
        return order;


    }
    public String editOrder(String id, String aankoper, String naamKlant, int fromCompanyId, int packagingId, String orderDateTime, double totalPrice, int orderStatus, int transporterId, String street, String streetNumber, String zipCode, String city, String country, String token){
        JSONObject putBody = new JSONObject();
        putBody.put("naamKlant", naamKlant);
        putBody.put("aankoper", aankoper);
        putBody.put("id", id);
        putBody.put("orderDateTime", orderDateTime);
        putBody.put("street", street);
        putBody.put("streetNumber", streetNumber);
        putBody.put("transporterId", transporterId);
        putBody.put("fromCompanyId", fromCompanyId);
        putBody.put("packagingId", packagingId);
        putBody.put("totalPrice", totalPrice);
        putBody.put("zipCode", zipCode);
        putBody.put("orderStatus", orderStatus);
        putBody.put("city", city);
        putBody.put("country", country);

        try{
            System.out.println("--------package id: " + packagingId);
            ApiCall.put("/order/" + id, putBody, token);
        } catch(Exception e){
            e.printStackTrace();
        }
        return "success";
    }
}
