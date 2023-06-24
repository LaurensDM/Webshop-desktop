import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import springboot.JavaServerMain;
import springboot.repository.BoxRepository;
import springboot.repository.DeliveryRepository;
import springboot.service.DeliveryService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JavaServerMain.class)
public class DeliveryTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DeliveryRepository deliveryRepository;

    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        objectMapper = new ObjectMapper();
    }
    @Test
    public void testGetById() {
        int companyId = 1;
        ResponseEntity<String> getAllResponse = restTemplate.getForEntity(createURLWithPort("/api/delivery/" + companyId), String.class);
        Assert.assertEquals(200, getAllResponse.getStatusCodeValue());
    }
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
