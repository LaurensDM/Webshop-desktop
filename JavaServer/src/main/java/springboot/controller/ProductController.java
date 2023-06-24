package springboot.controller;


import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.domein.Product;
import springboot.domein.ProductDTO;
import springboot.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll() {
        JSONObject response = new JSONObject();
        List<Product> products;

        products = productService.getAll();

        response.put("Product", products);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/all/{id}")
    public ResponseEntity<Object> findAllByCompanyId(@PathVariable("id") int id) {
        JSONObject response = new JSONObject();
        List<Product> products;

        products = productService.findAllByCompanyId(id);

        response.put("ProductCompany", products);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public Object findInfoById(@PathVariable("id") int id) {
        JSONObject response = new JSONObject();
        ProductDTO product;

        product = productService.findById(id);

        response.put("ProductInfo", product);
        return ResponseEntity.ok().body(response);
    }
}
