package springboot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import springboot.domein.Delivery;
import springboot.domein.DeliveryId;

import java.util.List;

@Repository
public interface DeliveryRepository extends CrudRepository<Delivery, DeliveryId> {
    @Query("SELECT d FROM Delivery d WHERE d.orderId = :id")
    Delivery findById(@PathVariable String id);
}
