package springboot.domein;

import java.io.Serializable;

public class DeliveryId implements Serializable {
    private int transporterId;
    private String orderId;

    public DeliveryId(int transporterId, String orderId) {
        this.transporterId = transporterId;
        this.orderId = orderId;
    }

    public DeliveryId() {
    }
}
