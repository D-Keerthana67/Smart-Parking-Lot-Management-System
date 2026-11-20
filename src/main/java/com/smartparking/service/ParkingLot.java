package com.smartparking.service;

import com.smartparking.model.*;
import java.util.*;
import java.util.stream.Collectors;

public final class ParkingLot {
    private static final ParkingLot INSTANCE = new ParkingLot();
    private final List<ParkingSlot> slots = new ArrayList<>();
    private final Map<String, ParkingTicket> activeTickets = new HashMap<>();

    private ParkingLot() { }

    public static ParkingLot getInstance() { return INSTANCE; }

    public synchronized void addSlot(ParkingSlot slot) {
        boolean duplicate = slots.stream().anyMatch(s -> s.getSlotNumber() == slot.getSlotNumber());
        if (duplicate) throw new IllegalArgumentException("Slot number already exists.");
        slots.add(slot);
        slots.sort(Comparator.comparingInt(ParkingSlot::getSlotNumber));
    }

    public synchronized ParkingTicket parkVehicle(Vehicle vehicle) {
        if (activeTickets.containsKey(vehicle.getRegistrationNumber())) {
            throw new IllegalStateException("This vehicle is already parked.");
        }
        SlotType requiredType = SlotType.valueOf(vehicle.getType().name());
        ParkingSlot slot = slots.stream()
                .filter(ParkingSlot::isAvailable)
                .filter(s -> s.getSlotType() == requiredType)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No compatible parking slot is available."));
        slot.park(vehicle);
        ParkingTicket ticket = new ParkingTicket(vehicle, slot.getSlotNumber());
        activeTickets.put(vehicle.getRegistrationNumber(), ticket);
        return ticket;
    }

    public synchronized ParkingTicket findActiveTicket(String ticketId) {
        return activeTickets.values().stream()
                .filter(t -> t.getTicketId().equalsIgnoreCase(ticketId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Active ticket not found."));
    }

    public synchronized void completeParking(ParkingTicket ticket) {
        ParkingTicket active = findActiveTicket(ticket.getTicketId());
        ParkingSlot slot = slots.stream()
                .filter(s -> s.getSlotNumber() == active.getSlotNumber())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Parking slot not found."));
        slot.release();
        active.close();
        activeTickets.remove(active.getVehicle().getRegistrationNumber());
    }

    public synchronized List<ParkingSlot> getSlots() {
        return Collections.unmodifiableList(new ArrayList<>(slots));
    }

    public synchronized long getAvailableCount() {
        return slots.stream().filter(ParkingSlot::isAvailable).count();
    }

    public synchronized Map<SlotType, Long> getOccupancy() {
        return Arrays.stream(SlotType.values()).collect(Collectors.toMap(
                type -> type,
                type -> slots.stream().filter(s -> s.getSlotType() == type && !s.isAvailable()).count(),
                (a, b) -> a, () -> new EnumMap<>(SlotType.class)));
    }
}
