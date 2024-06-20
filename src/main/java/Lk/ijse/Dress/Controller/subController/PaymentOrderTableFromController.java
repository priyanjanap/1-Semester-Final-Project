package Lk.ijse.Dress.Controller.subController;

import Lk.ijse.Dress.DTO.PaymentOrder;
import Lk.ijse.Dress.DTO.tm.PaymentOrderTm;
import Lk.ijse.Dress.Repository.PlaceOrderDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.util.List;

public class PaymentOrderTableFromController {

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
    private TableView<PaymentOrderTm> tblPayment;

    public  void initialize() {
        setCellValueFactory();
        loadAllOrders();
    }
    private void setCellValueFactory() {
        colPID.setCellValueFactory(new PropertyValueFactory<>("payid"));
        colCID.setCellValueFactory(new PropertyValueFactory<>("cusid"));
        colCName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("total"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }
    ObservableList<PaymentOrderTm> obList= FXCollections.observableArrayList();
    public void loadAllOrders() {
        try {
            List<PaymentOrder> paymentOrders = PlaceOrderDTO.getAllPaymentOrders();
            for (PaymentOrder od : paymentOrders) {
                PaymentOrderTm tm = new PaymentOrderTm(
                        od.getPayid(),
                        od.getCusid(),
                        od.getName(),
                        od.getTotal(),
                        od.getNic(),
                        od.getPaymentType(),
                        od.getAmount()
                );
                obList.add(tm);
            }
            tblPayment.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    }
