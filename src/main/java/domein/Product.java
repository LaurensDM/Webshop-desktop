package domein;

public class Product {
    private int id;
    private int stock;
    private String image;
    private int companyId;

    public Product(int id, int stock, String image, int companyId) {
        this.id = id;
        this.stock = stock;
        this.image = image;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public int getStock() {
        return stock;
    }

    public String getImage() {
        return image;
    }

    public int getCompanyId() {
        return companyId;
    }
}
