package com.example.wmshw.model;

public class AddViolationRequest {
    private String plugedNumber;
    private String violationType;
    private String location;
    private boolean paid;

    public AddViolationRequest() {
    }

    public AddViolationRequest(String plugedNumber, String violationType, String location) {
        this.plugedNumber = plugedNumber;
        this.violationType = violationType;
        this.location = location;
        this.paid = false;
    }

    public String getPlugedNumber() {
        return plugedNumber;
    }

    public void setPlugedNumber(String plugedNumber) {
        this.plugedNumber = plugedNumber;
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
