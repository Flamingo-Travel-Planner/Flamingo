package sample.Models;

public class Place {

    private String address, name, imageUrl;
    private double latitude, longitude, price;

    public Place(String address, String name, String imageUrl, double latitude, double longitude, double price) {
        this.address = address;
        this.name = name;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
    }

    @Override
    public String toString() {
        return "\n" + this.name + ", located at " + this.address + " (image: " + imageUrl + ")";
    }

    /****** Getters *****/
    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
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
