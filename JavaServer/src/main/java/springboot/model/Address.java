package springboot.model;

import org.json.simple.JSONObject;

public class Address {
    private String street;
    private String streetNumber;
    private String zipCode;
    private String city;
    private String country;

    public Address(String street, String streetNumber, String zipCode, String city, String country) {
        setStreet(street);
        setStreetNumber(streetNumber);
        setZipCode(zipCode);
        setCity(city);
        setCountry(country);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString(){
        return String.format("{%s %s, %s %s, %s}", street, streetNumber, zipCode, city, country);
    }
}
