package sample.Models;

import java.time.Duration;

public class Attraction extends Place {

    public enum Category {
        ENTERTAINMENT, MUSEUM, RELIGION, OUTDOOR_PARK, NIGHTLIFE, SHOPPING;
    }

    private Category category;
    private String subcategory;
    private Duration timeSpent;

    public Attraction(String address, String name, String imageUrl, double latitude, double longitude,
                      double price, Category category, String subcategory, Duration timeSpent) {
        super(address, name, imageUrl, latitude, longitude, price);
        this.category = category;
        this.subcategory = subcategory;
        this.timeSpent = timeSpent;
    }

    /****** Getters *****/
    public Category getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public Duration getTimeSpent() {
        return timeSpent;
    }
}
