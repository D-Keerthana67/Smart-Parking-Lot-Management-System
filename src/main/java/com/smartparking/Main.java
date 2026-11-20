package com.smartparking;

import com.smartparking.factory.VehicleFactory;
import com.smartparking.model.*;
import com.smartparking.pricing.StandardPricingStrategy;
import com.smartparking.service.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ParkingLot lot = ParkingLot.getInstance();
        lot.addSlot(new ParkingSlot(1, SlotType.BIKE));
        lot.addSlot(new ParkingSlot(2, SlotType.BIKE));
        lot.addSlot(new ParkingSlot(3, SlotType.CAR));
        lot.addSlot(new ParkingSlot(4, SlotType.CAR));
        lot.addSlot(new ParkingSlot(5, SlotType.TRUCK));

        ParkingService service = new ParkingService(lot, new StandardPricingStrategy());
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== SMART PARKING LOT =====");
            System.out.println("1. Park vehicle");
            System.out.println("2. Exit vehicle");
            System.out.println("3. View parking slots");
            System.out.println("4. View occupancy");
            System.out.println("5. Exit application");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> {
                        System.out.print("Vehicle type (BIKE/CAR/TRUCK): ");
                        VehicleType type = VehicleType.valueOf(sc.nextLine().trim().toUpperCase());
                        System.out.print("Registration number: ");
                        String number = sc.nextLine();
                        Vehicle vehicle = VehicleFactory.createVehicle(type, number);
                        ParkingTicket ticket = service.park(vehicle);
                        System.out.println("\nVehicle parked successfully!");
                        System.out.println("Ticket ID: " + ticket.getTicketId());
                        System.out.println("Slot number: " + ticket.getSlotNumber());
                        System.out.println("Entry time: " + ticket.getEntryTime());
                    }
                    case "2" -> {
                        System.out.print("Enter ticket ID: ");
                        String ticketId = sc.nextLine().trim();
                        System.out.println("Parking fee: Rs. " + service.exit(ticketId));
                        System.out.println("Vehicle exited. Slot released successfully.");
                    }
                    case "3" -> {
                        System.out.println("\n--- SLOT STATUS ---");
                        for (ParkingSlot slot : lot.getSlots()) {
                            String status = slot.isAvailable() ? "AVAILABLE"
                                    : "OCCUPIED - " + slot.getParkedVehicle().getRegistrationNumber();
                            System.out.println("Slot " + slot.getSlotNumber() + " (" + slot.getSlotType() + "): " + status);
                        }
                    }
                    case "4" -> {
                        System.out.println("\nOccupied slots by type: " + lot.getOccupancy());
                        System.out.println("Available slots: " + lot.getAvailableCount());
                    }
                    case "5" -> {
                        System.out.println("Thank you for using Smart Parking Lot!");
                        sc.close();
                        return;
                    }
                    default -> System.out.println("Invalid option. Please choose 1 to 5.");
                }
            } catch (IllegalArgumentException | IllegalStateException | java.util.NoSuchElementException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
