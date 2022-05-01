package com.epam.training.fooddelivery.service;

import com.epam.training.fooddelivery.data.DataStore;
import com.epam.training.fooddelivery.domain.Food;
import com.epam.training.fooddelivery.domain.Order;
import com.epam.training.fooddelivery.domain.OrderItem;
import com.epam.training.fooddelivery.domain.Statistics;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class FoodDeliveryService {
    private final DataStore dataStore;
    private final List<Order> orders;

    public FoodDeliveryService(DataStore dataStore) {
        this.dataStore = dataStore;
        this.orders = dataStore.getOrders();
    }


    public List<Order> getOrders(LocalDate startDate, LocalDate endDate) throws NoSuchElementException {
        List<Order> foundOrders = this.orders.stream()
                .filter(data -> data.getOrderDate().toLocalDate().isEqual(startDate)
                        || data.getOrderDate().toLocalDate().isAfter(startDate))
                .collect(Collectors.toList()).stream()
                .filter(data -> data.getOrderDate().toLocalDate().isEqual(endDate)
                        || data.getOrderDate().toLocalDate().isBefore(endDate))
                .collect(Collectors.toList());
        if (foundOrders.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return foundOrders;
        }
    }

    public Order getMostExpensiveOrder() {
        return this.orders.stream()
                .max(Comparator.comparing(Order::getTotalPrice)).get();

    }

    public Food getMostPopularFood() {
        List<OrderItem> listOrderItems =
                this.orders.stream().flatMap(a -> a.getOrderItems().stream()).collect(Collectors.toList());
        Map<Food, Integer> foods = new HashMap<>();
        for (OrderItem item : listOrderItems) {
            if (!foods.containsKey(item.getFood())) {
                foods.put(item.getFood(), item.getAmount());
            } else {
                foods.put(item.getFood(), (foods.get(item.getFood()) + item.getAmount()));
            }
        }
        Map.Entry<Food, Integer> maxEntry = null;
        for (Map.Entry<Food, Integer> entry : foods.entrySet()) {
            if (maxEntry == null || entry.getValue()
                    .compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }

    public long getMostLoyalCustomer() {
        BigDecimal mostLoyal = BigDecimal.ZERO;
        long mostLoyalCustomer = 0;
        Set<Long> customerIds = this.orders.stream()
                .map(Order::getCustomerId).collect(Collectors.toSet());
        for (long id : customerIds) {
            BigDecimal customerSpending =
                    this.orders.stream().filter(order -> order.getId() == order.getCustomerId()).collect(Collectors.toList())
                            .stream().map(Order::getTotalPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
            if (mostLoyal.compareTo(customerSpending) < 0) {
                mostLoyal = customerSpending;
                mostLoyalCustomer = id;
            }
        }
        return mostLoyalCustomer;
    }

    public Statistics getStatistics(LocalDate startDate, LocalDate endDate) {
        Statistics statistics = new Statistics();
        List<Order> searchPeriod = getOrders(startDate, endDate);
        statistics.setNumberOfOrder(searchPeriod.size());
        BigDecimal totalIncome = searchPeriod.stream()
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        statistics.setTotalIncome(totalIncome);
        statistics.setAverageIncomeByOrder(totalIncome.doubleValue() / searchPeriod.size());
        List<OrderItem> orderItems = searchPeriod.stream()
                .flatMap(order -> order.getOrderItems().stream()).collect(Collectors.toList());
        int amount = orderItems.stream().map(OrderItem::getAmount).collect(Collectors.toList())
                .stream().reduce(0, Integer::sum);
        statistics.setNumberOfFood(amount);
        LocalDateTime dayWhenHighestIncome =
                searchPeriod.stream().max(Comparator.comparing(Order::getTotalPrice)).get().getOrderDate();
        statistics.setDayOfHighestIncome(dayWhenHighestIncome.toLocalDate());
        return statistics;
    }

}
