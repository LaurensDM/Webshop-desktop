package domein;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class Order {
    private String orderID;
    private String naamKlant;
    private String aankoper;
    private String orderDate;
    private String street;
    private String huisNr;
    private String trackAndTrace;
    private String status;


    public Order(String naamKlant, String aankoper, String orderID, String orderDate, String street, String huisNr, String trackAndTrace, Integer status
    ) {
        this.naamKlant = naamKlant;
        this.aankoper = aankoper;
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.street = street;
        this.huisNr = huisNr;
        setStatus(status);
        this.trackAndTrace = trackAndTrace;
    }


    public Order(Order order) {
    }
    public Order( String klantNaam,String orderId, String orderdate, Integer status){
        System.out.println(status);
        this.naamKlant = klantNaam;
        this.orderID = orderId;
        this.orderDate = orderdate;
        setStatus(status);
    }

    public String getTrackAndTrace() {
        return trackAndTrace;
    }

    public String getNaamKlant() {
        return naamKlant;
    }

    public String getAankoper() {
        return aankoper;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getOrderDate() {

        return orderDate;
    }

    public String getFormattedDate(){
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(orderDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Error";
        }

    }

    public String getStreet() {
        return street;
    }

    public String getHuisNr() {
        return huisNr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(Integer status){
        System.out.println("aantal status: "+status);
        if(status!=null) {
            int statusValue = status.intValue();
            if (statusValue == 1) {
                this.status = "Geplaatst";
            } else if (statusValue == 2) {
                this.status = "Verwerkt";
            } else if (statusValue == 3) {
                this.status = "Geleverd";
            } else if (statusValue == 4) {
                this.status = "Geannuleerd";
            }
        }
    }

    private void addTrackAndTraceCode(int trackAndTraceCode) {

    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", naamKlant='" + naamKlant + '\'' +
                ", aankoper='" + aankoper + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", street='" + street + '\'' +
                ", huisNr='" + huisNr + '\'' +
                ", trackAndTrace='" + trackAndTrace + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
