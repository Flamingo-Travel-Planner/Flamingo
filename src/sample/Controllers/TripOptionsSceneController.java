package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import sample.Models.Destination;
import sample.managers.AppManager;
import sample.utils.FXMLTool;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class TripOptionsSceneController implements Initializable, Observer {

    private static final String TRIP_RECOMMENDATION_PANE_URL = "../FXML/tripRecommendationPane.fxml";

    @FXML
    private Label balanceLabel;
    @FXML
    private VBox recommendationsContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        AppManager.getInstance().addObserver(this);
    }

    public void loadRecommendations(){

        balanceLabel.setText("Balance: CAD " + AppManager.getInstance().getAppData().getBudget());

        for(Destination destination: AppManager.getInstance().getAppData().getRecommendationsList()){
            try {
                Pair<Parent, TripRecommendationPaneController> parentControllerPair = FXMLTool.loadParentController(getClass().getResource(TRIP_RECOMMENDATION_PANE_URL));
                parentControllerPair.getKey().setOnMouseClicked(event -> setupTrip(destination));
                parentControllerPair.getValue().setTripRecommendation(destination);
                recommendationsContainer.getChildren().add(parentControllerPair.getKey());
                parentControllerPair.getValue().setupRecommendationPane();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private void setupTrip(Destination selectedDestination){
        AppManager.getInstance().getAppData().setSelectedTrip(selectedDestination);
        AppManager.getInstance().changeWorkStageScene(AppManager.getInstance().getTripSetupScene());
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg == AppManager.getInstance().getTripOptionsScene()){
            loadRecommendations();
        }
    }

}
