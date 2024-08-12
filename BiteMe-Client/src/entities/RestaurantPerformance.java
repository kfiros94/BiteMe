package entities;

import java.util.ArrayList;
import java.util.List;

public class RestaurantPerformance {
    private String restaurantName;
    private double performanceMetric;

    // Getters and Setters

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public double getPerformanceMetric() {
        return performanceMetric;
    }

    public void setPerformanceMetric(double performanceMetric) {
        this.performanceMetric = performanceMetric;
    }

    // Method to convert received data into a list of RestaurantPerformance objects
    public static List<RestaurantPerformance> fromString(String data) {
        // Convert the string data to a list of RestaurantPerformance objects
        // Implement parsing logic based on the format of the received data
        // This is a placeholder implementation
        List<RestaurantPerformance> performanceList = new ArrayList<>();
        // Example parsing logic
        String[] entries = data.split(",");
        for (String entry : entries) {
            String[] parts = entry.split(":");
            RestaurantPerformance performance = new RestaurantPerformance();
            performance.setRestaurantName(parts[0].trim());
            performance.setPerformanceMetric(Double.parseDouble(parts[1].trim()));
            performanceList.add(performance);
        }
        return performanceList;
    }
}