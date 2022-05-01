package com.epam.training.fooddelivery.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Food {
    private String name;
    private BigDecimal price;

    @Override
    public String toString() {
        return "Name: " + this.name;
    }
}
