import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
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
import springboot.controller.BoxController;
import springboot.repository.BoxRepository;
import springboot.service.BoxService;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JavaServerMain.class)
public class BoxTest {

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
        //boxRepository.deleteAll();
    }

    @Test
    public void testAddBoxAndRetrieveAll() throws Exception {
        String jsonBox = "{\"id\": 1, \"name\": \"Test Box\", \"type\": \"Type A\", \"width\":10.0, \"length\": 10.0, \"height\": 10.0, \"price\": 10.0, \"isActive\": true}";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBox, headers);

        ResponseEntity<String> addResponse = restTemplate.exchange(
                createURLWithPort("/api/box/add"),
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        assertEquals(201, addResponse.getStatusCodeValue());

        ResponseEntity<String> getAllResponse = restTemplate.getForEntity(createURLWithPort("/api/box/all"), String.class);
        assertEquals(200, getAllResponse.getStatusCodeValue());

        String responseBody = getAllResponse.getBody();
        assertTrue(responseBody.contains("Test Box"));
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
