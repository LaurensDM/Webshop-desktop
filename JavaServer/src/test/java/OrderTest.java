import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import springboot.JavaServerMain;
import springboot.controller.OrderController;
import springboot.domein.Company;
import springboot.domein.Order;
import springboot.domein.OrderDTO;
import springboot.domein.OrderDetail;
import springboot.repository.BoxRepository;
import springboot.repository.OrderRepository;
import springboot.service.OrderService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JavaServerMain.class)
public class OrderTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OrderRepository orderRepository;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllOrders() {

        ResponseEntity<String> getAllResponse = restTemplate.getForEntity(createURLWithPort("/api/order/all"), String.class);
        Assert.assertEquals(200, getAllResponse.getStatusCodeValue());
    }

    @Test
    public void testGetById() {
        String orderId = "ABC123";
        String jsonOrder = "{\"id\": \"1\",\"buyerId\": \"buyer123\", \"customerId\": 123, \"fromCompanyId\": 456, \"packagingId\": 789, \"orderReference\": \"ABC123\", \"orderDateTime\": \"2023-05-22T10:00:00\", \"netPrice\": 100.0, \"taxPrice\": 10.0, \"totalPrice\": 110.0, \"orderStatus\": 1}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonOrder, headers);

        ResponseEntity<String> addResponse = restTemplate.exchange(
                createURLWithPort("/api/user/login"),
                HttpMethod.GET,
                requestEntity,
                String.class
        );
        ResponseEntity<String> getAllResponse = restTemplate.getForEntity(createURLWithPort("/api/order/details/" + orderId), String.class);
        Assert.assertEquals(200, getAllResponse.getStatusCodeValue());
    }

    @Test
    public void testUpdateOrder() {
        String jsonOrder = "{\"id\": \"1\",\"buyerId\": \"buyer123\", \"customerId\": 123, \"fromCompanyId\": 456, \"packagingId\": 789, \"orderReference\": \"ABC123\", \"orderDateTime\": \"2023-05-22T10:00:00\", \"netPrice\": 100.0, \"taxPrice\": 10.0, \"totalPrice\": 110.0, \"orderStatus\": 1}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonOrder, headers);

        ResponseEntity<String> addResponse = restTemplate.exchange(
                createURLWithPort("/api/order/"+ "ABC123"),
                HttpMethod.PUT,
                requestEntity,
                String.class
        );
        Assert.assertEquals(200, addResponse.getStatusCodeValue());
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


}
