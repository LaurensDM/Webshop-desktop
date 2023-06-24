import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import springboot.JavaServerMain;
import springboot.repository.OrderRepository;
import springboot.repository.ProductPriceRepository;
import springboot.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JavaServerMain.class)
public class ProductTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductPriceRepository productPriceRepositoryRepository;
    @Autowired
    private ProductRepository productRepositoryRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllProducts() {

        ResponseEntity<String> getAllResponse = restTemplate.getForEntity(createURLWithPort("/api/product/all"), String.class);
        Assert.assertEquals(200, getAllResponse.getStatusCodeValue());
    }
    @Test
    public void testGetAllProductsByCompanyId() {
        int id =1;
        ResponseEntity<String> getAllResponse = restTemplate.getForEntity(createURLWithPort("/api/product/all/" + id), String.class);
        Assert.assertEquals(200, getAllResponse.getStatusCodeValue());
    }
    @Test
    public void testGetById() {
        int productId = 1;
        ResponseEntity<String> getAllResponse = restTemplate.getForEntity(createURLWithPort("/api/product/" + productId), String.class);
        Assert.assertEquals(200, getAllResponse.getStatusCodeValue());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
