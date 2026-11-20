package com.smartparking.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class ParkingTicket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final int slotNumber;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public ParkingTicket(Vehicle vehicle, int slotNumber) {
        this.ticketId = UUID.randomUUID().toString().toUpperCase();
        this.vehicle = vehicle;
        this.slotNumber = slotNumber;
        this.entryTime = LocalDateTime.now();
    }

    public String getTicketId() { return ticketId; }
    public Vehicle getVehicle() { return vehicle; }
    public int getSlotNumber() { return slotNumber; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }
    public boolean isActive() { return exitTime == null; }
    public void close() {
        if (!isActive()) throw new IllegalStateException("Ticket is already closed.");
        exitTime = LocalDateTime.now();
    }
}
