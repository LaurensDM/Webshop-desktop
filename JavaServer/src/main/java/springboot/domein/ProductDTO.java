package springboot.domein;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDTO implements Serializable {

    private String naam;
    private int aantal;
    private int eenheidsprijs;
    private int totalePrijs;

    public ProductDTO(String naam, int quantity, int price) {
        this.naam = naam;
        this.aantal = quantity;
        this.eenheidsprijs = price;
        this.totalePrijs = price*quantity;
    }
}
