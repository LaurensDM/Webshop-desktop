package domein;

import model.Role;
import model.User;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class TransportController {

    private DomeinController dc;

    public TransportController(DomeinController dc) {
        this.dc = dc;
    }

    public ArrayList<TransportDienst> getTransportServices() {
        JSONObject jsonObject = null;
        try {
            jsonObject = ApiCall.get("/transportService/all", null);
        } catch (Exception e) {
            throw new RuntimeException((String) jsonObject.get("error"));
        }
        ArrayList<TransportDienst> transportServices = new ArrayList<>();
        for (Object o : (ArrayList) jsonObject.get("transportServices")) {
            JSONObject jsonTransportService = (JSONObject) o;
            transportServices.add(new TransportDienst (
                    ((Long) jsonTransportService.get("id")).intValue(),
                    (String) jsonTransportService.get("name"),
                    (String) jsonTransportService.get("email"),
                    (String) jsonTransportService.get("phoneNumber"),
                    (Boolean) jsonTransportService.get("actief")
            ));
        }
        return transportServices;
    }

    public String addTransportService(String name, String emailAdresses, String phoneNumbers, boolean isActive) {
        System.out.println("INFO -- TransportController.addTransportService()");
        JSONObject jsonObject = null;
        JSONObject postBody = new JSONObject();
        postBody.put("name", name);
        postBody.put("emailAdresses", emailAdresses);
        postBody.put("phoneNumbers", phoneNumbers);
        postBody.put("isActive", isActive);

        try {
            jsonObject = ApiCall.post("/transportService/add", postBody, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(jsonObject);
        return "success";
    }

    public String editTransportService(int id, String name, String emailAdresses, String phoneNumbers, boolean isActive){
        JSONObject putBody = new JSONObject();
        putBody.put("id", id);
        putBody.put("name", name);
        putBody.put("emailAdresses", emailAdresses);
        putBody.put("phoneNumbers", phoneNumbers);
        putBody.put("isActive", isActive);

        try{
            ApiCall.put("/transportService/edit/" + id, putBody, null);
        } catch(Exception e){
            e.printStackTrace();
        }
        return "success";
    }

}
