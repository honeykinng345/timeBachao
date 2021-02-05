package home.services.timeBacaho.Models;



public class categories {
    private int cid;
    private String name;

    private String image;

    public categories() {
    }

    public categories(int cid, String name, String image) {
        this.cid = cid;
        this.name = name;
        this.image = image;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

