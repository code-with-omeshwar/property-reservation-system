package com.omeshwar.project.airBNB.strategy;

import com.omeshwar.project.airBNB.entity.Inventory;

import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal calculatePrice(Inventory inventory);
}
