package springboot.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import springboot.domein.*;
import springboot.repository.DeliveryRepository;
import springboot.repository.OrderRepository;
import springboot.service.OrderItemService;
import springboot.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
    private OrderRepository orderRepository;

    private DeliveryRepository deliveryRepository;

    private OrderItemService orderItemService;


    public OrderServiceImpl(OrderRepository orderRepository, DeliveryRepository deliveryRepository, OrderItemService orderItemService) {
        super();
        this.orderRepository = orderRepository;
        this.deliveryRepository = deliveryRepository;
        this.orderItemService = orderItemService;
    }

    @Override
    public List<OrderDTO> getAll() {
        logger.info("OrderServiceImpl -- getAllOrders -- Getting all orders");
        List<Order> orders = (List<Order>) orderRepository.findAll();
        List<OrderDTO> orderDTOS = new ArrayList<>();
        orders.stream().forEach(order -> orderDTOS.add(new OrderDTO(order)));
        System.out.println("1ste dto= "+orderDTOS.get(0));
        System.out.println("1ste dto satus="+orderDTOS.get(0).getStatus());
        return orderDTOS;
    }

    @Override
    public Order getById(String id) {
        return orderRepository.findById(id).get();
    }

    public OrderDetail getDetailsById(String id) {
        Order order = getById(id);
        logger.info(id);
        Delivery delivery = deliveryRepository.findById(id);
        List<ProductDTO> productDTOS = orderItemService.getAllProducts(id);
        OrderDetail orderDetail = new OrderDetail(order, delivery, productDTOS);
        return orderDetail;
    }

    @Override
    public Order updateOrder(String id, Order order) {
        Order orderToUpdate = orderRepository.findById(id).get();
        orderToUpdate.setOrderStatus(order.getOrderStatus());
        orderRepository.save(orderToUpdate);
        return orderToUpdate;
    }

    @Override
    public List<Company> getOrderCompanyByCustomerId(int customerId) {
        logger.info("OrderServiceImpl -- getOrderCompanyByCustomerId");
        List<Object> obj = orderRepository.findOrderCompanyByCustomerId(customerId);

        List<Company> companies = new ArrayList<>();

        for (Object o : obj) {
            Object[] fields = (Object[]) o;
            Company company = new Company();
            company.setId((Integer) fields[0]);
            company.setName(fields[1].toString());
            company.setLogoImg(fields[2].toString());
            company.setCountryCode(fields[3].toString());
            company.setVatNumber(fields[4].toString());
            company.setStreet(fields[5].toString());
            company.setStreetNumber(fields[6].toString());
            company.setZipCode(fields[7].toString());
            company.setCity(fields[8].toString());
            company.setCountry(fields[9].toString());
            companies.add(company);
        }

        return companies;
    }



}