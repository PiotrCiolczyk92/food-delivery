package com.epam.training.fooddelivery.view;

import com.epam.training.fooddelivery.domain.Food;
import com.epam.training.fooddelivery.domain.Order;
import com.epam.training.fooddelivery.domain.Statistics;

import java.time.LocalDate;
import java.util.Scanner;

public class View {
    private final Scanner scanner;

    public View() {
        this.scanner = new Scanner(System.in);
    }

    public void printWelcomeMessage() {
        System.out.println("Welcome to Food Delivery Service\n");
    }

    public LocalDate readStartDate() {
        System.out.print("Enter the start date (DD-MM-YYYY): ");
        String userInput = scanner.nextLine();
        String[] UI = userInput.split("-");
        return LocalDate.of(
                Integer.parseInt(UI[2]),
                Integer.parseInt(UI[1]),
                Integer.parseInt(UI[0]));
    }

    public LocalDate readEndDate() {
        System.out.print("Enter the end date (DD-MM-YYYY): ");
        String userInput = scanner.nextLine();
        String[] UI = userInput.split("-");
        return LocalDate.of(
                Integer.parseInt(UI[2]),
                Integer.parseInt(UI[1]),
                Integer.parseInt(UI[0]));
    }

    public void printMostExpensiveOrder(Order order) {
        System.out.println("The most expensive order was #" + order.getId()
                + "with a total price of " + order.getTotalPrice() + " EUR");
    }

    public void printMostPopularFood(Food food) {
        System.out.println("The most popular food is " + food.getName());
    }

    public void printMostLoyalCustomer(long customerId) {
        System.out.println("The customer who ordered the most was: #" + customerId);
    }

    public void printStatistics(Statistics statistics, LocalDate startDate, LocalDate endDate) {
        System.out.println("The statistics between " + startDate
                + " and " + endDate + ":");
        System.out.println("The total income was: " + statistics.getTotalIncome() + " EUR");
        System.out.println("The average income per order: "
                + String.format("%.2f", statistics.getAverageIncomeByOrder()) + " EUR");
        System.out.println("There were " + statistics.getNumberOfFood() + " dishes served");
        System.out.println("There were " + statistics.getNumberOfOrder() + " orders made");
        System.out.println("The day with the highest income: " + statistics.getDayOfHighestIncome());
    }


}
