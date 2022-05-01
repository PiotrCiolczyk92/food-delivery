package com.epam.training.fooddelivery.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {
    private long id;
    private List<OrderItem> orderItems;
    private long customerId;
    private BigDecimal totalPrice;
    private LocalDateTime orderDate;

    public Order() {
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderItems=" + orderItems +
                ", customerId=" + customerId +
                ", totalPrice=" + totalPrice +
                ", orderDate=" + orderDate +
                "}\n";
    }
}
