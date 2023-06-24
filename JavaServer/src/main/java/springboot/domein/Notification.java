package springboot.domein;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "notification")
public class Notification {
    @Column(name = "orderId")
    private String orderId;
    @Column(name = "userId")
    private String userId;
    @Column(name = "companyId")
    private Integer companyId;
    @Column(name = "date")
    private String date;
    @Column(name = "audience")
    private String audience;
    @Column(name = "subject")
    private String subject;
    @Column(name = "text")
    private String text;
    @Column(name = "readBy")
    private String readBy;
    @Column(name = "archivedBy")
    private String archivedBy;

    // I don't want to use these anymore (they are redundant)
    @Column(name = "status")
    private Boolean status;
    @Column(name = "archived")
    private Boolean archived;

    @Id
    private String id;

    // TODO: How to use foreign keys in springboot
//    @ForeignKey(name = "fk_notification_company")
//    @ManyToOne
//    @JoinColumn(name = "companyId", insertable = false, updatable = false)
//    private Company company;

//    @ForeignKey(name = "fk_notification_user")
//    @ManyToOne
//    @JoinColumn(name = "userId", insertable = false, updatable = false)
//    private User user;

//    @ForeignKey(name = "fk_notification_order")
//    @ManyToOne
//    @JoinColumn(name = "orderId", insertable = false, updatable = false)
//    private Order order;


    public Notification(
            String role,
            String orderId,
            String userId,
            Integer companyId,
            String date,
            String audience,
            String subject,
            String text,
            String readBy,
            String archivedBy,
            Boolean status,
            Boolean archived
    ) {
        id = UUID.randomUUID().toString();
        this.orderId = orderId;
        this.userId = userId;
        this.companyId = companyId;
        this.date = date;
        this.audience = audience;
        this.subject = subject;
        this.text = text;
        this.readBy = readBy;
        this.archivedBy = archivedBy;
        this.status = status;
        this.archived = archived;
    }

    public Notification(String text, String userId, Integer companyId){
        id = UUID.randomUUID().toString();
        this.text = text;
        this.userId = userId;
        this.companyId = companyId;
    }

    public Notification(String text, String date, String userId){
        id = UUID.randomUUID().toString();
        this.text = text;
        this.date = date;
        //this.orderId = orderId;
        this.userId = userId;
    }

    public Notification() {

    }

    public void markAsRead(User user) {
        this.status = true;
        this.readBy = user.getEmail();
    }

    public void markAsArchived(User user) {
        this.archived = true;
        this.archivedBy = user.getEmail();
    }

}
