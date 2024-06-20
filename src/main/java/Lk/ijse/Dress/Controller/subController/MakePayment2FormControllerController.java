package Lk.ijse.Dress.Controller.subController;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lombok.SneakyThrows;

public class MakePayment2FormControllerController {

    public AnchorPane payment2Anchor;
    @FXML
    private ImageView colseImage;

    @FXML
    private JFXButton orders;

    @FXML
    private JFXButton rental;
    @FXML
    void onCanselIconCLick(MouseEvent event) {
        Parent parent = payment2Anchor.getParent();
        if (parent instanceof Pane) {
            Pane parentPane = (Pane) parent;
            parentPane.getChildren().remove(payment2Anchor);
        }    }

    @SneakyThrows
    @FXML
    void ordersClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AnchorPaneMoves/makeOrdpayment_form.fxml"));
        Parent root = fxmlLoader.load();
        MakeOrdPaymentFormController makeOrdPaymentFormController=fxmlLoader.getController();
        payment2Anchor.getChildren().clear();
        payment2Anchor.getChildren().add(root);



    }
    @SneakyThrows
    @FXML
    void rentalClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AnchorPaneMoves/makeRenPayment_form.fxml"));
        Parent root = fxmlLoader.load();
        MakeRenPaymentFormController controller=fxmlLoader.getController();
        payment2Anchor.getChildren().clear();
        payment2Anchor.getChildren().add(root);

    }

}
