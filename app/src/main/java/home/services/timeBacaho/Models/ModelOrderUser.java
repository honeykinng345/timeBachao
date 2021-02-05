package home.services.timeBacaho.Models;

public class ModelOrderUser {
     int id;
     String orderId, orderTime, myLongitude, mylatitude,
             orderCost;
         String     orderStatus, timeday, message, selectUserdate, timeUserPick, userEmail;
     int Phone;
public  ModelOrderUser(){

}
    public ModelOrderUser(int id, String orderId, String orderTime, String myLongitude, String mylatitude, String orderCost, String orderStatus, String timeday, String message, String selectUserdate, String timeUserPick, String userEmail, int phone) {
        this.id = id;
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.myLongitude = myLongitude;
        this.mylatitude = mylatitude;
        this.orderCost = orderCost;
        this.orderStatus = orderStatus;
        this.timeday = timeday;
        this.message = message;
        this.selectUserdate = selectUserdate;
        this.timeUserPick = timeUserPick;
        this.userEmail = userEmail;
        Phone = phone;
    }

    public ModelOrderUser(int id, String orderId, String orderTime, String myLongitude, String mylatitude, String orderCost, String orderStatus, String timeday, String selectUserdate, String timeUserPick, String userEmail, int phone) {
        this.id = id;
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.myLongitude = myLongitude;
        this.mylatitude = mylatitude;
        this.orderCost = orderCost;
        this.orderStatus = orderStatus;
        this.timeday = timeday;
        this.message = message;
        this.selectUserdate = selectUserdate;
        this.timeUserPick = timeUserPick;
        this.userEmail = userEmail;
        Phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getMyLongitude() {
        return myLongitude;
    }

    public void setMyLongitude(String myLongitude) {
        this.myLongitude = myLongitude;
    }

    public String getMylatitude() {
        return mylatitude;
    }

    public void setMylatitude(String mylatitude) {
        this.mylatitude = mylatitude;
    }

    public String getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(String orderCost) {
        this.orderCost = orderCost;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTimeday() {
        return timeday;
    }

    public void setTimeday(String timeday) {
        this.timeday = timeday;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSelectUserdate() {
        return selectUserdate;
    }

    public void setSelectUserdate(String selectUserdate) {
        this.selectUserdate = selectUserdate;
    }

    public String getTimeUserPick() {
        return timeUserPick;
    }

    public void setTimeUserPick(String timeUserPick) {
        this.timeUserPick = timeUserPick;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getPhone() {
        return Phone;
    }

    public void setPhone(int phone) {
        Phone = phone;
    }
}