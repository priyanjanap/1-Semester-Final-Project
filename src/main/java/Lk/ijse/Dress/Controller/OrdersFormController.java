package Lk.ijse.Dress.Controller;

import Lk.ijse.Dress.DTO.OrderTable;
import Lk.ijse.Dress.DTO.tm.OrdersTm;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.List;

import static Lk.ijse.Dress.Repository.CustomerDTO.delete;

public class OrdersFormController {

    @FXML
    private JFXButton btmNewOrder;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCusId;

    @FXML
    private TableColumn<?, ?> colCusName;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colFinished;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colRetunDate;

    @FXML
    private TableColumn<?, ?> colViewDitals;

    @FXML
    private TableColumn<?, ?> colhandOver;

    @FXML
    private Label lblOrderCount;

    @FXML
    private TableView<OrdersTm> tblOrder;

    @FXML
    void btmNewOrderOnAction(ActionEvent event) {

    }

    public void initialize() {
        loadRentalAndOrdersData();
        setCellValueFactory();
        loadAllSupplier();
        loadCounts();
    }

    private void setCellValueFactory() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("id1"));
        colCusId.setCellValueFactory(new PropertyValueFactory<>("id2"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colRetunDate.setCellValueFactory(new PropertyValueFactory<>("number"));
        colViewDitals.setCellValueFactory(new PropertyValueFactory<>("handover"));
        colFinished.setCellValueFactory(new PropertyValueFactory<>("finish"));
        colhandOver.setCellValueFactory(new PropertyValueFactory<>("handover"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));
    }


    @SneakyThrows
    public static ObservableList<OrderTable> loadRentalAndOrdersData() {
        ObservableList<OrderTable>OrdersList = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/luxora", "root", "p1a2s3i4n5@P")) {
            String joinQuery = "SELECT o.Order_Id, o.order_date, c.Customer_Id, c.Customer_name, c.Customer_contact_number " +
                    "FROM orders o JOIN customer c ON o.Customer_Id = c.Customer_Id";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(joinQuery)) {

                while (resultSet.next()) {
                    String orderId = resultSet.getString("Order_Id");
                    Date orderDate = resultSet.getDate("order_date");
                    String cusId = resultSet.getString("Customer_Id");
                    String cusName = resultSet.getString("Customer_name");
                    int cusContactNumber = resultSet.getInt("Customer_contact_number");

                    OrderTable order = new OrderTable(orderId, orderDate, cusId, cusName, cusContactNumber);
                    OrdersList.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return OrdersList;
    }

    ObservableList<OrdersTm> obList = FXCollections.observableArrayList();

    @SneakyThrows
    public void loadAllSupplier() {

        try {
            List<OrderTable> materialModelList = loadRentalAndOrdersData();
            for (OrderTable model : materialModelList) {
                OrdersTm Suppliertm = new OrdersTm(
                        model.getOrderId(),
                        model.getCusid(),
                        model.getCusname(),
                        model.getDate(),
                        model.getNumber(),
                        seView(),
                        seTfinished(),
                        SETHANDOVER(),
                        getJfxButton()
                );

                obList.add(Suppliertm);
            }
            tblOrder.setItems(obList);
        } finally {
            tblOrder.refresh();
        }
    }

    private String SETHANDOVER() {
        return "handover";
    }

    private String seView() {
        return "Women";
    }

    private String seTfinished() {
        return "finished";
    }

    {
    }

    private JFXButton getJfxButton() {
        JFXButton btnRemove = new JFXButton("action");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            OrdersTm selectedCustomer = tblOrder.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                try {
                    boolean deleted = delete(selectedCustomer.getId1());
                    if (deleted) {
                        obList.remove(selectedCustomer);
                        tblOrder.refresh();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to delete customer.");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred while deleting customer.");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a customer to remove.");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        });
        return btnRemove;
    }
    private void setOrderCount(int orderCount) {
        lblOrderCount.setText(String.valueOf(orderCount));
    }

    private int getOrderCount() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "SELECT COUNT(*) AS order_count FROM orders";
        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("order_count");
            }
        }
        return 0;
    }
    private void loadCounts() {
        try {
            setOrderCount(getOrderCount());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load counts: " + e.getMessage()).show();
        }
    }
}