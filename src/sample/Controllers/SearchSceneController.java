package sample.Controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import sample.Models.DestinationRecommender;
import sample.Models.SearchForm;
import sample.managers.AppManager;
import sample.managers.DialogWindowManager;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchSceneController implements Initializable {

    @FXML
    private JFXTextField budgetField, originField;
    @FXML
    private JFXDatePicker departureDatePicker, returnDatePicker;
    @FXML
    private JFXCheckBox museumsCheckBox, nightlifeCheckBox, religionCheckBox, entertainmentCheckBox, outdoorCheckBox, shoppingCheckBox;
    @FXML
    private Parent categoriesContainer;

    private SearchForm searchForm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchForm = new SearchForm();

        for (Node categoryHBox: categoriesContainer.getChildrenUnmodifiable()) {
            for(Node category: ((Parent)categoryHBox).getChildrenUnmodifiable()) {
                ((JFXCheckBox) category).setSelected(true);
            }
        }

    }

    public void search(){

        try{
           searchForm.setBudget(Integer.parseInt(budgetField.getText()));
        }catch (Exception e){
            DialogWindowManager.showAlert(AppManager.getInstance().getCurrentStage(), "Error!", "One or more fields contain invalid data. Please re-enter.");
            return;
        }

        searchForm.setOrigin(originField.getText());

        searchForm.setDepartureDate(departureDatePicker.getValue());
        searchForm.setReturnDate(returnDatePicker.getValue());

        searchForm.setMuseums(museumsCheckBox.isSelected());
        searchForm.setNightlife(nightlifeCheckBox.isSelected());
        searchForm.setReligion(religionCheckBox.isSelected());
        searchForm.setEntertainment(entertainmentCheckBox.isSelected());
        searchForm.setOutdoor(outdoorCheckBox.isSelected());
        searchForm.setShopping(shoppingCheckBox.isSelected());

        int categoriesSelected = 0;

        for (Node categoryHBox: categoriesContainer.getChildrenUnmodifiable()) {
            for(Node category: ((Parent)categoryHBox).getChildrenUnmodifiable()) {
                if(((JFXCheckBox)category).isSelected()) categoriesSelected++;
            }
        }

        if(categoriesSelected == 0) {
            DialogWindowManager.showAlert(AppManager.getInstance().getCurrentStage(), "Error!", "Please select at least one interest category");
            return;
        }

        AppManager.getInstance().getAppData().setRecommendationsList(DestinationRecommender.recommendDestinations(searchForm));
        AppManager.getInstance().getAppData().setBudget(searchForm.getBudget());

        AppManager.getInstance().showWorkStage();
    }
}
