package springboot.domein;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Getter @Setter
@Entity
@Table(name = "packaging")
public class Box {

    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "width")
    private double width;

    @Column(name = "length")
    private double length;

    @Column(name = "height")
    private double height;

    @Column(name = "price")
    private Double price;

    @Column(name = "active")
    private Boolean isActive;

    public Box(Integer id, String name, String type, double width, double length, double height , Double price, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.width = width;
        this.length = length;
        this.height = height;
        this.price = price;
        this.isActive = isActive;
    }

    public Box() {}

}
