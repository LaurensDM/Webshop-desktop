package domein;


public class AddEmployee {
    private String id;
    private String firstName;
    private String surname;
    private String street;
    private String streetNr;
    private Integer zipCode;
    private String adres;
    private Integer phoneNumber;
    private  String email;
    private String role;
    public AddEmployee(String firstName
    , String surname
    , String street
    , String streetNr
    , Integer zipCode
    , String adres
    , Integer phoneNumber
    ,  String email
    , String role) {
        this.firstName=firstName;
        this.surname=surname;
        this.street=street;
        this.streetNr=streetNr;
        this.zipCode=zipCode;
        this.adres=adres;
        this.phoneNumber=phoneNumber;
        this.email=email;
        this.role=role;
    }

    private void addId(){
        //todo:id toevoegen aan employee
    }
}
