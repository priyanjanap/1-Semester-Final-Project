package Lk.ijse.Dress.Controller;

import Lk.ijse.Dress.Controller.subController.PayemntSoldTabelFormController;
import Lk.ijse.Dress.Controller.subController.PaymentCostFormController;
import Lk.ijse.Dress.Controller.subController.PaymentOrderTableFromController;
import Lk.ijse.Dress.Controller.subController.PaymentRenatlTabelFromController;
import Lk.ijse.Dress.DTO.Enum.AnchorPaneType;
import Lk.ijse.Dress.DTO.Enum.AnchorPaneType2;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import lombok.SneakyThrows;

public class PaymentFormController {

    @FXML
    private AnchorPane anchorpane2;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colCID;

    @FXML
    private TableColumn<?, ?> colCName;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colPID;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private JFXComboBox<AnchorPaneType2> comboOrderRental;

    @FXML
    private JFXComboBox<AnchorPaneType> comboPaymentOrCost;

    @FXML
    private Label lblCost;

    @FXML
    private Label lblInCome;

    @FXML
    private Label lblProfit;

    @FXML
    private TableView<?> tblPayment;
    @FXML
    void initialize() {
        ObservableList<AnchorPaneType> options = FXCollections.observableArrayList(AnchorPaneType.values());
        comboPaymentOrCost.setItems(options);
        ObservableList<AnchorPaneType2> options2=FXCollections.observableArrayList(AnchorPaneType2.values());
        comboOrderRental.setItems(options2);

    }
@SneakyThrows
    @FXML
    void comboOrderRentalOnAction(ActionEvent event) {
        ComboBox<AnchorPaneType2> comboBox = (ComboBox<AnchorPaneType2>) event.getSource();
        AnchorPaneType2 selectedType = comboBox.getValue();
        switch (selectedType) {
            case Rental:
                FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/View/SubView/paymentRenatlTabel_from.fxml"));
                Parent parent=fxmlLoader.load();
                PaymentRenatlTabelFromController paymentRenatlTabelFromControllerr=fxmlLoader.getController();
                anchorpane2.getChildren().clear();;
                anchorpane2.getChildren().add(parent);

                break;
            case Orders:
                FXMLLoader fxmlLoader2=new FXMLLoader(getClass().getResource("/View/SubView/paymentOrderTable_from.fxml"));
                Parent rootnode=fxmlLoader2.load();
                PaymentOrderTableFromController paymentOrderTableFromController1=fxmlLoader2.getController();
                anchorpane2.getChildren().clear();;
                anchorpane2.getChildren().add(rootnode);
                break;
            case sold:
                FXMLLoader fxmlLoader3=new FXMLLoader(getClass().getResource("/View/SubView/payemntSoldTabel_form.fxml"));
                Parent parent2=fxmlLoader3.load();
                PayemntSoldTabelFormController payemntSoldTabelFormController=fxmlLoader3.getController();
                anchorpane2.getChildren().clear();;
                anchorpane2.getChildren().add(parent2);
                break;
            default:
                break;
        }

    }
    @SneakyThrows
    @FXML
    void comboPaymentOrCostOnAction(ActionEvent event) {
        ComboBox<AnchorPaneType> comboBox = (ComboBox<AnchorPaneType>) event.getSource();
        AnchorPaneType selectedType = comboBox.getValue();
        switch (selectedType) {
            case Income:

                break;
            case Cost:
                FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/View/SubView/paymentCostTable_form.fxml"));
                Parent parent=fxmlLoader.load();
                PaymentCostFormController paymentCostFormController=fxmlLoader.getController();
                anchorpane2.getChildren().clear();;
                anchorpane2.getChildren().add(parent);
                break;

            default:
                break;
        }

    }

}
