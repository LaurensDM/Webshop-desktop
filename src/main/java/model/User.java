package model;


public class User {
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private Integer companyId;
    private Role role;
    private String id;
    private Address address;


    public User(String id, String name, String firstName, String lastName, String email, Integer companyId, Role role) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.companyId = companyId;
    }

    public User(String name, String firstName, String lastName, String email, Role role, Address address) {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.address = address;
    }

    public User(String email, Role role) {
        this.email = email;
        this.role = role;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getRole() {return String.valueOf(role);}

    public void setRole(Role role) {
        this.role = role;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format(
                "User{id=%s, name='%s', firstName='%s', lastName='%s', email='%s', companyId='%s' role='%s'}" +
                        "\n\tAddress: %s",
                id, name, firstName, lastName, email, companyId, role, address
        );
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
