package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Models.Destination;


public class TripRecommendationPaneController {

    @FXML
    private ImageView backgroundImageView, destinationImageView;
    @FXML
    private Label destinationLabel, priceLabel;

    private Destination destination;

    public Destination getDestination() {
        return destination;
    }

    public void setTripRecommendation(Destination destination) {
        this.destination = destination;
    }

    public void setupRecommendationPane(){
        destinationLabel.setText(destination.getCity() + ", " + destination.getCountry());
        priceLabel.setText("CAD " + getDestination().getPrice());

        backgroundImageView.setImage(new Image(getClass().getResource(destination.getImageURL()).toExternalForm()));
        destinationImageView.setImage(new Image(getClass().getResource(destination.getImageURL()).toExternalForm()));
    }
}
