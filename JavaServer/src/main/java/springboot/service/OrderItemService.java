package springboot.service;

import springboot.domein.ProductDTO;

import java.util.List;

public interface OrderItemService {
    List<ProductDTO> getAllProducts(String orderId);
}
