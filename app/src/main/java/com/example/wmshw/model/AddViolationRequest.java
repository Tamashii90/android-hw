package com.example.wmshw.model;

public class AddViolationRequest {
    private String plateNumber;
    private String violationType;
    private String location;
    private boolean paid;

    public AddViolationRequest() {
    }

    public AddViolationRequest(String plateNumber, String violationType, String location) {
        this.plateNumber = plateNumber;
        this.violationType = violationType;
        this.location = location;
        this.paid = false;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getViolationType() {
        return violationType;
    }

    public void setViolationType(String violationType) {
        this.violationType = violationType;
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
}
