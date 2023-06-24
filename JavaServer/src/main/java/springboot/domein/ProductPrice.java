package springboot.domein;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "productprice")
public class ProductPrice {
    @Id
    private Integer id;

    @Column(name="productId")
    private int productId;

    @Column(name = "price")
    private int price;

    @Column(name = "quantity")
    private int quantity;

    public ProductPrice(Integer id, String currencyId, int price, int quantity) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

    public ProductPrice() {

    }

    @Override
    public String toString() {
        return "ProductPrice{" +
                "id=" + id +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
