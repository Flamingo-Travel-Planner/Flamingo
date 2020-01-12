package sample.Models;

public class Place {

    private String address, name, imageId;
    private double latitude, longitude, price;

    public Place(String address, String name, String imageId, double latitude, double longitude, double price) {
        this.address = address;
        this.name = name;
        this.imageId = imageId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
    }

    @Override
    public String toString() {
        return "\n" + this.name + ", located at " + this.address + " (image: " + imageId + ")";
    }

    /****** Getters *****/
    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getImageId() {
        return imageId;
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
