package demo.contact.app.activity;

public class Contact_list {

    String id;
    String imagepath;
    String cname1;
    String cnumber1;
    String caddress1;
    String uid;

    public Contact_list(String id, String imagepath, String cname1, String cnumber1, String caddress1,String uid) {
        this.id = id;
        this.imagepath = imagepath;
        this.cname1 = cname1;
        this.cnumber1 = cnumber1;
        this.caddress1 = caddress1;
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getCname1() {
        return cname1;
    }

    public void setCname1(String cname1) {
        this.cname1 = cname1;
    }

    public String getCnumber1() {
        return cnumber1;
    }

    public void setCnumber1(String cnumber1) {
        this.cnumber1 = cnumber1;
    }


    public String getCaddress1() {
        return caddress1;
    }

    public void setCaddress1(String caddress1) {
        this.caddress1 = caddress1;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
