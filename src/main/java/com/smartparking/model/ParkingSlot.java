package com.smartparking.model;

public class ParkingSlot {
    private final int slotNumber;
    private final SlotType slotType;
    private Vehicle parkedVehicle;

    public ParkingSlot(int slotNumber, SlotType slotType) {
        if (slotNumber <= 0) throw new IllegalArgumentException("Slot number must be positive.");
        this.slotNumber = slotNumber;
        this.slotType = slotType;
    }

    public int getSlotNumber() { return slotNumber; }
    public SlotType getSlotType() { return slotType; }
    public Vehicle getParkedVehicle() { return parkedVehicle; }
    public boolean isAvailable() { return parkedVehicle == null; }

    public void park(Vehicle vehicle) {
        if (!isAvailable()) throw new IllegalStateException("Slot is already occupied.");
        parkedVehicle = vehicle;
    }

    public void release() {
        if (isAvailable()) throw new IllegalStateException("Slot is already empty.");
        parkedVehicle = null;
    }
}
