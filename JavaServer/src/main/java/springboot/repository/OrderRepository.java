package springboot.repository;

import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import springboot.domein.Order;
import springboot.domein.OrderDetail;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, String> {


    @Query("select DISTINCT c.id, c.name,c.logoImg,c.countryCode, c.vatNumber, c.street, c.streetNumber,c.zipCode,c.country,c.city from Order o join Company c ON o.fromCompanyId = c.id where o.fromCompanyId = :id")
    List<Object> findOrderCompanyByCustomerId(@Param("id") int customerId);
}
