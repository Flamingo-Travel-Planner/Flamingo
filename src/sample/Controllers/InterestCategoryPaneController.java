package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import sample.Models.Attraction;
import sample.utils.FXMLTool;

import java.util.List;

public class InterestCategoryPaneController {

    private static final String PLACE_PANE_URL = "../FXML/placePane.fxml";
    @FXML
    private Label categoryLabel;
    @FXML
    private HBox placesContainer;

    private Attraction.Category category;

    private List<Attraction> attractionList;

    public void setCategory(Attraction.Category category) {
        this.category = category;
    }

    public void setAttractionList(List<Attraction> attractionList) {
        this.attractionList = attractionList;
    }

    public void setupInterestCategoryPane(){
        categoryLabel.setText(category.name());

        for(Attraction attraction: attractionList){
            try {
                Pair<Parent, PlacePaneController> parentControllerPair = FXMLTool.loadParentController(getClass().getResource(PLACE_PANE_URL));
                parentControllerPair.getValue().setAttraction(attraction);
                parentControllerPair.getValue().setupPlacePane();
                placesContainer.getChildren().add(parentControllerPair.getKey());
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
