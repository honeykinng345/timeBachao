package home.services.timeBacaho.Models;

public class subCategories {

    int id,cid;
    String title,description,duration;
   String Discountprices,Simage,OrigonalPrice,discountNotes,discountAvaliabel;

    public subCategories() {
    }

    public subCategories(int id, int cid, String title, String description, String duration, String discountprices, String simage, String origonalPrice, String discountNotes, String discountAvaliabel) {
        this.id = id;
        this.cid = cid;
        this.title = title;
        this.description = description;
        this.duration = duration;
        Discountprices = discountprices;
        Simage = simage;
        OrigonalPrice = origonalPrice;
        this.discountNotes = discountNotes;
        this.discountAvaliabel = discountAvaliabel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDiscountprices() {
        return Discountprices;
    }

    public void setDiscountprices(String discountprices) {
        Discountprices = discountprices;
    }

    public String getSimage() {
        return Simage;
    }

    public void setSimage(String simage) {
        Simage = simage;
    }

    public String getOrigonalPrice() {
        return OrigonalPrice;
    }

    public void setOrigonalPrice(String origonalPrice) {
        OrigonalPrice = origonalPrice;
    }

    public String getDiscountNotes() {
        return discountNotes;
    }

    public void setDiscountNotes(String discountNotes) {
        this.discountNotes = discountNotes;
    }

    public String getDiscountAvaliabel() {
        return discountAvaliabel;
    }

    public void setDiscountAvaliabel(String discountAvaliabel) {
        this.discountAvaliabel = discountAvaliabel;
    }
}

