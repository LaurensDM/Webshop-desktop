package springboot.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static springboot.JavaServerMain.appProps;

@RestController
@RequestMapping(value = "/api/health", method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
//@RequestMapping("/api/health")
public class HealthController {
    @GetMapping("/ping")
    public ResponseEntity<Object> health() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("wing", true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().body(jsonObject.toString());
    }

    @GetMapping("/version")
    public ResponseEntity<Object> version() {
        JSONObject response = new JSONObject();
        try {
            response.put("env", appProps.getProperty("application.env"));
            response.put("version", appProps.getProperty("application.version"));
            response.put("name", appProps.getProperty("application.name"));
        } catch (Exception e) {
            e.printStackTrace();
        }



        // TODO: fix this so that it returns a json object
        return ResponseEntity.ok().body(response.toString());
    }
}
