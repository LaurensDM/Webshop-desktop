package springboot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;
import springboot.domein.OrderItem;

import java.util.List;

public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {
    @Query("SELECT d FROM OrderItem d WHERE d.orderId = :id")
    List<OrderItem> findById(@PathVariable String id);
}
