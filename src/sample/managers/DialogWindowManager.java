package sample.managers;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogWindowManager {

    private static JFXAlert<Void> alertBox;

    private static Stage stage;

    public static void showAlert(Stage stage, String heading, String body) {

        JFXDialogLayout layout = new JFXDialogLayout();

        Label headingLabel = new Label(heading);
        Label bodyLabel = new Label(body);

        layout.setHeading(headingLabel);
        layout.setBody(bodyLabel);

        DialogWindowManager.stage = stage;

        buildAlertBox();

        alertBox.setContent(layout);
        alertBox.showAndWait();
    }

    private static void buildAlertBox() {
        alertBox = new JFXAlert<>(stage);
        alertBox.setOverlayClose(true);
        alertBox.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        alertBox.initModality(Modality.NONE);
    }
}
