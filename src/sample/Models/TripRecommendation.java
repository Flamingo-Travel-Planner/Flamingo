package sample.Models;

public class TripRecommendation {

    private String destinationName;

    private int price;

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public TripRecommendation(String destinationName, int price){
        this.destinationName = destinationName;
        this.price = price;
    }
}
