package com.smartparking.model;

public class Bike extends Vehicle {
    public Bike(String registrationNumber) {
        super(registrationNumber, VehicleType.BIKE);
    }
}
