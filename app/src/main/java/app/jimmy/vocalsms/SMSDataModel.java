package app.jimmy.vocalsms;

/**
 * @author Jimmy
 * Created on 8/3/19.
 */
public class SMSDataModel {
    private int id;
    private String from;
    private String body;
    private String date;

    public SMSDataModel(int id, String from, String body, String date) {
        this.id = id;
        this.from = from;
        this.body = body;
        this.date = date;
    }

    public SMSDataModel() {

    }

    public int getId() {
        return id;
    }

    public SMSDataModel(String header) {
        body = header;
    }

    public String getFrom() {
        return from;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }
}
