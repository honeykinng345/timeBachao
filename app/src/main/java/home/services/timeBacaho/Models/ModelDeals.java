package home.services.timeBacaho.Models;

public class ModelDeals {

    int id;
    String dealName,shortDescription,longDescription,Image,Cost,timeDuration;

    public ModelDeals() {
    }

    public ModelDeals(int id, String dealName, String shortDescription, String longDescription, String image, String cost, String timeDuration) {
        this.id = id;
        this.dealName = dealName;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.Image = image;
        this.Cost = cost;
        this.timeDuration = timeDuration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(String timeDuration) {
        this.timeDuration = timeDuration;
    }
}
