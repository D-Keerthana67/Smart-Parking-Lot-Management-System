package com.smartparking.service;

import com.smartparking.model.*;
import com.smartparking.pricing.PricingStrategy;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class ParkingService {
    private final ParkingLot parkingLot;
    private final PricingStrategy pricingStrategy;

    public ParkingService(ParkingLot parkingLot, PricingStrategy pricingStrategy) {
        this.parkingLot = parkingLot;
        this.pricingStrategy = pricingStrategy;
    }

    public ParkingTicket park(Vehicle vehicle) {
        return parkingLot.parkVehicle(vehicle);
    }

    public BigDecimal exit(String ticketId) {
        ParkingTicket ticket = parkingLot.findActiveTicket(ticketId);
        Duration duration = Duration.between(ticket.getEntryTime(), LocalDateTime.now());
        BigDecimal fee = pricingStrategy.calculateFee(ticket.getVehicle().getType(), duration);
        parkingLot.completeParking(ticket);
        return fee;
    }
}
