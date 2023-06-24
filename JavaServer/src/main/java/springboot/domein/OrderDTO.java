package springboot.domein;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class OrderDTO implements Serializable {

    private String orderId;
    private int klantId;
    private String aankoper;
    private String date;
    private Integer status;

    public OrderDTO(Order order) {
        this.orderId = order.getId();
        this.klantId = order.getCustomerId();
        this.aankoper = order.getBuyerId();
        this.date = order.getOrderDateTime();
        this.status = order.getOrderStatus();
    }


}
