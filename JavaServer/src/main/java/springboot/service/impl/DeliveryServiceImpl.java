package springboot.service.impl;

import org.springframework.stereotype.Service;
import springboot.domein.Delivery;
import springboot.repository.DeliveryRepository;
import springboot.service.DeliveryService;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private DeliveryRepository deliveryRepository;

    public DeliveryServiceImpl(DeliveryRepository deliveryRepository) {
        super();
        this.deliveryRepository = deliveryRepository;
    }
    @Override
    public void updateDelivery(String id, Delivery delivery) {
        Delivery existingDelivery = deliveryRepository.findById(id);
        existingDelivery.setDeliveryStatus(delivery.getDeliveryStatus());
        existingDelivery.setTrackAndtrace(delivery.getTrackAndtrace());
        existingDelivery.setTransporterId(delivery.getTransporterId());
        deliveryRepository.save(existingDelivery);
    }

    @Override
    public Delivery getDeliveryById(String id) {
        return deliveryRepository.findById(id);
    }
}
