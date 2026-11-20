package com.smartparking.pricing;

import com.smartparking.model.VehicleType;
import java.math.BigDecimal;
import java.time.Duration;

public interface PricingStrategy {
    BigDecimal calculateFee(VehicleType type, Duration duration);
}
