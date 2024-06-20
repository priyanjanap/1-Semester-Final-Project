package Lk.ijse.Dress.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ReuseMethod {

    public void changeOnlyAnchorPane(String fxml, AnchorPane mainAnchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        AnchorPane anchorPane = fxmlLoader.load();
        mainAnchorPane.getChildren().clear();
        mainAnchorPane.getChildren().add(anchorPane);
    }

    public void clickChangeButtonBoarderColor(JFXButton jfxButton, JFXButton... buttons) {
        jfxButton.setStyle("-fx-background-color: rgba(203, 231, 235, 1); -fx-border-color: transparent;");

        for (JFXButton button : buttons) {
            button.setStyle("-fx-border-color: transparent;");
            button.setTextFill(Color.rgb(5, 75, 180));
        }
    }
}
