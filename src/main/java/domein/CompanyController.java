package domein;

import model.Company;
import org.json.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompanyController {

    public Company getCompanyByToken(int companyId, String token) {
        System.out.printf("INFO -- CompanyController.getCompanyByToken()%n");
        JSONObject getCompanyResponse;
        try {
            getCompanyResponse = (JSONObject) ApiCall.get(String.format("/companies/%d", companyId), token).get("company");
        } catch (IOException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);

        }
        System.out.printf("\tReceived company from api: %s%n", getCompanyResponse);
        // TODO: find a better way to do this?
        Integer id = Integer.parseInt(String.valueOf(getCompanyResponse.get("id")));
        String name = String.valueOf(getCompanyResponse.get("name"));
        // TODO: if null, set to default image? right now will be: logoImg='null'
        String logoImg = String.valueOf(getCompanyResponse.get("logoImg"));
        String countryCode = String.valueOf(getCompanyResponse.get("countryCode"));
        String vatNumber = String.valueOf(getCompanyResponse.get("vatNumber"));
        String street = String.valueOf(getCompanyResponse.get("street"));
        String streetNumber = String.valueOf(getCompanyResponse.get("streetNumber"));
        String zipCode = String.valueOf(getCompanyResponse.get("zipCode"));
        String city = String.valueOf(getCompanyResponse.get("city"));
        String country = String.valueOf(getCompanyResponse.get("country"));
        Company company = new Company(id, name, logoImg, countryCode, vatNumber, street, streetNumber, zipCode, city, country);
        System.out.printf("\tCreated local company: %s%n", company);
        return company;
    }

    public List<Company> getCustomerCompaniesById(int companyId, String token) {
        System.out.printf("INFO -- CompanyController.getCompanyByToken()%n");
        JSONObject json = null;
        try {
            json = ApiCall.get(String.format("/order/customersByCompany/%d", companyId), token);
        } catch (Exception ie) {
            System.out.printf("Error while getting companies: %s%n", ie);
        }

        System.out.println(json);
        ArrayList<Company> companies = new ArrayList<>();


        for (Object o : (ArrayList) json.get("Customers")) {


            JSONObject jsonProduct = (JSONObject) o;
            companies.add(new Company(
                    ((Long) (jsonProduct.get("id"))).intValue(),
                    ((String) jsonProduct.get("name")),
                    (String) jsonProduct.get("logoImg"),
                    (String) jsonProduct.get("countryCode"),
                    (String) jsonProduct.get("vatNumber"),
                    (String) jsonProduct.get("street"),
                    (String) jsonProduct.get("streetNumber"),
                    (String) jsonProduct.get("zipCode"),
                    (String) jsonProduct.get("city"),
                    (String) jsonProduct.get("country")
            ));
        }


        return companies;


    }
}
