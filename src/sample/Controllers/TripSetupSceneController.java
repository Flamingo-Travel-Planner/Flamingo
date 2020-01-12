package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import sample.Models.Attraction;
import sample.managers.AppManager;
import sample.utils.AttractionRecommender;
import sample.utils.FXMLTool;

import java.net.URL;
import java.util.*;

public class TripSetupSceneController implements Initializable, Observer {

    private static final String INTEREST_CATEGORY_PANE_URL = "../FXML/interestCategoryPane.fxml";

    @FXML
    private Label destinationLabel, balanceLabel;
    @FXML
    private VBox categoriesContainer;

    private HashMap<Attraction.Category, List<Attraction>> attractionsByCategory;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AppManager.getInstance().addObserver(this);
    }

    public void loadSuggestions(){

        destinationLabel.setText(AppManager.getInstance().getAppData().getSelectedTrip().getCity() + ", " + AppManager.getInstance().getAppData().getSelectedTrip().getCountry());

        balanceLabel.setText("Balance: CAD " + AppManager.getInstance().getAppData().getBudget());

        attractionsByCategory = AttractionRecommender.recommendAttractions(AppManager.getInstance().getAppData().getSearchForm(), AppManager.getInstance().getAppData().getSelectedTrip());

        for(Attraction.Category category : attractionsByCategory.keySet()){
            try {
                Pair<Parent, InterestCategoryPaneController> parentControllerPair = FXMLTool.loadParentController(getClass().getResource(INTEREST_CATEGORY_PANE_URL));
                parentControllerPair.getValue().setCategory(category);
                parentControllerPair.getValue().setAttractionList(attractionsByCategory.get(category));
                parentControllerPair.getValue().setupInterestCategoryPane();
                categoriesContainer.getChildren().add(parentControllerPair.getKey());
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg == AppManager.getInstance().getTripSetupScene()){
            loadSuggestions();
        }
    }

}
