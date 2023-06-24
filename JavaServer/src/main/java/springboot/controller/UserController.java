package springboot.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.domein.User;
import springboot.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;
    private static final Logger logger = LogManager.getLogger(UserController.class);

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody JSONObject emailAndPassword
    ) {
        JSONObject response = new JSONObject();
        try {
            response.put("token", this.userService.login(emailAndPassword));
        } catch (IllegalAccessException iae) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(iae.getMessage());
        }


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response.toJSONString());
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @RequestBody JSONObject jsonUser
    ) {
        JSONObject response = new JSONObject();
        HttpStatus status = HttpStatus.I_AM_A_TEAPOT;
        ResponseEntity responseEntry;
        try {
            response.put("token", this.userService.register(jsonUser));
            status = HttpStatus.CREATED;
        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        } catch (DuplicateKeyException e) {
            response.put("error", e.getMessage());
            status = HttpStatus.CONFLICT;
        } catch (Exception e) {
            response.put("error", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {
            responseEntry = ResponseEntity
                    .status(status)
                    .body(response);
        }
        return responseEntry;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllEmployees(
            @RequestHeader("Authorization") String token
    ) {
        JSONObject response = new JSONObject();
        List<User> users;

        try {
            users = this.userService.getAllEmployees(token);
        } catch (IllegalArgumentException iae) {
            logger.error(iae.getMessage());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }
        response.put("employees", users);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @PostMapping("/")
    public ResponseEntity<JSONObject> addUser(
            @RequestBody JSONObject jsonUserToAdd,
            @RequestHeader("Authorization") String token
    ) {
        JSONObject response = new JSONObject();
        HttpStatus status = HttpStatus.I_AM_A_TEAPOT;
        ResponseEntity responseEntry;
        try {
            response.put("user", this.userService.createUserAndAddToCompany(jsonUserToAdd, token));
            status = HttpStatus.CREATED;
        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        } catch (DuplicateKeyException e) {
            response.put("error", e.getMessage());
            status = HttpStatus.CONFLICT;
        } catch (Exception e) {
            response.put("error", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {
            responseEntry = ResponseEntity
                    .status(status)
                    .body(response);
        }
        return responseEntry;
    }

    @PutMapping("{id}")
    public ResponseEntity<JSONObject> updateUser(
            @PathVariable("id") String id,
            @RequestBody String userToUpdate,
            @RequestHeader("Authorization") String token
    ) {
        JSONParser parser = new JSONParser();
        JSONObject request = new JSONObject();
        try {
            request = (JSONObject) parser.parse(userToUpdate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        JSONObject response = new JSONObject();
        HttpStatus status = HttpStatus.CREATED;
        User updatedUser = null;
        try {
            updatedUser = this.userService.updateUser(id, request, token);
        } catch (IllegalAccessException iae) {
            response.put("error", iae.getMessage());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        } catch (IllegalArgumentException iae) {
            response.put("error", iae.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }

        response.put("updatedUser", updatedUser);

        return ResponseEntity
                .status(status)
                .body(response);
    }
}
