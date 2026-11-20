package com.smartparking.pricing;

import com.smartparking.model.VehicleType;
import java.math.BigDecimal;
import java.time.Duration;
import java.math.RoundingMode;

public class StandardPricingStrategy implements PricingStrategy {
    private static final BigDecimal BIKE_RATE = new BigDecimal("10.00");
    private static final BigDecimal CAR_RATE = new BigDecimal("20.00");
    private static final BigDecimal TRUCK_RATE = new BigDecimal("40.00");

    @Override
    public BigDecimal calculateFee(VehicleType type, Duration duration) {
        long minutes = Math.max(1, duration.toMinutes());
        long billableHours = Math.max(1, (minutes + 59) / 60);
        BigDecimal rate = switch (type) {
            case BIKE -> BIKE_RATE;
            case CAR -> CAR_RATE;
            case TRUCK -> TRUCK_RATE;
        };
        return rate.multiply(BigDecimal.valueOf(billableHours))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
