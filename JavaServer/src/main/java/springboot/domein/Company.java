package springboot.domein;

import lombok.Data;
import org.jetbrains.annotations.Contract;

import javax.persistence.*;

@Data
@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "logoImg")
    private String logoImg;
    @Column(name = "countryCode")
    private String countryCode;
    // TODO: create a vatNumber class;
    @Column(name = "vatNumber")
    private String vatNumber;
    @Column(name = "street")
    private String street;
    @Column(name = "streetNumber")
    private String streetNumber;
    @Column(name = "zipCode")
    private String zipCode;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;

    public Company(String name, String countryCode, String vatNumber, String street, String streetNumber, String zipCode, String city, String country) {
        this.name = name;
        this.logoImg = null;
        this.countryCode = countryCode;
        this.vatNumber = vatNumber;
        this.street = street;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public Company() {}

    @Override
    public String toString() {
        return String.format(
                "Company[id=%d, name='%s', logoImg='%s', countryCode='%s', vatNumber='%s', street='%s', streetNumber='%s', zipCode='%s', city='%s', country='%s']",
                id, name, logoImg, countryCode, vatNumber, street, streetNumber, zipCode, city, country
        );
    }
}
