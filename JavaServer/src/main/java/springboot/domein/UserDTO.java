package springboot.domein;

public class UserDTO {
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private Integer companyId;

    public UserDTO(User user) {
        name = user.getName();
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        email = user.getEmail();
        role = user.getRole();
        companyId = user.getCompanyId();
    }

    public UserDTO(String name, String firstName, String lastName, String email, String role, Integer companyId) {
        this.name = name;
        setFirstName(firstName);
        setLastName(lastName);
        this.email = email;
        this.role = role;
        this.companyId = companyId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            this.firstName = "Unset";
        } else {
            this.firstName = firstName;
        }
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            this.lastName = "Unset";
        } else {
            this.lastName = lastName;
        }
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public Integer getCompanyId() {
        return companyId;
    }
}
