package springboot.controller;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.service.BoxService;

@RestController
@RequestMapping("/api/box")
public class BoxController {

    private BoxService boxService;

    public BoxController(BoxService boxService) {
        super();
        this.boxService = boxService;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllTransportServices(
    ) {
        JSONObject response = new JSONObject();
        HttpStatus status = HttpStatus.NO_CONTENT;
        ResponseEntity responseEntry;
        try {
            response.put("boxes", this.boxService.getAllBoxes());
            status = HttpStatus.OK;
        } catch (Exception e) {
            response.put("error", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {
            responseEntry = ResponseEntity.status(status).body(response);
        }
        return responseEntry;
    }

    //ADD
    @PostMapping("/add")
    public ResponseEntity<JSONObject> addBox(
            @RequestBody JSONObject jsonBox
    ) {
        JSONObject response = new JSONObject();
        HttpStatus status = HttpStatus.CREATED;
        ResponseEntity responseEntity;
        try {
            response.put("box", this.boxService.addBox(jsonBox));
            status = HttpStatus.CREATED;
        } catch (Exception e) {
            response.put("error", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {
            responseEntity = ResponseEntity.status(status).body(response);
        }
        return responseEntity;
    }

    //EDIT
    @PutMapping("/edit/{id}")
    public ResponseEntity<JSONObject> editBox(
            @PathVariable("id") Integer id,
            @RequestBody JSONObject jsonBox
    ) {
        JSONObject response = new JSONObject();
        HttpStatus status = HttpStatus.CREATED;
        ResponseEntity responseEntity;
        try {
            response.put("box", this.boxService.editBox(id, jsonBox));
            status = HttpStatus.CREATED;
        } catch (Exception e) {
            response.put("error", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {
            responseEntity = ResponseEntity.status(status).body(response);
        }
        return responseEntity;
    }


}
