package Lk.ijse.Dress.Controller.subController;

import Lk.ijse.Dress.DTO.PaymentDiscount;
import Lk.ijse.Dress.DTO.tm.PaymentDiscountTm;
import Lk.ijse.Dress.Repository.OrderDiscountDTO;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.List;

import static Lk.ijse.Dress.Repository.CustomerDTO.delete;

public class PayemntSoldTabelFormController {

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
    private TableView<PaymentDiscountTm> tblPayment;
    public  void initialize() {
        setCellValueFactory();
        loadAllOrders();
    }
    private void setCellValueFactory() {
        colPID.setCellValueFactory(new PropertyValueFactory<>("id1"));
        colCID.setCellValueFactory(new PropertyValueFactory<>("id2"));
        colCName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("total"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colType.setCellValueFactory(new PropertyValueFactory<>("dressId"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));
    }
    ObservableList<PaymentDiscountTm> obList= FXCollections.observableArrayList();
    public void loadAllOrders() {
        try {
            List<PaymentDiscount> paymentOrders = OrderDiscountDTO.getAllPaymentDiscounts();
            for (PaymentDiscount od : paymentOrders) {
                PaymentDiscountTm tm = new PaymentDiscountTm(
                        od.getPayid(),
                        od.getCusid(),
                        od.getName(),
                        od.getTotal(),
                        od.getDate(),
                        od.getId(),
                        getJfxButton()

                );
                obList.add(tm);
            }
            tblPayment.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private JFXButton getJfxButton() {
        JFXButton btnRemove = new JFXButton("action");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            PaymentDiscountTm selectedCustomer = (PaymentDiscountTm) tblPayment.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                try {
                    boolean deleted = delete(selectedCustomer.getId1());
                    if (deleted) {
                        obList.remove(selectedCustomer);
                        tblPayment.refresh();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to delete id.");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred while deleting id.");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a id to remove.");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        });
        return btnRemove;
    }

}
