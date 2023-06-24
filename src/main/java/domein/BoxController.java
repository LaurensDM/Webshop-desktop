package domein;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class BoxController {

    private DomeinController dc;

    public BoxController(DomeinController dc) {
        this.dc = dc;
    }

    public ArrayList<Box> getBoxes(){
        JSONObject jsonObject = null;
        try{
            jsonObject = ApiCall.get("/box/all", null);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException((String) jsonObject.get("error"));
        }
        ArrayList<Box> boxes = new ArrayList<>();
        for (Object o : (ArrayList) jsonObject.get("boxes")) {
            JSONObject jsonBox = (JSONObject) o;
            boxes.add(new Box(
                    ((Long) jsonBox.get("id")).intValue(),
                    (String) jsonBox.get("name"),
                    (String) jsonBox.get("type"),
                    (double) jsonBox.get("width"),
                    (double) jsonBox.get("height"),
                    (double) jsonBox.get("length"),
                    (Double) jsonBox.get("price"),
                    (Boolean) jsonBox.get("isActive")
            ));
        }
        return boxes;
    }

    public String addBox(String name, String type, double width, double height, double length, double price, boolean isActive) {
        JSONObject jsonObject = null;
        JSONObject postBody = new JSONObject();
        postBody.put("name", name);
        postBody.put("type", type);
        postBody.put("width", width);
        postBody.put("height", height);
        postBody.put("length", length);
        postBody.put("price", price);
        postBody.put("isActive", isActive);

        try {
            jsonObject = ApiCall.post("/box/add", postBody, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(jsonObject);
        return "success";
    }

    public String editBox(int id, String name, String type, double width, double height, double length, double price, boolean isActive){
        JSONObject putBody = new JSONObject();
        putBody.put("id", id);
        putBody.put("name", name);
        putBody.put("type", type);
        putBody.put("width", width);
        putBody.put("height", height);
        putBody.put("length", length);
        putBody.put("price", price);
        putBody.put("isActive", isActive);

        try{
            ApiCall.put("/box/edit/" + id, putBody, null);
        } catch(Exception e){
            e.printStackTrace();
        }
        return "success";
    }

}
