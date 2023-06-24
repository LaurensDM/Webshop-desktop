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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JavaServerMain.class)
public class TransportserviceTest {
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
    public void testEditTransportservice() throws Exception {
        String jsonTransportservice = "{\"id\": 2, \"name\": \"John Doe\", \"phoneNumbers\": \"1234567890\", \"emailAdresses\": \"johndoe@example.com\",\"vatNumber\": \"123456789\", \"trackandtraceInfo\": \"ABC123\", \"isActive\": true}";
        Integer id = 2;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonTransportservice, headers);
        System.out.println(requestEntity);
        ResponseEntity<String> addResponse = restTemplate.exchange(
                createURLWithPort("/api/transportService/edit/" + id),
                HttpMethod.PUT,
                requestEntity,
                String.class
        );

        System.out.println(addResponse);
        assertEquals(201, addResponse.getStatusCodeValue());
    }
    @Test
    public void TestGetAllTransportservices(){
        ResponseEntity<String> getAllResponse = restTemplate.getForEntity(createURLWithPort("/api/transportService/all"), String.class);
        System.out.println(getAllResponse);
        assertEquals(200, getAllResponse.getStatusCodeValue());
    }
    @Test
    public  void TestAddTransportservice(){
        String jsonTransportservice = "{\n" +
                "  \"id\": 4,\n" +
                "  \"name\": \"Jane Doe\",\n" +
                "  \"phoneNumbers\": \"1234567890\",\n" +
                "  \"emailAdresses\": \"janedoe@example.com\",\n" +
                "  \"vatNumber\": \"123456789\",\n" +
                "  \"trackandtraceInfo\": \"ABC123\",\n" +
                "  \"isActive\": true\n" +
                "}";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonTransportservice, headers);

        ResponseEntity<String> addResponse = restTemplate.exchange(
                createURLWithPort("/api/transportService/add/"),
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        assertEquals(201, addResponse.getStatusCodeValue());
    }
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
