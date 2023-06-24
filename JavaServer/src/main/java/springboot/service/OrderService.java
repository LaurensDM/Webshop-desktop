package springboot.service;

import org.json.simple.JSONObject;
import springboot.domein.Company;
import springboot.domein.Company;
import springboot.domein.Order;
import springboot.domein.OrderDTO;
import springboot.domein.OrderDetail;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getAll();

    Order getById(String id);

    Order updateOrder(String id, Order order);

    OrderDetail getDetailsById(String id);

    List<Company> getOrderCompanyByCustomerId(int id);
}
