package domein;

import model.Notification;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class NotificationController {

    public ArrayList<Notification> getNotifications(String token) {
        System.out.printf("INFO -- NotificationController.getNotifications() --%n");
        JSONObject jsonObject = null;
        try {
            jsonObject = ApiCall.get("/notifications/all", token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.printf("\tFound notifications: %s", jsonObject.toJSONString());

        ArrayList<Notification> notifications = new ArrayList<>();
        for (Object o : (ArrayList) jsonObject.get("notifications")) {
            JSONObject jsonNotification = (JSONObject) o;
            notifications.add(new Notification (
                    (String) jsonNotification.get("id"),
                    (String) jsonNotification.get("orderId"),
                    (String) jsonNotification.get("userId"),
//                    (Integer) jsonNotification.get("companyId"),
                    Integer.getInteger(String.valueOf(jsonNotification.get("companyId"))),
                    (String) jsonNotification.get("date"),
                    (String) jsonNotification.get("subject"),
                    (String) jsonNotification.get("text"),
                    (String) jsonNotification.get("audience"),
                    (String) jsonNotification.get("readBy"),
                    (String) jsonNotification.get("archivedBy")
            ));
        }
        return notifications;
    }

    public String addNotification(String username, String text) {
        System.out.println("INFO -- NotificationController.addNotification()");
        JSONObject jsonObject = null;
        JSONObject postBody = new JSONObject();
        postBody.put("username", username);
        postBody.put("text", text);

        try {
            jsonObject = ApiCall.post("/notifications/add", postBody, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(jsonObject);
        return "success";
    }

}
