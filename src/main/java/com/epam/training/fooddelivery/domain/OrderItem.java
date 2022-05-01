package com.epam.training.fooddelivery.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {
    private Food food;
    private int amount;
    private BigDecimal price;

    @Override
    public String toString() {
        return "OrderItem{" +
                "food=" + food +
                ", amount=" + amount +
                ", price=" + price +
                "}\n";
    }

    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(amount));
    }
}
