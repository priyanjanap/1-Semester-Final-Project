package Lk.ijse.Dress.Controller.subController;

import Lk.ijse.Dress.Controller.DashboardFormController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class DashboardAnchorPane1FormController {

    public ImageView colseImage;
    public JFXButton btmLogOUt;
    public JFXButton btmaddAccount;
    @FXML
    private AnchorPane sideAnchorPane4;

    DashboardFormController dashboardFormController= new DashboardFormController();


    @FXML
    void btmAddToAnotherAccountOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AnchorPaneMoves/adminVerifaction_form.fxml")));
        Scene scene = new Scene(rootNode);
        Stage newStage = new Stage();
        newStage.setScene(scene);

        Stage currentStage = (Stage) sideAnchorPane4.getScene().getWindow();

        newStage.initStyle(StageStyle.UNDECORATED);

        newStage.setX(1000);
        newStage.setY(50);

        newStage.show();

        sideAnchorPane4.setVisible(false);
    }


    @FXML
    void btmLoGOut(ActionEvent event) throws IOException {

       // AnchorPane btmLogout=dashboardFormController.mainAncPane;
        Parent rootNode = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/Login_form.fxml")));
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        Stage stage1 = (Stage) btmLogOUt.getScene().getWindow();
        stage1.close();
       // Stage stage2 = (Stage) btmLogout .getScene().getWindow();
       // stage2.close();

    }

    public void onCanselIconCLick(MouseEvent mouseEvent) {
        Parent parent = sideAnchorPane4.getParent();
        if (parent instanceof Pane) {
            Pane parentPane = (Pane) parent;
            parentPane.getChildren().remove(sideAnchorPane4);
        }
    }
}
