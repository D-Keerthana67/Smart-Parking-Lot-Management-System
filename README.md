# Smart Parking Lot Management System

A console-based parking management application built with **Java 17** and **Maven**. It manages bikes, cars, and trucks, assigns the lowest-numbered compatible free slot, generates parking tickets, calculates parking fees, and releases slots when vehicles exit.

## Features

- Supports bikes, cars, and trucks.
- Assigns the first available compatible slot (slots are checked in ascending slot-number order).
- Prevents the same registration number from being parked twice at the same time.
- Generates a unique ticket ID for every parking session.
- Calculates fees based on vehicle type and parking duration.
- Releases the slot after successful payment/exit.
- Displays slot status, occupancy by type, and available-slot count.
- Handles invalid options, unavailable slots, duplicate vehicles, and unknown tickets.

## Technology

- Java 17
- Maven
- Java Collections and Streams
- `LocalDateTime` and `Duration`
- `BigDecimal` for fee calculations

## Design patterns and OOP

- **Singleton:** `ParkingLot` provides one shared parking-lot instance.
- **Factory:** `VehicleFactory` creates the correct vehicle subclass.
- **Strategy:** `PricingStrategy` allows the fee calculation policy to be changed independently.
- **Inheritance and polymorphism:** `Bike`, `Car`, and `Truck` extend `Vehicle`.
- **Encapsulation:** model state is accessed through methods.

## Parking and pricing rules

Each vehicle type uses a matching slot type. A bike uses a bike slot, a car uses a car slot, and a truck uses a truck slot. The system selects the available compatible slot with the lowest slot number.

| Vehicle | Rate |
|---|---:|
| Bike | Rs. 10 per hour |
| Car | Rs. 20 per hour |
| Truck | Rs. 40 per hour |

A minimum of one hour is charged. Any partial hour is rounded up to the next hour. The sample rates and slot inventory are demonstration values and can be changed.

## Project structure

```text
src/main/java/com/smartparking/
├── Main.java
├── factory/
│   └── VehicleFactory.java
├── model/
│   ├── Bike.java
│   ├── Car.java
│   ├── ParkingSlot.java
│   ├── ParkingTicket.java
│   ├── SlotType.java
│   ├── Truck.java
│   ├── Vehicle.java
│   └── VehicleType.java
├── pricing/
│   ├── PricingStrategy.java
│   └── StandardPricingStrategy.java
└── service/
    ├── ParkingLot.java
    └── ParkingService.java
```

## Run the project

Install Java 17 and Maven, then run these commands from the project directory:

```bash
mvn clean compile
mvn exec:java
```

## Sample workflow

1. Select **Park vehicle**.
2. Enter a type such as `CAR` and a registration number.
3. Save the generated ticket ID and slot number.
4. Select **Exit vehicle** and enter the ticket ID.
5. The application calculates the fee and makes the slot available again.

## Future enhancements

- Store vehicles, tickets, and payments in MySQL.
- Add multiple floors and distance-based slot selection.
- Add configurable pricing, discounts, and lost-ticket handling.
- Build REST APIs and a web interface using Spring Boot.
- Add automated unit tests with JUnit.

## Author

Keerthana Devan
