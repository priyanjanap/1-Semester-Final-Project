package Lk.ijse.Dress.Controller.subController;

import Lk.ijse.Dress.Controller.SupplierPaymentFormController;
import Lk.ijse.Dress.Controller.subController.MakePayment2FormControllerController;
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
import lombok.SneakyThrows;

public class MakePaymentFormController {
    public AnchorPane paymentAnchor;
    @FXML
    private ImageView colseImage;

    @FXML
    private JFXButton btmRevenue;

    @FXML
    private JFXButton expense;
    @FXML
    void onCanselIconCLick(MouseEvent event) {
        Parent parent = paymentAnchor.getParent();
        if (parent instanceof Pane) {
            Pane parentPane = (Pane) parent;
            parentPane.getChildren().remove(paymentAnchor);
        }
    }
    @SneakyThrows
    @FXML
    void expenseClick(ActionEvent event) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/supplierpayment_form.fxml"));
            Parent root = fxmlLoader.load();
            SupplierPaymentFormController supplierPaymentFormController = fxmlLoader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();


    }
    @SneakyThrows
    @FXML
    void revenueClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AnchorPaneMoves/makePayment2_form.fxml"));
        Parent root = fxmlLoader.load();
        MakePayment2FormControllerController controller = fxmlLoader.getController();
        paymentAnchor.getChildren().clear();
        paymentAnchor.getChildren().add(root);

    }

}
