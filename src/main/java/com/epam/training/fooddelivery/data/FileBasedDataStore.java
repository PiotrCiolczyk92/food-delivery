package com.epam.training.fooddelivery.data;

import com.epam.training.fooddelivery.domain.Food;
import com.epam.training.fooddelivery.domain.Order;
import com.epam.training.fooddelivery.domain.OrderItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FileBasedDataStore implements DataStore {
    private String filePath;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
            .localizedBy(Locale.GERMAN);
    private List<Order> orders = new ArrayList<>();

    public FileBasedDataStore(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Order> getOrders() {
        String line;
        try (BufferedReader reader =
                     Files.newBufferedReader(Paths.get(this.filePath))) {
            while ((line = reader.readLine()) != null) {
                String[] lineRecords = line.split(",");
                if(!orderIsPresent(Long.parseLong(lineRecords[0]))) {
                    Order order = new Order();
                    order.setOrderItems(new ArrayList<>());
                    order.setId(Long.parseLong(lineRecords[0]));
                    order.setCustomerId(Long.parseLong(lineRecords[1]));
                    order.setOrderDate(LocalDateTime.parse(lineRecords[2], dateFormatter));
                    OrderItem orderItem = new OrderItem();
                    Food food = findFoodTypeByName(lineRecords[3]);
                    orderItem.setFood(food);
                    orderItem.setPrice(food.getPrice());
                    orderItem.setAmount(Integer.parseInt(lineRecords[4]));
                    order.getOrderItems().add(orderItem);
                    if(order.getTotalPrice() == null) {
                        order.setTotalPrice(orderItem.getTotalPrice());
                    } else {
                        order.setTotalPrice(order.getTotalPrice().add(orderItem.getTotalPrice()));
                    }
                    orders.add(order);
                } else if (orderIsPresent(Long.parseLong(lineRecords[0]))){
                    Order order = findOrderById(Long.parseLong(lineRecords[0]));
                    OrderItem orderItem = new OrderItem();
                    Food food = findFoodTypeByName(lineRecords[3]);
                    orderItem.setFood(food);
                    orderItem.setPrice(food.getPrice());
                    orderItem.setAmount(Integer.parseInt(lineRecords[4]));
                    order.getOrderItems().add(orderItem);
                    if(order.getTotalPrice() == null) {
                        order.setTotalPrice(orderItem.getTotalPrice());
                    } else {
                        order.setTotalPrice(order.getTotalPrice().add(orderItem.getTotalPrice()));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private static List<Food> getFoodType() {
        List<Food> items = new ArrayList<>();
        items.add(new Food("Fideua", BigDecimal.valueOf(15)));
        items.add(new Food("Paella", BigDecimal.valueOf(13)));
        items.add(new Food("Tortilla", BigDecimal.valueOf(10)));
        items.add(new Food("Gazpacho", BigDecimal.valueOf(8)));
        items.add(new Food("Quesadilla", BigDecimal.valueOf(13)));
        return items;
    }

    private Food findFoodTypeByName(String nameOfFood) {
        return getFoodType().stream()
                .filter(food -> food.getName().equals(nameOfFood)).findFirst().get();
    }

    private boolean orderIsPresent(long id) {
        return this.orders.stream().anyMatch(order -> order.getId() == id);
    }

    private Order findOrderById(long id) {
       return this.orders.stream().filter(order -> order.getId() == id).findFirst().get();
    }

}
