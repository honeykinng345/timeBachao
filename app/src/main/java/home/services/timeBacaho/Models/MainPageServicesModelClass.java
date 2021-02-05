package home.services.timeBacaho.Models;

public class MainPageServicesModelClass {

    private int Sid;
    private String Sname;

    private String SImage;

    public MainPageServicesModelClass(int sid, String sname, String SImage) {
        Sid = sid;
        Sname = sname;
        this.SImage = SImage;
    }

    public MainPageServicesModelClass() {
    }

    public int getSid() {
        return Sid;
    }

    public void setSid(int sid) {
        Sid = sid;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String sname) {
        Sname = sname;
    }

    public String getSImage() {
        return SImage;
    }

    public void setSImage(String SImage) {
        this.SImage = SImage;
    }
}
