package domein;

import model.Notification;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class ProductController {

    public ArrayList<Product> getAllProducts() {
        System.out.printf("INFO -- ProductController.getAllProducts() --%n");

        JSONObject json = null;
        try {
            json = ApiCall.get("/product/all", null);
        } catch (Exception ie) {
            System.out.printf("Error while getting products: %s%n", ie);
        }

        System.out.printf("\tFound products: %s", json.toJSONString());

        ArrayList<Product> products = new ArrayList<>();


        for (Object o : (ArrayList) json.get("Product")) {


            JSONObject jsonProduct = (JSONObject) o;
            products.add(new Product(
                    ((Long) (jsonProduct.get("id"))).intValue(),
                    ((Long) jsonProduct.get("stock")).intValue(),
                    (String) jsonProduct.get("image"),
                    ((Long) jsonProduct.get("companyId")).intValue()
            ));
        }
        return products;
    }
    public ArrayList<Product> getAllProductsByCompanyId(int id, String token) {
        System.out.printf("INFO -- ProductController.getAllProductsByCompanyId() --%n");

        JSONObject json = null;
        try {
            json = ApiCall.get("/product/all/" + id, token);
        } catch (Exception ie) {
            System.out.printf("Error while getting products: %s%n", ie);
        }

        System.out.printf("\tFound products: %s", json.toJSONString());

        ArrayList<Product> products = new ArrayList<>();


        for (Object o : (ArrayList) json.get("ProductCompany")) {


            JSONObject jsonProduct = (JSONObject) o;
            products.add(new Product(
                    ((Long) (jsonProduct.get("id"))).intValue(),
                    ((Long) jsonProduct.get("stock")).intValue(),
                    (String) jsonProduct.get("image"),
                    ((Long) jsonProduct.get("companyId")).intValue()
            ));
        }
        return products;
    }

    public ProductInfo GetProductInfoById(int id) {
        System.out.printf("INFO -- ProductController.getProductInfoById() --%n");

        JSONObject json = null;
        try {
            System.out.println(ApiCall.get("/product/" + id, null));
            System.out.println("------> het id: " + id);
            json = ApiCall.get("/product/" + id, null);
        } catch (Exception ie) {
            System.out.printf("Error while getting product: %s%n", ie);
        }

        System.out.printf("\tFound product: %s", json.toJSONString());


        JSONObject jsonProduct = (JSONObject) json.get("ProductInfo");
        ProductInfo product = new ProductInfo(
                (String) jsonProduct.get("naam"),
                ((Long) jsonProduct.get("aantal")).intValue(),
                ((Long) jsonProduct.get("eenheidsprijs")).intValue(),
                ((Long) jsonProduct.get("totalePrijs")).intValue()
        );

        return product;
    }
}
