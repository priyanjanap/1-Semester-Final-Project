package Lk.ijse.Dress.Controller;

import Lk.ijse.Dress.DTO.Supplier;
import Lk.ijse.Dress.DTO.SupplierPayment;
import Lk.ijse.Dress.Repository.PaymentRepo;
import Lk.ijse.Dress.Repository.SupplierDTO;
import Lk.ijse.Dress.Repository.SupplierPaymentRepo;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class SupplierPaymentFormController {

    @FXML
    private DatePicker Datepicker;

    @FXML
    private JFXComboBox<String> comboID;

    @FXML
    private Label lblPayid;

    @FXML
    private Label lblname;

    @FXML
    private TextField txtAmount;

    public void initialize() {
        getCurrentPaymentID();
        getCustomerIds();
        supplierName();
        comboID.setOnAction(event -> supplierName());
    }

    private void getCurrentPaymentID() {
        try {
            String currentId = PaymentRepo.getCurrentId();
            String nextPaymentId = generateNextPaymentId(currentId);
            lblPayid.setText(nextPaymentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextPaymentId(String currentId) {
        if (currentId != null && currentId.startsWith("PAY")) {
            try {
                int idNum = Integer.parseInt(currentId.substring(3));
                return "PAY" + String.format("%03d", idNum + 1);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return "PAY001";
    }

    private void payment() {


    }

    private void getCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            var idList = SupplierDTO.getAllSupplierIds();
            obList.addAll(idList);
            comboID.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void supplierName() {
        String id = comboID.getValue();

        if (id == null || id.isEmpty()) {
            lblname.setText("No supplier selected");
            return;
        }

        try {
            Supplier supplier = SupplierDTO.getById(id);

            if (supplier != null) {
                lblname.setText(supplier.getSupplierName());
            } else {
                lblname.setText("Supplier not found");
            }
        } catch (SQLException e) {
            lblname.setText("Error retrieving supplier");
            e.printStackTrace();
        }
    }

    @FXML
    void canselAction(ActionEvent event) {

    }

    @FXML
    void comboIDOnAction(ActionEvent event) {

    }

    @FXML
    void saveAction(ActionEvent event) {
        String supplierId = comboID.getValue();
        if (supplierId == null || supplierId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select a supplier.").show();
            return;
        }

        String supplierName = lblname.getText();
        if (supplierName.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Supplier name is empty.").show();
            return;
        }

        String payId = lblPayid.getText();
        if (payId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Payment ID is empty.").show();
            return;
        }

        LocalDate paymentDate = Datepicker.getValue();
        if (paymentDate == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a payment date.").show();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(txtAmount.getText());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid amount format.").show();
            return;
        }

        SupplierPayment supplierPayment = new SupplierPayment(payId, supplierId, supplierName, amount, Date.valueOf(paymentDate));

        try {
            boolean isInserted = SupplierPaymentRepo.insertSupplierPayment(supplierPayment);
            if (isInserted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment inserted successfully.").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Failed to insert payment.").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while inserting payment: " + e.getMessage()).show();
        }
    }


}
