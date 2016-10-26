package asia.ienter.qrscaner.models;

/**
 * Created by phamquangmanh on 10/25/16.
 */
public class HistoryClass {
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long  getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    private int ID;
    private String content;
    private long date;

}
