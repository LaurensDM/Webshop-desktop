package springboot.service;

import org.springframework.stereotype.Service;
import springboot.domein.Product;
import springboot.domein.ProductDTO;

import java.util.List;

@Service
public interface ProductService {
    List<Product> getAll();
    List<Product> findAllByCompanyId(Integer companyId);
    ProductDTO findById(Integer id);
}
