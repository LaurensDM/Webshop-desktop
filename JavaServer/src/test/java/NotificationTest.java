import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import springboot.JavaServerMain;
import springboot.repository.BoxRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JavaServerMain.class)
public class NotificationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BoxRepository boxRepository;

    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        objectMapper = new ObjectMapper();
    }
    @Test
    public void testGetAllNotifications() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer your_token_here");

        ResponseEntity<String> getAllResponse = restTemplate.exchange(
                createURLWithPort("/api/notifications/all"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        Assert.assertEquals(HttpStatus.OK, getAllResponse.getStatusCode());
    }
    @Test
    public void testGetById() {
        int companyId = 1;
        ResponseEntity<String> getAllResponse = restTemplate.getForEntity(createURLWithPort("/api/companies/" + companyId), String.class);
        Assert.assertEquals(200, getAllResponse.getStatusCodeValue());
    }
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
