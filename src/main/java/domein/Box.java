package domein;


public class Box {

    private Integer id;
    private String name;
    private String type;
    private double width;
    private double height;
    private double length;
    private double price;
    private boolean isActive;

    public Box() {
    }

    public Box(Integer id, String name, String type, double width, double height, double length, double price, boolean isActive) {
        setId(id);
        setName(name);
        setType(type);
        setWidth(width);
        setHeight(height);
        setLength(length);
        setPrice(price);
        setIsActive(isActive);
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getLength() {
        return length;
    }

    private void setLength(double length) {
        this.length = length;
    }

    private void setHeight(double height) {
        this.height = height;
    }

    private void setWidth(double width) {
        this.width = width;
    }
}
