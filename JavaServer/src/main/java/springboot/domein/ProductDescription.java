package springboot.domein;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "productdescription")
public class ProductDescription {
    @Id
    private Integer id;

    @Column(name="productId")
    private int productId;

    @Column(name = "languageId")
    private String languageId;

    @Column(name = "name")
    private String name;

    @Column(name = "shortDescription")
    private String shortDescription;

    @Column(name = "longDescription")
    private String longDescription;


    public ProductDescription(Integer id, String languageId, String name, String shortDescription, String longDescription) {
        this.id = id;
        this.languageId = languageId;
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
    }

    public ProductDescription() {

    }

    @Override
    public String toString() {
        return "ProductDescription{" +
                "id=" + id +
                ", languageId='" + languageId + '\'' +
                ", name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                '}';
    }
}
