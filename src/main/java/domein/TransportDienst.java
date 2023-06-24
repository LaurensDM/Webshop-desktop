package domein;

import java.util.List;

public class TransportDienst {

    public TransportDienst(){
        this.isActive = true;
    }

    public TransportDienst(Integer id, String name, String phoneNumbers, String emailAddresses, boolean isActive){
        this.id = id;
        setName(name);
        setPhoneNumbers(phoneNumbers);
        setEmailAddresses(emailAddresses);
        // Standard : active
        setIsActive(isActive);
    }

    public TransportDienst(String name, String phoneNumbers, String emailAddresses,
        int amountOfCharacters, boolean onlyNumbers, String prefix, boolean isActive)
    {
        setName(name);
        setPhoneNumbers(phoneNumbers);
        setEmailAddresses(emailAddresses);
        //setAmountOfCharacters(amountOfCharacters);
        //setOnlyNumbers(onlyNumbers);
        //setPrefix(prefix);
        setTrackAndTraceCode(amountOfCharacters, onlyNumbers, prefix);
        // Standard : active
        setIsActive(isActive);
    }
    private Integer id;
    private String name;
    private String phoneNumbers;
    private String emailAddresses;
    private String trackAndTraceCode;
    private int amountOfCharacters;
    private boolean onlyNumbers;
    private String prefix;
    /*
    private String postalCode;
    */
    private boolean isActive;

    //getters and setters
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhoneNumbers(){
        return phoneNumbers;
    }
    public void setPhoneNumbers(String phoneNumbers){
        this.phoneNumbers = phoneNumbers;
    }
    public String getEmailAddresses() {
        return emailAddresses;
    }
    public void setEmailAddresses(String emailAddresses) {
        this.emailAddresses = emailAddresses;
    }
    public String getTrackAndTraceCode() {
        return trackAndTraceCode;
    }
    public void setTrackAndTraceCode(int amountOfCharacters, boolean onlyNumbers, String prefix){
        this.trackAndTraceCode = String.valueOf(amountOfCharacters) + String.valueOf(onlyNumbers) + prefix;
    }
    /*
    public int getAmountOfCharacters() {
        return amountOfCharacters;
    }
    */
    public void setAmountOfCharacters(int amountOfCharacters) {
        this.amountOfCharacters = amountOfCharacters;
    }
    /*
    public boolean getIsOnlyNumbers() {
        return onlyNumbers;
    }
    */
    public void setOnlyNumbers(boolean onlyNumbers) {
        this.onlyNumbers = onlyNumbers;
    }
    /*
    public String getPrefix() {
        return prefix;
    }
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString(){
        return name + getIsActive();
    }

}
