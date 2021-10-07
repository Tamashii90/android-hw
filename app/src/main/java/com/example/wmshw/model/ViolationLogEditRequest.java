package com.example.wmshw.model;


public class ViolationLogEditRequest {
    private String type;
    private String location;
    private String date;
    private boolean paid;

    public ViolationLogEditRequest(String type, String location, String date, boolean paid) {
        this.type = type;
        this.location = location;
        this.date = date;
        this.paid = paid;
    }

    public ViolationLogEditRequest() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
