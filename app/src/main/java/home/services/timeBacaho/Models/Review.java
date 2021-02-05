package home.services.timeBacaho.Models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("userEmail")
    @Expose
    private String userEmail;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("timeday")
    @Expose
    private String timeday;
    @SerializedName("itemName")
    @Expose
    private String itemName;

    /**
     * No args constructor for use in serialization
     *
     */
    public Review() {
    }

    /**
     *
     * @param timeday
     * @param itemName
     * @param rating
     * @param userEmail
     * @param comment
     * @param id
     * @param userName
     * @param status
     */
    public Review(String id, String userEmail, String rating, String comment, String status, String userName, String timeday, String itemName) {
        super();
        this.id = id;
        this.userEmail = userEmail;
        this.rating = rating;
        this.comment = comment;
        this.status = status;
        this.userName = userName;
        this.timeday = timeday;
        this.itemName = itemName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTimeday() {
        return timeday;
    }

    public void setTimeday(String timeday) {
        this.timeday = timeday;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

}
