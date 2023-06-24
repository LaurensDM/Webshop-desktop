import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import springboot.JavaServerMain;
import springboot.controller.UserController;
import springboot.repository.BoxRepository;
import springboot.service.UserService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JavaServerMain.class)
@Import(UserController.class)
@SpringJUnitConfig
public class UserTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private UserService userService;
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void login() throws Exception {
        /*HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(createURLWithPort("/api/user/login")))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());*/
        String email="qwertic@qwict.com";
        String password="qwertic@qwict.com";
        JSONObject emailAndPassword = new JSONObject();
        emailAndPassword.put("email", email);
        emailAndPassword.put("password", password);
        UserController controller = new UserController(userService);
        System.out.println(emailAndPassword);
        System.out.println(controller.login(emailAndPassword));
        Assert.assertEquals(201, controller.login(emailAndPassword).getStatusCode());

    }
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
