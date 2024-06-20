package Lk.ijse.Dress.Controller;

import Lk.ijse.Dress.Controller.subController.PaymentOrderFormController;
import Lk.ijse.Dress.Repository.*;
import Lk.ijse.Dress.DTO.*;
import Lk.ijse.Dress.DTO.tm.MaterialOrderTm;
import Lk.ijse.Dress.DTO.OrderPayment;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static Lk.ijse.Dress.Repository.CustomerDTO.delete;

public class AddNewOrderFormController {

    public ComboBox cmbCustomerId;
    public Label lblCustomerName;
    public Label lblPaymentId;
    public JFXComboBox comboPaymentBox;
    @FXML
    private JFXCheckBox choiceBoxPaid;


    @FXML
    private JFXComboBox<String> cmbMaterialId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblLocalTime;

    @FXML
    private Label lblMaterialName;

    @FXML
    private Label lblMaterialPrice;

    @FXML
    private Label lblMatrialCost;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblTailorFees;

    @FXML
    private Label lblTotal;

    @FXML
    private TableView<MaterialOrderTm> tblMaterials;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtBuslt;


    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtHips;

    @FXML
    private TextField txtInseam;

    @FXML
    private TextField txtNeck;

    @FXML
    private TextField txtNicNumber;

    @FXML
    private TextField txtSholder;

    @FXML
    private TextField txtWalst;

    public void initialize() {
        setDate();
        getCustomerIds();
        getMaterialDs();
        setCellValueFactory();
        getCurrentPaymentID();
        getCurrentOrderId();
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        LocalTime time = LocalTime.now();
        lblLocalTime.setText(String.valueOf(time));
        lblDate.setText(String.valueOf(now));

    }

    private void getCurrentPaymentID() {
        try {
            String currentId = PaymentRepo.getCurrentId();
            String nextPaymentId = generateNextPaymentId(currentId);
            lblPaymentId.setText(nextPaymentId);
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

    private void getCurrentOrderId() {
        try {
            String currentId = OrdersDTO.getCurrentId();
            String nextOrderId = generateNextOrderId(currentId);
            lblOrderId.setText(nextOrderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextOrderId(String currentId) {
        if (currentId != null && currentId.startsWith("ORD")) {
            try {
                int idNum = Integer.parseInt(currentId.substring(3));
                return "ORD" + String.format("%03d", idNum + 1);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return "ORD001";
    }

    private void getCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            var idList = CustomerDTO.getAllCustomerIds();
            obList.addAll(idList);
            cmbCustomerId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getMaterialDs() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = MaterialDTO.getAllMaterialIds();
            obList.addAll(codeList);
            cmbMaterialId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));
    }

    @FXML
    void btmAddOnAction(ActionEvent event) {
        try {
            String orderId = lblOrderId.getText();
            String customerId = cmbCustomerId.getValue().toString();
            String cusname = lblCustomerName.getText();
            LocalDate currentDate = LocalDate.now();
            java.sql.Date orderDate = java.sql.Date.valueOf(currentDate);
            java.sql.Date paymentDate = java.sql.Date.valueOf(currentDate);

            String paymentId = lblPaymentId.getText();
            double amount = Double.parseDouble(lblTotal.getText());

            var order = new Order(orderId, orderDate, customerId, paymentId);
            var payment = new Payment(paymentId, paymentDate, amount);

            OrderPayment orderProcessor = new OrderPayment(order, payment, cusname);

            ;

            List<OrderMaterial> orderMaterialsList = new ArrayList<>();

            for (MaterialOrderTm tm : tblMaterials.getItems()) {
                OrderMaterial orderMaterial = new OrderMaterial(
                        orderId,
                        tm.getId(),
                        tm.getAmount(),
                        tm.getPrice()
                );
                orderMaterialsList.add(orderMaterial);
            }

            PlaceOrder placeOrder = new PlaceOrder(order, orderMaterialsList, payment);

            boolean isPlaced = transactionRepo.placeOrder(placeOrder);

            if (isPlaced) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Order Successfully Placed");
                alert.showAndWait();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/SubView/PayementOrder_form.fxml"));
                Parent root = loader.load();
                PaymentOrderFormController paymentOrderFormController = loader.getController();

                paymentOrderFormController.setOrderDetails(orderProcessor.getOrder(), orderProcessor.getPayment(), orderProcessor.getName());

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Place Order").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid Amount").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "An error occurred").show();
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    @FXML
    void btmCanselOnAction(ActionEvent event) {

    }

    @FXML
    void btmGenarateInvoice(ActionEvent event) {

    }

    private double materialcost;
    ObservableList<MaterialOrderTm> obList = FXCollections.observableArrayList();

    @FXML
    void buttonAddAnotherOnAction(ActionEvent event) {
        String id = cmbMaterialId.getValue();
        String name = lblMaterialName.getText();
        double price = Double.parseDouble(lblMaterialPrice.getText());
        int amount = Integer.parseInt(txtAmount.getText());
        JFXButton btnRemove = getJfxButton();
        materialcost = price * amount;
        lblMaterialPrice.setText(String.valueOf(materialcost));
        boolean found = false;

        for (MaterialOrderTm tm : obList) {
            if (id.equals(tm.getId())) {


                tblMaterials.refresh();
                found = true;
                break;
            }
        }
        if (!found) {
            MaterialOrderTm tm = new MaterialOrderTm(id, name, price, amount, btnRemove);
            obList.add(tm);
        }
        tblMaterials.setItems(obList);
        tblMaterials.refresh();
        lblMatrialCost.setText(String.valueOf(materialcost));


    }

    private double materialCost = 0;

    public void Mashiment() {
        double wast = 250;
        double bust = 100;
        double inseam = 100;
        double hip = 100;
        double neck = 100;
        double shoulder = 200;

        try {
            double waistMeasurement = Double.parseDouble(txtWalst.getText().trim());
            double bustMeasurement = Double.parseDouble(txtBuslt.getText().trim());
            double inseamMeasurement = Double.parseDouble(txtInseam.getText().trim());
            double hipsMeasurement = Double.parseDouble(txtHips.getText().trim());
            double neckMeasurement = Double.parseDouble(txtNeck.getText().trim());
            double shoulderMeasurement = Double.parseDouble(txtSholder.getText().trim());

            double tailorFees = (wast * waistMeasurement) + (bust * bustMeasurement) + (inseam * inseamMeasurement) +
                    (hip * hipsMeasurement) + (neck * neckMeasurement) + (shoulder * shoulderMeasurement);

            lblTailorFees.setText(String.valueOf(tailorFees));
            lblTotal.setText(String.valueOf(tailorFees + materialCost));
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid input. Please enter valid numbers for all measurements.").show();
        }
    }

    private JFXButton getJfxButton() {
        JFXButton btnRemove = new JFXButton("action");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            MaterialOrderTm selectedEmployee = tblMaterials.getSelectionModel().getSelectedItem();
            if (selectedEmployee != null) {
                try {
                    boolean deleted = delete(selectedEmployee.getId());
                    if (deleted) {
                        obList.remove(selectedEmployee);
                        tblMaterials.refresh();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to delete Material");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred while deleting Material.");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a Material to remove.");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        });
        return btnRemove;
    }

    @FXML
    void cmbChooseDesginOnAction(ActionEvent event) {


    }

    @FXML
    public void cmbCustomerIdOnAction(ActionEvent actionEvent) {
        String id = (String) cmbCustomerId.getValue();
        try {
            Customer customer = CustomerDTO.searchById(id);
            lblCustomerName.setText(customer.getCustomer_name());
            customer.getCustomer_contact_Number();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void cmbMaterialIdOnACtion(ActionEvent actionEvent) {
        String id = cmbMaterialId.getValue();
       try{
           MaterialModel materialModel=MaterialDTO.searchById(id);
           lblMaterialName.setText(materialModel.getMaterialName());
           lblMaterialPrice.setText(String.valueOf(materialModel.getPrice()));



       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }

    public void btmGenaraetOnAction(ActionEvent actionEvent) {
        Mashiment();
    }

    public void comboPayemntBoxOnAction(ActionEvent actionEvent) {
    }
}

