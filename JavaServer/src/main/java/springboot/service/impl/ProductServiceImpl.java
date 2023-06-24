package springboot.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.domein.Product;
import springboot.domein.ProductDTO;
import springboot.domein.ProductDescription;
import springboot.domein.ProductPrice;
import springboot.repository.ProductDescriptionRepository;
import springboot.repository.ProductPriceRepository;
import springboot.repository.ProductRepository;
import springboot.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductDescriptionRepository productDescriptionRepository;
    private ProductPriceRepository productPriceRepository;
    private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(ProductRepository productRepository, ProductDescriptionRepository productDescriptionRepository, ProductPriceRepository productPriceRepository) {
        this.productRepository = productRepository;
        this.productDescriptionRepository = productDescriptionRepository;
        this.productPriceRepository = productPriceRepository;
    }

    @Override
    public List<Product> getAll() {
        logger.info("getAllProducts -- Getting all products!");
        List<Product> allProducts = productRepository.findAll();
        return allProducts;
    }

    @Override
    public List<Product> findAllByCompanyId(Integer companyId)
    {
        logger.info("getAllProducts -- Getting all products from company!");
        List<Product> allProducts = productRepository.findAllByCompanyId(companyId);
        return allProducts;
    }

    @Override
    public ProductDTO findById(Integer id) {
        logger.info("getProductById -- Getting product by id! " +id);
        ProductPrice productPrice = productPriceRepository.findByProductId(id);
        ProductDescription productDescription = productDescriptionRepository.findByProductIdAndLanguageId(id, "en");
        ProductDTO productdto = new ProductDTO( productDescription.getName(),productPrice.getQuantity(),productPrice.getPrice());

        return productdto;
    }

}
