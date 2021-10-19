package com.example.wmshw.model;

public class Vehicle {
    private String plugedNumber;
    private String driver;
    private String type;
    private String productionDate;
    private String registrationDate;
    private boolean crossOut;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isCrossOut() {
        return crossOut;
    }

    public void setCrossOut(boolean crossOut) {
        this.crossOut = crossOut;
    }
}
