package com.example.wmshw.model;

public class RegisterRequest {
    private String driver;
    private String plateNumber;
    private String repeatPlateNumber;
    private String type;
    private String productionDate;
    private boolean crossOut;

    public RegisterRequest(String driver, String plateNumber, String repeatPlateNumber, String type, String productionDate) {
        this.driver = driver;
        this.plateNumber = plateNumber;
        this.repeatPlateNumber = repeatPlateNumber;
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

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getRepeatPlateNumber() {
        return repeatPlateNumber;
    }

    public void setRepeatPlateNumber(String repeatPlateNumber) {
        this.repeatPlateNumber = repeatPlateNumber;
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
                ", plateNumber='" + plateNumber + '\'' +
                ", repeeatPlateNumber='" + repeatPlateNumber + '\'' +
                ", type='" + type + '\'' +
                ", productionDate='" + productionDate + '\'' +
                ", crossOut=" + crossOut +
                '}';
    }
}
