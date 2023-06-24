package springboot.domein;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    private Integer id;

    @Column(name = "stock")
    private int stock;

    @Column(name = "image")
    private String image;
    @Column(name = "companyId")
    private int companyId;


    public Product(Integer id, int stock, String image, int companyId) {
        this.id = id;
        this.stock = stock;
        this.image = image;
        this.companyId = companyId;
    }

    public Product() {

        
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", stock=" + stock +
                ", image='" + image + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}
