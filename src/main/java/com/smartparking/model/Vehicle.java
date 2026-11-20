package com.smartparking.model;

import java.util.Objects;

public abstract class Vehicle {
    private final String registrationNumber;
    private final VehicleType type;

    protected Vehicle(String registrationNumber, VehicleType type) {
        if (registrationNumber == null || registrationNumber.isBlank()) {
            throw new IllegalArgumentException("Registration number cannot be empty.");
        }
        this.registrationNumber = registrationNumber.trim().toUpperCase();
        this.type = Objects.requireNonNull(type);
    }

    public String getRegistrationNumber() { return registrationNumber; }
    public VehicleType getType() { return type; }
}
