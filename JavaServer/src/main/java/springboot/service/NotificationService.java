package springboot.service;

import org.json.simple.JSONObject;
import springboot.domein.Notification;
//import springboot.domein.User;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    List<Notification> getAll(String token);
    Notification createNotification(JSONObject jsonNotification);
    Notification updateNotification(Notification notification);

    Notification getNotification(String id);

    void deleteNotification(String id);

    void switchReadStatus(String id, springboot.domein.User user);
    void switchArchiveStatus(String id, springboot.domein.User user);
}
