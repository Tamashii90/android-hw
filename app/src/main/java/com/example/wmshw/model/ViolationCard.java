package com.example.wmshw.model;

public class ViolationCard {
    private String plugedNumber;
    private String driver;
    private String location;
    private boolean paid;
    private String date;
    private long tax;

    public ViolationCard() {
    }

    public ViolationCard(String plugedNumber, String driver, String location, boolean paid, String date, long tax) {
        this.plugedNumber = plugedNumber;
        this.driver = driver;
        this.location = location;
        this.paid = paid;
        this.date = date;
        this.tax = tax;
    }

    public String getPlugedNumber() {
        return plugedNumber;
    }

    public void setPlugedNumber(String plugedNumber) {
        this.plugedNumber = plugedNumber;
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

    @Override
    public String toString() {
        return "{" +
                "plugedNumber='" + plugedNumber + '\'' +
                ", driver='" + driver + '\'' +
                ", location='" + location + '\'' +
                ", paid=" + paid +
                ", date=" + date +
                ", tax=" + tax +
                '}';
    }
}
