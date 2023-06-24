package springboot.controller;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.domein.Delivery;
import springboot.service.DeliveryService;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

    private DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getDeliveryById(@PathVariable("id") String id) {
        JSONObject response = new JSONObject();
        Delivery delivery = deliveryService.getDeliveryById(id);
        response.put("delivery", delivery);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateDelivery(@PathVariable("id") String id, @RequestBody Delivery delivery) {
        JSONObject response = new JSONObject();
        deliveryService.updateDelivery(id, delivery);
        response.put("message", "Delivery updated successfully");
        return ResponseEntity.ok().body(response);
    }
}
