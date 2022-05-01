package com.epam.training.fooddelivery;

import com.epam.training.fooddelivery.data.DataStore;
import com.epam.training.fooddelivery.data.FileBasedDataStore;
import com.epam.training.fooddelivery.service.FoodDeliveryService;
import com.epam.training.fooddelivery.view.View;

import java.time.LocalDate;

public class Application {
    private final View view = new View();
    private final DataStore dataStore = new FileBasedDataStore(
            "C:\\Users\\Dell\\IdeaProjects\\java-basics-2-food-delivery\\input\\orders.csv"
    );
    private FoodDeliveryService service = new FoodDeliveryService(dataStore);

    public static void main(String[] args) {
        Application application = new Application();
        application.start();
    }

    private void start() {
        view.printWelcomeMessage();

        view.printMostExpensiveOrder(service.getMostExpensiveOrder());
        view.printMostPopularFood(service.getMostPopularFood());
        view.printMostLoyalCustomer(service.getMostLoyalCustomer());
        System.out.println("");
        LocalDate startDate = view.readStartDate();
        LocalDate endDate = view.readEndDate();
        System.out.println("");
        view.printStatistics(service.getStatistics(startDate, endDate),startDate, endDate);
    }
}
