package springboot.domein;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetail {


    private String id;


    private String buyerId;

    private int customerId;

    private int fromCompanyId;

    private int packagingId;

    private String orderReference;

    private String orderDateTime;

    private double netPrice;

    private double taxPrice;

    private double totalPrice;

    private int orderStatus;
    private int transporterId;

    private String street;


    private String streetNumber;


    private String zipCode;


    private String city;


    private String country;

    private String trackAndtrace;

    private List<ProductDTO> products;

    public OrderDetail (Order order, Delivery delivery, List<ProductDTO> products) {
        id = order.getId();
        buyerId = order.getBuyerId();
        customerId = order.getCustomerId();
        fromCompanyId = order.getFromCompanyId();
        packagingId = order.getPackagingId();
        orderReference = order.getOrderReference();
        orderDateTime = order.getOrderDateTime();
        netPrice = order.getNetPrice();
        taxPrice = order.getTaxPrice();
        totalPrice = order.getTotalPrice();
        orderStatus = order.getOrderStatus();
        transporterId = delivery.getTransporterId();
        street = delivery.getStreet();
        streetNumber = delivery.getStreetNumber();
        zipCode = delivery.getZipCode();
        city = delivery.getCity();
        country = delivery.getCountry();
        trackAndtrace = delivery.getTrackAndtrace();
        this.products = products;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id='" + id + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", customerId=" + customerId +
                ", fromCompanyId=" + fromCompanyId +
                ", packagingId=" + packagingId +
                ", orderReference='" + orderReference + '\'' +
                ", orderDateTime='" + orderDateTime + '\'' +
                ", netPrice=" + netPrice +
                ", taxPrice=" + taxPrice +
                ", totalPrice=" + totalPrice +
                ", orderStatus=" + orderStatus +
                ", transporterId=" + transporterId +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", trackAndtrace='" + trackAndtrace + '\'' +
                '}';
    }
}
