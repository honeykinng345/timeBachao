package home.services.timeBacaho.Models;

public class ModelOrderItem {
int id;
    String  Itemname, cost;
    int pid;
    String price;

    int quantity;
    String timestamps;

    public ModelOrderItem() {
    }

    public ModelOrderItem (int id, String itemname, String cost, int pid, String price, int quantity, String timestamps) {
        this.id = id;
        Itemname = itemname;
        this.cost = cost;
        this.pid = pid;
        this.price = price;
        this.quantity = quantity;
        this.timestamps = timestamps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemname() {
        return Itemname;
    }

    public void setItemname(String itemname) {
        Itemname = itemname;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTimestamp() {
        return timestamps;
    }

    public void setTimestamp(String timestamp) {
        this.timestamps = timestamp;
    }
}
