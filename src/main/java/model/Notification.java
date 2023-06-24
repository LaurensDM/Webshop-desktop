package model;

import java.util.Date;

public class Notification {
    private String id;
    private String orderId;
    private String userId;
    private Integer companyId;
    private String date;
    private String subject;
    private String text;
    private String audience;
    private String readBy;
    private String archivedBy;
//    private String status;
//    private String archived;


    public Notification(
            String id,
            String orderId,
            String userId,
            Integer companyId,
            String date,
            String subject,
            String text,
            String audience,
            String readBy,
            String archivedBy
    ) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.companyId = companyId;
        this.date = date;
        this.subject = subject;
        this.text = text;
        this.audience = audience;
        setReadBy(readBy);
        setArchivedBy(archivedBy);
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public String getAudience() {
        return audience;
    }

    public String getReadBy() {
        return readBy;
    }

    public String getArchivedBy() {
        return archivedBy;
    }

    public void setReadBy(String readBy) {
        if (readBy == null) {
            this.readBy = "Not read";
        } else {
            this.readBy = readBy;
        }
    }

    public void setArchivedBy(String archivedBy) {
        if (archivedBy == null) {
            this.archivedBy = "Not archived";
        } else {
            this.archivedBy = archivedBy;
        }
    }
}
