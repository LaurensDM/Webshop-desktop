package springboot.domein;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Data
@Getter @Setter
@Entity
@Table(name = "deliveryservice")
public class TransportDienst{

    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "vatNumber")
    private String vatNumber;

    @Column(name = "trackandtraceInfo")
    private String trackandtraceInfo;

    @Column(name = "actief")
    private Boolean actief;

    public TransportDienst(Integer id, String name, String phoneNumber, String email, Boolean actief) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.vatNumber = "None specified";
        this.trackandtraceInfo = "None specified";
        this.actief = actief;
    }

    public TransportDienst() {}

}