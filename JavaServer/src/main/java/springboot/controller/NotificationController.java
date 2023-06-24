package springboot.controller;

import java.util.List;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import springboot.domein.Notification;
import springboot.domein.User;
import springboot.service.NotificationService;


@RestController
@RequestMapping(value = "/api/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
public class NotificationController {
    private NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        super();
        this.notificationService = notificationService;
    }

    @GetMapping("/all")
//    public ResponseEntity<List<Notification>> // TODO: is this a better way to parse data back to frontend?
    public ResponseEntity<Object> getAllNotifications(@RequestHeader("Authorization") String token) {
        JSONObject response = new JSONObject();
        List<Notification> notifications;

        notifications = this.notificationService.getAll(token);

        response.put("notifications", notifications);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @PostMapping("/add")
    public ResponseEntity<JSONObject> addNotification(
            @RequestBody JSONObject jsonNotification
    ) {
        JSONObject response = new JSONObject();
        HttpStatus status = HttpStatus.CREATED;
        ResponseEntity responseEntity;
        try {
            response.put("notification", this.notificationService.createNotification(jsonNotification));
            status = HttpStatus.CREATED;
        } catch (Exception e) {
            response.put("error", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {
            responseEntity = ResponseEntity.status(status).body(response);
        }
        return responseEntity;
    }

//    @GetMapping("/{id}")
//    public Notification getNotificationById(@PathVariable("id") String id) {
//        return this.notificationService.getNotificationById(id);
//    }

}
