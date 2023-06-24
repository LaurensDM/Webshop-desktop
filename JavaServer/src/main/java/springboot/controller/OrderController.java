package springboot.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.domein.Company;
import springboot.domein.Order;
import springboot.domein.OrderDTO;
import springboot.domein.OrderDetail;
import springboot.repository.OrderRepository;
import springboot.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepo;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll() {
        JSONObject response = new JSONObject();
        List<OrderDTO> orders;

        orders = this.orderService.getAll();

        response.put("Orders", orders);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping("/customersByCompany/{id}")
    public ResponseEntity<Object> getAllCustomersByCompany(@PathVariable("id") int id) {
        JSONObject response = new JSONObject();
        List<Company> customers;

        customers = orderService.getOrderCompanyByCustomerId(id);
        System.out.println(customers.get(0));

        response.put("Customers", customers);


        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") String id) {
        JSONObject response = new JSONObject();
        Order order;

        order = orderService.getById(id);

        response.put("Order", order);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<Object> getDetailsById(@PathVariable("id") String id) {
        JSONObject response = new JSONObject();
        OrderDetail order;

        order = orderService.getDetailsById(id);

        response.put("OrderDetail", order);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable("id") String id, @RequestBody Order order) {
        JSONObject response = new JSONObject();
        Order updatedOrder;

        updatedOrder = orderService.updateOrder(id, order);

        response.put("Order", updatedOrder);
        return ResponseEntity
                .ok()
                .body(response);
    }


}
