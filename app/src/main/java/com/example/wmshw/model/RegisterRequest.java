package com.example.wmshw.model;

public class RegisterRequest {
    private String driver;
    private String plugedNumber;
    private String repeatPlugedNumber;
    private String type;
    private String productionDate;
    private boolean crossOut;

    public RegisterRequest(String driver, String plugedNumber, String repeatPlugedNumber, String type, String productionDate) {
        this.driver = driver;
        this.plugedNumber = plugedNumber;
        this.repeatPlugedNumber = repeatPlugedNumber;
        this.type = type;
        this.productionDate = productionDate;
        this.crossOut = false;
    }

    public RegisterRequest() {
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getPlugedNumber() {
        return plugedNumber;
    }

    public void setPlugedNumber(String plugedNumber) {
        this.plugedNumber = plugedNumber;
    }

    public String getRepeatPlugedNumber() {
        return repeatPlugedNumber;
    }

    public void setRepeatPlugedNumber(String repeatPlugedNumber) {
        this.repeatPlugedNumber = repeatPlugedNumber;
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

    public boolean isCrossOut() {
        return crossOut;
    }

    public void setCrossOut(boolean crossOut) {
        this.crossOut = crossOut;
    }

    @Override
    public String toString() {
        return "{" +
                "driver='" + driver + '\'' +
                ", plugedNumber='" + plugedNumber + '\'' +
                ", repeeatPlugedNumber='" + repeatPlugedNumber + '\'' +
                ", type='" + type + '\'' +
                ", productionDate='" + productionDate + '\'' +
                ", crossOut=" + crossOut +
                '}';
    }
}
