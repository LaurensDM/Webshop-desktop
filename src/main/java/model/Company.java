package model;

public class Company {
    private Integer id;
    private String name;
    private String logoImg;
    private String countryCode;
    private String vatNumber;
    private String street;
    private String streetNumber;
    private String zipCode;
    private String city;
    private String country;

    public Company(
            Integer id,
            String name,
            String logoImg,
            String countryCode,
            String vatNumber,
            String street,
            String streetNumber,
            String zipCode,
            String city,
            String country
    ) {
        this.id = id;
        this.name = name;
        this.logoImg = logoImg;
        this.countryCode = countryCode;
        this.vatNumber = vatNumber;
        this.street = street;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
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

    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
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
    public String toString() {
        return String.format(
                "Company[id=%d, name='%s', logoImg='%s', countryCode='%s', vatNumber='%s', street='%s', streetNumber='%s', zipCode='%s', city='%s', country='%s']",
                id, name, logoImg, countryCode, vatNumber, street, streetNumber, zipCode, city, country
        );
    }
}
