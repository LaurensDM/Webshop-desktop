package springboot.domein;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "delivery")
@IdClass(DeliveryId.class)
public class Delivery {
    @Id
    @Column(name= "transporterId")
    private int transporterId;

    @Id
    @Column(name= "orderId")
    private String orderId;

    @Column(name = "packagingId")
    private int packagingId;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private String streetNumber;

    @Column(name = "zipCode")
    private String zipCode;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "additionalInformation")
    private String additionalInformation;

    @Column(name = "trackAndtrace")
    private String trackAndtrace;

    @Column(name = "deliveryStatus")
    private int deliveryStatus;

    public Delivery(int transporterId, String orderId, int packagingId, String street, String streetNumber, String zipCode, String city, String country, String additionalInformation, String trackAndtrace, int deliveryStatus) {
        this.transporterId = transporterId;
        this.orderId = orderId;
        this.packagingId = packagingId;
        this.street = street;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        this.additionalInformation = additionalInformation;
        this.trackAndtrace = trackAndtrace;
        this.deliveryStatus = deliveryStatus;
    }


    public Delivery() {

    }
}
