package springboot.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import springboot.domein.OrderItem;
import springboot.domein.ProductDTO;
import springboot.repository.OrderItemRepository;
import springboot.service.OrderItemService;
import springboot.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private static final Logger logger = LogManager.getLogger(OrderItemServiceImpl.class);

    private ProductService productService;
    private OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(ProductService productService, OrderItemRepository orderItemRepository) {
        this.productService = productService;
        this.orderItemRepository = orderItemRepository;
    }


    @Override
    public List<ProductDTO> getAllProducts(String orderId) {
        logger.info("getAllProducts -- Getting all products from order! " + orderId);
        List<OrderItem> orderItems = orderItemRepository.findById(orderId);
        List<ProductDTO> allProducts = new ArrayList<>();
        orderItems.stream().forEach(orderItem -> {
            ProductDTO productDTO = productService.findById(orderItem.getProductId());
            allProducts.add(productDTO);
        });
        return allProducts;
    }
}
