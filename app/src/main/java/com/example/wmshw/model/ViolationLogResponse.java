package com.example.wmshw.model;

import com.google.gson.JsonObject;


public class ViolationLogResponse {
    private JsonObject vehicle;
    private String location;
    private boolean paid;
    private String date;
    private JsonObject violation;

    public JsonObject getVehicle() {
        return vehicle;
    }

    public void setVehicle(JsonObject vehicle) {
        this.vehicle = vehicle;
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

    public JsonObject getViolation() {
        return violation;
    }

    public void setViolation(JsonObject violation) {
        this.violation = violation;
    }
}
