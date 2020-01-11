package sample.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Pair;

import java.net.URL;

public class FXMLTool {

    public static Scene createScene(URL location, int[] sceneDimensions) throws Exception{
        Parent parent = loadParent(location);

        return new Scene(parent, sceneDimensions[0], sceneDimensions[1]);
    }

    public static Parent loadParent(URL location) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(location);

        return fxmlLoader.load();
    }

    public static <Controller> Pair<Parent, Controller> loadParentController(URL location) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent parent = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();

        return new Pair<>(parent,controller);
    }

}
