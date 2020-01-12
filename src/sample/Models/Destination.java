package sample.Models;

// http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class Destination {
    private final String IMAGE_PATH = "../media/destinations/";

    private String city, country, description, imageURL;

    private double latitude, longitude, price;

    public Destination(String city) {
        // Auto-populate fields based on city
        Object obj = null;
        try {
            obj = new JSONParser().parse(new FileReader("./src/sample/data/destinations.json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            throw new IllegalStateException("Couldn't read file :(");
        }

        // typecasting obj to JSONObject
        JSONArray jsonDestinations = (JSONArray) obj;

        for (Object destinationObj : jsonDestinations) {
            JSONObject destination = (JSONObject) destinationObj;

            if (destination.get("city").equals(city)) {
                this.city = (String) destination.get("city");
                this.country = (String) destination.get("country");
                this.description = (String) destination.get("description");
                this.latitude = (double) destination.get("latitude");
                this.longitude = (double) destination.get("longitude");
                this.price = (double) destination.get("price");
                this.imageURL = IMAGE_PATH + city.toLowerCase() + ".jpg";
                break;
            }
        }

        // Check if any of the attributes is null to see if it worked :)
        if (this.country == null) {
            throw new IllegalStateException("Attributes were not correctly filled");
        }
    }

    @Override
    public String toString() {
        return "\n\nCity: " + city + "\nCountry: " + country + "\nLatitude: " + latitude + "\nLongitude: " + longitude +
                "\nPrice: " + price + "\nDescription: " + description;
    }

    @Override
    public boolean equals(Object destObj) {
        if (!(destObj instanceof Destination)) return false;

        Destination dest = (Destination) destObj;
        return this.city.equals(dest.city) && this.country.equals(dest.country);
    }



    /*********** Getters ***********/
    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getPrice() {
        return price;
    }
}
