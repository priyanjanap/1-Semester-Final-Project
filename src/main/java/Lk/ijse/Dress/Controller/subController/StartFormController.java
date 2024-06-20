package Lk.ijse.Dress.Controller.subController;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class StartFormController implements Initializable {

    @FXML
    private AnchorPane MainAnchorPane;

    @FXML
    private StackPane Stackpane;

    @FXML
    private JFXButton btmStart;

    @FXML
    void btmStartOnAction(ActionEvent event) throws IOException {
        Parent rootNode= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Login_form.fxml")));
        Scene scene=btmStart.getScene();
        rootNode.translateXProperty().set(scene.getWidth());
        Stackpane.getChildren().add(rootNode);
        Timeline timeline=new Timeline();
        KeyValue keyValue=new KeyValue(rootNode.translateXProperty(),0, Interpolator.EASE_IN);
        KeyFrame keyFrame= new KeyFrame(Duration.seconds(1),keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(event1 -> {
            Stackpane.getChildren().removeAll(MainAnchorPane);
        });
    timeline.play();

    }
    private void playAudio() {
     /*   String audioFilePath = "C:\\Users\\PRIYANJANA\\Downloads\\1715538400276znq26pl8-voicemaker.in-speech.mp3";
        Media media = new Media(new File(audioFilePath).toURI().toString());

        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mediaPlayer.play();*/
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
playAudio();
    }
}
