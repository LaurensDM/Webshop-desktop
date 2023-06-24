package springboot.domein;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "orderitem")
public class OrderItem {

    @Id
    private Integer id;

    @Column(name= "orderId")
    private String orderId;

    @Column(name= "productId")
    private int productId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "netPrice")
    private double netPrice;

}
