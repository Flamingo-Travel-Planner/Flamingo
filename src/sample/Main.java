package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        AppManager.getInstance().setupStages(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
