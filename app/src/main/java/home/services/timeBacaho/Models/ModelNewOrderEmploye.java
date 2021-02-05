package home.services.timeBacaho.Models;

public class ModelNewOrderEmploye {

int id;
String EmployeEmail, clientName, status, orderId, clientCity, clientCountry, clientAddress;
int clientPhone;
String dateService, timeService, message, cost, created_us;

    public ModelNewOrderEmploye() {
    }

    public ModelNewOrderEmploye(int id, String employeEmail, String clientName, String status, String orderId, String clientCity, String clientCountry, String clientAddress, int clientPhone, String dateService, String timeService, String message, String cost, String created_us) {
        this.id = id;
        this.EmployeEmail = employeEmail;
        this.clientName = clientName;
        this.status = status;
        this.orderId = orderId;
        this.clientCity = clientCity;
        this.clientCountry = clientCountry;
        this.clientAddress = clientAddress;
        this.clientPhone = clientPhone;
        this.dateService = dateService;
        this.timeService = timeService;
        this.message = message;
        this.cost = cost;
        this.created_us = created_us;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeEmail() {
        return EmployeEmail;
    }

    public void setEmployeEmail(String employeEmail) {
        EmployeEmail = employeEmail;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getClientCity() {
        return clientCity;
    }

    public void setClientCity(String clientCity) {
        this.clientCity = clientCity;
    }

    public String getClientCountry() {
        return clientCountry;
    }

    public void setClientCountry(String clientCountry) {
        this.clientCountry = clientCountry;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public int getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(int clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getDateService() {
        return dateService;
    }

    public void setDateService(String dateService) {
        this.dateService = dateService;
    }

    public String getTimeService() {
        return timeService;
    }

    public void setTimeService(String timeService) {
        this.timeService = timeService;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCreated_us() {
        return created_us;
    }

    public void setCreated_us(String created_us) {
        this.created_us = created_us;
    }
}
