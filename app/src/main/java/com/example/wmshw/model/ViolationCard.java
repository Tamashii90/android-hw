package com.example.wmshw.model;

public class ViolationCard {
    private long id;
    private String plateNumber;
    private String driver;
    private String type;
    private String location;
    private boolean paid;
    private String date;
    private long tax;

    public ViolationCard() {
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTax() {
        return tax;
    }

    public void setTax(long tax) {
        this.tax = tax;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ViolationCard{" +
                "id=" + id +
                ", plateNumber='" + plateNumber + '\'' +
                ", driver='" + driver + '\'' +
                ", type='" + type + '\'' +
                ", location='" + location + '\'' +
                ", paid=" + paid +
                ", date='" + date + '\'' +
                ", tax=" + tax +
                '}';
    }
}
