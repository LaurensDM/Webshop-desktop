package springboot.service;

import springboot.domein.Delivery;

public interface DeliveryService {
    void updateDelivery(String id, Delivery delivery);

    Delivery getDeliveryById(String id);
}
