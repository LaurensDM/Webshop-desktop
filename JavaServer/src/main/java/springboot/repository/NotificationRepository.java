package springboot.repository;

import org.springframework.stereotype.Repository;
import springboot.domein.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


@Repository
public interface NotificationRepository extends JpaRepository<Notification, String>{

    List<Notification> findAllByUserId(String userId);
    List<Notification> findAllByCompanyId(Integer companyId);
    List<Notification> findAllByOrderId(String orderId);

    Notification findTopByOrderByIdDesc();
}