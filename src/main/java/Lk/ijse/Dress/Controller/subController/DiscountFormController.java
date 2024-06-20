package Lk.ijse.Dress.Controller.subController;

import Lk.ijse.Dress.Repository.*;
import Lk.ijse.Dress.DTO.*;
import Lk.ijse.Dress.DTO.tm.Discounttm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DiscountFormController {

    public Label lblPaymentId;
    @FXML
    private JFXButton btmAddNewCustomer;
    @FXML
    private TableColumn<?,? > colAction;

    @FXML
    private TableColumn<?,?> colDiscount;

    @FXML
    private TableColumn<Discounttm,? > colItemCode;

    @FXML
    private TableColumn<Discounttm,?> colItemDiscription;

    @FXML
    private TableColumn<Discounttm,?> colItemQTY;

    @FXML
    private TableColumn<Discounttm,?> colTotal;

    @FXML
    private TableColumn<Discounttm, ?> colUnitprice;
    @FXML
    private TableView<Discounttm> tblDiscountCart;

    @FXML
    private JFXButton btmAddToCard;

    @FXML
    public Label lblTotal;
    public Label lblordertId;
    @FXML
    private Label LblDate;

    @FXML
    private JFXButton btmBack;

    @FXML
    private JFXButton btmOrder;

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbItem;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblDiscription;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TextField txtQty;

    private ObservableList<Discounttm> obList = FXCollections.observableArrayList();

    public void initialize(){
        setDate();
        setCellValueFactory();
        getDiscountCodes();
        getCustomerIds();
        getCurrentDiscountId();
        getCurrentPaymentID();
        //  calculateDiscount();
    }

    private void setDate(){
        LocalDate now = LocalDate.now();
        LblDate.setText(String.valueOf(now));
    }

    private void setCellValueFactory(){
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("Code"));
        colItemDiscription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colItemQTY.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        colUnitprice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("Discount"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btmRemove"));
    }

    private void getDiscountCodes() throws RuntimeException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = DiscountDTO.getCodes();
            obList.addAll(codeList);
            cmbItem.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    private void getCurrentDiscountId(){
        try {
            String currentId= OrdersDTO.getCurrentId();
            String nextOrderId= generateNextOrderId(currentId);
            lblordertId.setText(nextOrderId);
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


    private void getCurrentPaymentID(){
        try {
            String currentId= PaymentRepo.getCurrentId();
            String nextPaymentId= generateNextPaymentId(currentId);
            lblPaymentId.setText(nextPaymentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void calculateNetTotal() {
        double netTotal = 0;
        double discountTotal = 0;

        ObservableList<Discounttm> items = tblDiscountCart.getItems();

        for (int i = 0; i < items.size(); i++) {
            Discounttm item = items.get(i);
            netTotal += item.getTotal();
            discountTotal += item.getDiscount();
        }

        netTotal -= discountTotal;

        lblTotal.setText(String.valueOf(netTotal));
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

    public  static  String paymentid;
    public  static  Date date1;
    public  static  double setAmount;
    public  double getAmount(){

        return setAmount;
    }
    public  String getPaymentId(){
       return  paymentid;

}
    public  Date getDate(){

    return date1;
}
    @FXML
    void BtmOrderOnAction(ActionEvent event) {
        String orderId = lblordertId.getText();
        String cusId = cmbCustomerId.getValue();
        String name = lblCustomerName.getText();
        String id = cmbItem.getValue();
        Date date = Date.valueOf(LocalDate.now());
        String PaymentId = lblPaymentId.getText();
        double total = Double.parseDouble(lblTotal.getText());
        var order = new Order(orderId, date, cusId, PaymentId);
        var payment = new Payment(PaymentId, date, total);

        PaymentDiscount discountPayment = new PaymentDiscount(PaymentId, cusId, name, total, date, id);
        List<Order_discount> odList = new ArrayList<>();

        for (Discounttm tm : tblDiscountCart.getItems()) {
            Order_discount od = new Order_discount(
                    orderId,
                    tm.getCode(),
                    tm.getUnitPrice(),
                    tm.getQty()
            );

            odList.add(od);
        }

        PlaceDiscount po = new PlaceDiscount(order, odList, payment);
        try {
            boolean isPlaced = PlaceOrderDTO.placeOrder(po);
            if (isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
                boolean isDiscount = PaymentRepo.insert3(discountPayment);
                if (isDiscount) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Order Table insert Successfully!").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void cmbCustomerOnAction(ActionEvent event) {
        String id = cmbCustomerId.getValue();
        try {
            Customer customer = CustomerDTO.searchById(id);
            lblCustomerName.setText(customer.getCustomer_name());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) {
        String code = cmbItem.getValue();
        try {
            Discount discount = DiscountDTO.searchById(code);
            if(discount != null) {
                lblDiscription.setText(discount.getDescription());
                lblUnitPrice.setText(String.valueOf(discount.getUnit_price()));
                lblQtyOnHand.setText(String.valueOf(discount.getQty()));
            }

            txtQty.requestFocus();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btmAddToCardOnAction(event);
    }

    public void btmAddToCardOnAction(ActionEvent actionEvent) {
        String code = cmbItem.getValue();
        String description = lblDiscription.getText();
       int qty = Integer.parseInt(txtQty.getText());
        String unitPriceText = lblUnitPrice.getText();
        double unitPrice = 0.0;

        if (!unitPriceText.isEmpty()) {
            unitPrice = Double.parseDouble(unitPriceText);
        }

        double discount = (qty * unitPrice) * 0.3;
        double total = (qty * unitPrice) - discount;
        double setAmount= total;
        JFXButton btnRemove = getJfxButton();

        boolean found = false;
        for (Discounttm tm : obList) {
            if (code.equals(tm.getCode())) {
                qty += tm.getQty();
                discount = (qty * unitPrice) * 0.3;
                total = (qty * unitPrice) - discount;

                tm.setQty(qty);
                tm.setTotal(total);
                tm.setDiscount(discount);

                tblDiscountCart.refresh();
                found = true;
                break;
            }
        }

        if (!found) {
            Discounttm tm = new Discounttm(code, description, qty, unitPrice, discount, total, btnRemove);
            obList.add(tm);
        }

        tblDiscountCart.setItems(obList);
        calculateNetTotal();
        txtQty.setText("");
    }

    private JFXButton getJfxButton() {
        JFXButton btnRemove = new JFXButton("Remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to remove?", yes, no);
            alert.setHeaderText(null);
            alert.setTitle("Confirmation");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == yes) {
                obList.clear();
                tblDiscountCart.refresh();
                calculateNetTotal();
            }
        });
        return btnRemove;
    }

    @FXML
    void btmNewCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/AddNewCustomer_form.fxml"));
        Stage stage = (Stage) btmBack.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Item Form");
        stage.centerOnScreen();
    }

    public void btmBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/dashboard2_form.fxml"));
        Stage stage = (Stage) btmBack.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Item Form");
        stage.centerOnScreen();
    }
}
