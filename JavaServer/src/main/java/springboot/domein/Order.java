package springboot.domein;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    private String id;

    @Column(name = "buyerId")
    private String buyerId;

    @Column(name = "customerId")
    private int customerId;

    @Column(name = "fromCompanyId")
    private int fromCompanyId;

    @Column(name = "packagingId")
    private int packagingId;

    @Column(name = "orderReference")
    private String orderReference;

    @Column(name = "orderDateTime")
    private String orderDateTime;

    @Column(name = "netPrice")
    private double netPrice;

    @Column(name = "taxPrice")
    private double taxPrice;

    @Column(name = "totalPrice")
    private double totalPrice;

    @Column(name = "orderStatus")
    private Integer orderStatus;

    
    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", customerId=" + customerId +
                ", fromCompanyId=" + fromCompanyId +
                ", packagingId=" + packagingId +
                ", orderReference='" + orderReference + '\'' +
                ", orderDateTime='" + orderDateTime + '\'' +
                ", netPrice=" + netPrice +
                ", taxPrice=" + taxPrice +
                ", totalPrice=" + totalPrice +
                ", orderStatus=" + orderStatus +
                '}';
    }

}
