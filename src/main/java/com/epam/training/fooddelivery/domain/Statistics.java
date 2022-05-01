package com.epam.training.fooddelivery.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Statistics {
    private BigDecimal totalIncome;
    private double averageIncomeByOrder;
    private int numberOfFood;
    private int numberOfOrder;
    private LocalDate dayOfHighestIncome;

    public Statistics() {}


}
