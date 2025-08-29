package com.omeshwar.project.airBNB.strategy;

import com.omeshwar.project.airBNB.entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class SurgePricingStrategy implements PricingStrategy {

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
       return wrapped.calculatePrice(inventory).multiply(inventory.getSurgeFactor());
    }
}
