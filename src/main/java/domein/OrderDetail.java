package domein;

import java.util.List;

public class OrderDetail {
    private String id;

    private String aankoper;

    private String naamKlant;

    private int fromCompanyId;

    private int packagingId;

    private String orderDateTime;

    private double totalPrice;

    private String orderStatus;
    private int transporterId;

    private String street;


    private String streetNumber;


    private String zipCode;


    private String city;


    private String country;

    private List<ProductInfo> products;


    public OrderDetail(String id, String aankoper, String naamKlant, int fromCompanyId, int packagingId, String orderDateTime, double totalPrice, int orderStatus, int transporterId, String street, String streetNumber, String zipCode, String city, String country, List<ProductInfo> products) {
        this.id = id;
        this.aankoper = aankoper;
        this.naamKlant = naamKlant;
        this.fromCompanyId = fromCompanyId;
        this.packagingId = packagingId;
        this.orderDateTime = orderDateTime;
        this.totalPrice = totalPrice;
        setStatus(orderStatus);
        this.transporterId = transporterId;
        this.street = street;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        this.products = products;
    }


    public String getOrderStatus() {
        return orderStatus;
    }
    public void setStatus(Integer status){
        System.out.println("aantal status: "+status);
        if(status!=null) {
            int statusValue = status.intValue();
            if (statusValue == 0) {
                this.orderStatus = "Geplaatst";
            } else if (statusValue == 1) {
                this.orderStatus = "Verwerkt";
            } else if (statusValue == 2) {
                this.orderStatus = "Geleverd";
            } else if (statusValue == 3) {
                this.orderStatus = "Geannuleerd";
            }
        }
    }

    public int getPackagingId() {
        return packagingId;
    }

    public String getNaamKlant() {
        return naamKlant;
    }

    public int getTransporterId() {
        return transporterId;
    }

    public int getFromCompanyId() {
        return fromCompanyId;
    }

    public String getId() {
        return id;
    }

    public String getAankoper() {
        return aankoper;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public List<ProductInfo> getProducts() {
        return products;
    }

    public void setPackagingId(int packagingId) {
        this.packagingId = packagingId;
    }

    public void setTransporterId(int transporterId) {
        this.transporterId = transporterId;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id='" + id + '\'' +
                ", aankoper='" + aankoper + '\'' +
                ", naamKlant='" + naamKlant + '\'' +
                ", fromCompanyId=" + fromCompanyId +
                ", packagingId=" + packagingId +
                ", orderDateTime='" + orderDateTime + '\'' +
                ", totalPrice=" + totalPrice +
                ", orderStatus='" + orderStatus + '\'' +
                ", transporterId=" + transporterId +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", products=" + products +
                '}';
    }
}
