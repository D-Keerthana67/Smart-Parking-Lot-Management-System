package com.smartparking.factory;

import com.smartparking.model.*;

public final class VehicleFactory {
    private VehicleFactory() { }

    public static Vehicle createVehicle(VehicleType type, String registrationNumber) {
        return switch (type) {
            case BIKE -> new Bike(registrationNumber);
            case CAR -> new Car(registrationNumber);
            case TRUCK -> new Truck(registrationNumber);
        };
    }
}
