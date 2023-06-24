package domein;

public class ProductInfo {
    private String naam;
    private int aantal;
    private int eenheidsprijs;
    private int totalePrijs;

    public ProductInfo(String naam, int aantal, int eenheidsprijs, int totalePrijs) {
        this.naam = naam;
        this.aantal = aantal;
        this.eenheidsprijs = eenheidsprijs;
        this.totalePrijs = totalePrijs;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "naam='" + naam + '\'' +
                ", aantal=" + aantal +
                ", eenheidsprijs=" + eenheidsprijs +
                ", totalePrijs=" + totalePrijs +
                '}';
    }

    public String getNaam() {
        return naam;
    }

    public int getAantal() {
        return aantal;
    }

    public int getEenheidsprijs() {
        return eenheidsprijs;
    }

    public int getTotalePrijs() {
        return totalePrijs;
    }
}
