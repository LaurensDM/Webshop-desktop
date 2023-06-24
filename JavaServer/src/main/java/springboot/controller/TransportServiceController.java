package springboot.controller;

import javassist.NotFoundException;
import org.json.simple.JSONObject;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.domein.TransportDienst;
import springboot.service.TransportServiceService;

@RestController
@RequestMapping("/api/transportService")
public class TransportServiceController {
    private TransportServiceService transportServiceService;

    public TransportServiceController(TransportServiceService transportServiceService) {
        super();
        this.transportServiceService = transportServiceService;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllTransportServices(
            /*@RequestHeader("Authorization") String token*/
    ) {
        JSONObject response = new JSONObject();
        HttpStatus status = HttpStatus.NO_CONTENT;
        ResponseEntity responseEntry;
        try {
            response.put("transportServices", this.transportServiceService.getAllTransportServices(/*token*/));
            status = HttpStatus.OK;
        } catch (Exception e) {
            response.put("error", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {
            responseEntry = ResponseEntity.status(status).body(response);
        }
        return responseEntry;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTransportServiceById(
            @PathVariable("id") Integer id,
            @RequestHeader("Authorization") String token
    ) {
        JSONObject response = new JSONObject();
        HttpStatus status = HttpStatus.NO_CONTENT;
        ResponseEntity responseEntry;
        try {
            response.put("transportService", this.transportServiceService.getTransportServiceById(id, token));
            status = HttpStatus.OK;
        } catch (NotFoundException e) {
            response.put("error", e.getMessage());
            status = HttpStatus.NOT_FOUND;
        } catch (IllegalAccessException e) {
            response.put("error", e.getMessage());
            status = HttpStatus.UNAUTHORIZED;
        } catch (Exception e) {
            response.put("error", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } finally {
            responseEntry = ResponseEntity.status(status).body(response);
        }
        return responseEntry;
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTransportService(@PathVariable("id") Integer id) {
        JSONObject response = new JSONObject();
        HttpStatus status = HttpStatus.I_AM_A_TEAPOT;
        try {
            transportServiceService.deleteTransportService(id);
            response.put("message", "Transport service with id " + id + " successfully deleted.");
            status = HttpStatus.OK;
        } catch (EmptyResultDataAccessException e) {
            response.put("error", "Transport service with id " + id + " not found.");
            status = HttpStatus.NOT_FOUND;
        } catch (Exception e) {
            response.put("error", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(response);
    }

    //ADD
    @PostMapping("/add")
    public ResponseEntity<JSONObject> addTransportService(
            @RequestBody JSONObject jsonTransportDienst
    ) {
        JSONObject response = new JSONObject();
        HttpStatus status = HttpStatus.CREATED;
        ResponseEntity responseEntity;
        try {
            response.put("transportDienst", this.transportServiceService.addTransportService(jsonTransportDienst));
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
    public ResponseEntity<JSONObject> editTransportService(
            @PathVariable("id") Integer id,
            @RequestBody JSONObject jsonTransportDienst
    ) {
        JSONObject response = new JSONObject();
        HttpStatus status = HttpStatus.CREATED;
        ResponseEntity responseEntity;
        try {
            response.put("transportDienst", this.transportServiceService.editTransportService(id, jsonTransportDienst));
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
