import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import springboot.JavaServerMain;
import springboot.repository.BoxRepository;
import springboot.repository.CompanyRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JavaServerMain.class)
public class CompanyTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CompanyRepository companyRepository;

    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        objectMapper = new ObjectMapper();
    }
    @Test
    public void testGetById() {
        int companyId = 1;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer 2b93f1c4-38bd-490d-a0ca-f7b81b9de171");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/companies/" + companyId), HttpMethod.GET, entity, String.class);

        Assert.assertEquals(200, response.getStatusCodeValue());
    }
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
