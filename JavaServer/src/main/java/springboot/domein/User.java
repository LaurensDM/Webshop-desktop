package springboot.domein;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    @Column(name = "companyId")
    private Integer companyId;

    @Column(name = "hash")
    private String hash;

    @Column(name = "salt")
    private String salt;

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

    // TODO: How to use foreign keys in springboot
//    @ForeignKey(name = "companyId")
//    @ManyToOne
//    @JoinColumn(name = "companyId", referencedColumnName = "id")


    public User(String name, String firstName, String lastName, String email, String role, Integer companyId, String hash, String salt, String street, String streetNumber, String zipCode, String city, String country) {
        id = UUID.randomUUID().toString();
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.companyId = companyId;
        this.hash = hash;
        this.salt = salt;
        this.street = street;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public User(
            String name,
            String firstName,
            String lastName,
            String email,
            String role,
            Integer companyId,
            String hash,
            String salt
    ) { // doubles possible
        id = UUID.randomUUID().toString();
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.companyId = companyId;
        this.hash = hash;
        this.salt = salt;
    }

    public User(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public User() {}

    public String getName() {
        return name;
    }

//    public void setName(String name) {
//        this.name = name;
//    }

    public String getFirstName() {
        return firstName;
    }

//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }

    public String getLastName() {
        return lastName;
    }

//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }

    public String getEmail() {
        return email;
    }

//    public void setEmail(String email) {
//        this.email = email;
//    }

    public String getRole() {
        return role;
    }

//    public void setRole(String role) {
//        this.role = role;
//    }
    public Integer getCompanyId() {
        return companyId;
    }
//    public void setCompanyId(Integer companyId) {
//        this.companyId = companyId;
//    }

    public String getHash() {
        return hash;
    }

//    public void setHash(String hash) {
//        this.hash = hash;
//    }

    public String getSalt() {
        return salt;
    }

//    public void setSalt(String salt) {
//        this.salt = salt;
//    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", companyId='" + companyId + '\''+
                ", password='" + hash + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

//    public void setId(UUID id) {
//        this.id = id;
//    }
}
