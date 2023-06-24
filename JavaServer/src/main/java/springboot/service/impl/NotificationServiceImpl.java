package springboot.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.cfg.NotYetImplementedException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import springboot.domein.TransportDienst;
import springboot.domein.UserDTO;
import springboot.domein.Notification;
import springboot.domein.User;
import springboot.repository.NotificationRepository;
import springboot.service.NotificationService;
import springboot.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private static final Logger logger = LogManager.getLogger(NotificationServiceImpl.class);

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public NotificationServiceImpl(NotificationRepository notificationRepository, UserService userService) {
        super();
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    @Override
    public List<Notification> getAll(String token) {
        logger.info("NotificationServiceImpl -- getAll");
        UserDTO userDTO = UserServiceImpl.decodeUserByToken(token);
        User user = userService.getUserByEmail(userDTO.getEmail());
        logger.info(String.format("User %s with role %s and companyId %d is requesting notifications", user.getEmail(), user.getRole(), user.getCompanyId()));
        List<Notification> userIdNotifications = new ArrayList<>();
        List<Notification> companyIdNotifications = new ArrayList<>();
        List<Notification> orderIdNotifications = new ArrayList<>();

        userIdNotifications.addAll(notificationRepository.findAllByUserId(user.getId()));
        userIdNotifications.removeIf(notification -> !notification.getAudience().equals("private"));

        companyIdNotifications.addAll(notificationRepository.findAllByCompanyId(user.getCompanyId()));
        companyIdNotifications.removeIf(notification -> {
            if (notification.getOrderId() != null) {
                orderIdNotifications.add(notification);
                return true;
            }

            if (!user.getRole().equals("admin") && notification.getAudience().equals("admin")) {
                return true;
            }
            return false;
        });

        List<Notification> formattedNotifications = new ArrayList<>();
        formattedNotifications.addAll(userIdNotifications);
        formattedNotifications.addAll(companyIdNotifications);
        formattedNotifications.addAll(orderIdNotifications);
        logger.info(String.format("Found %d private notifications, %d company notifications and %d order notifications", userIdNotifications.size(), companyIdNotifications.size(), orderIdNotifications.size()));
        return formattedNotifications;
    }

    @Override
    public Notification createNotification(JSONObject jsonNotification) {
        //String username = (String) jsonNotification.get("username");
        String text = (String) jsonNotification.get("text");

        Notification latestNotification = notificationRepository.findTopByOrderByIdDesc();

        Notification notification = new Notification(text, "15-05-2023", 1);

        return notificationRepository.save(notification);

    }

    @Override
    public Notification updateNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification getNotification(String id) {

        logger.info(String.format("NotificationServiceImpl -- getNotification -- id: " + id));
        return notificationRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Notification with id " + id + " not found")
        );
    }

    @Override
    public void deleteNotification(String id) {
        Notification notification = getNotification(id);
        notificationRepository.delete(notification);
    }

    @Override
    public void switchReadStatus(String id, User user) {
        throw new NotYetImplementedException();
    }

    @Override
    public void switchArchiveStatus(String id, User user) {
        throw new NotYetImplementedException();
    }
}
