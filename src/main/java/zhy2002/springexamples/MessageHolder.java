package zhy2002.springexamples;


import java.util.Date;

public class MessageHolder {

    private String message;
    private String prefix;
    private Date dateCreated;

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getMessage() {
        return (dateCreated == null ? "": dateCreated.toString()) + prefix + message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
