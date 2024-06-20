package Lk.ijse.Dress.Controller;

import Lk.ijse.Dress.Repository.SupplierDTO;
import Lk.ijse.Dress.DTO.Supplier;
import Lk.ijse.Dress.DTO.tm.Suppliertm;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class SuppliersFormController {

    public JFXButton btmClear;
    public JFXButton btmSearch;
    @FXML
    private JFXButton btmCansel;

    @FXML
    private JFXButton btmCharts;

    @FXML
    private JFXButton btmSave;

    @FXML
    private JFXButton btmUpdate;

    @FXML
    private TableColumn<Suppliertm, ?> colContactNumber;

    @FXML
    private TableColumn<Suppliertm, ?> colSupplierAddress;

    @FXML
    private TableColumn<Suppliertm, ?> colSupplierId;

    @FXML
    private TableColumn<Suppliertm, ?> colSupplierName;

    @FXML
    private Label lblSupplierCount;

    @FXML
    private TableView<Suppliertm> tblSupplier;

    @FXML
    private TextField txtSupplierSearch;
    @FXML
    private TextField txtSupplierAddress;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private TextField txtSupplierName;


    @FXML
    private TextField txtTell;

    public void initialize() throws SQLException {

        setCellValueFactory();
        loadAllSupplier();
        try {
            supplierCount = getSupplierCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setSupplierCount(supplierCount);

    }
    public  void loadAllSupplier() {
        ObservableList<Suppliertm> obList= FXCollections.observableArrayList();
        try {
            List<Supplier> materialModelList = SupplierDTO.getAllSuppliers();
            for (Supplier model : materialModelList) {
                Suppliertm Suppliertm=new Suppliertm(
                        model.getSupplierId(),
                        model.getSupplierName(),
                        model.getSupplierAddress(),
                        model.getContactNumber()
                );
                obList.add(Suppliertm);
            }
            tblSupplier.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            tblSupplier.
                    refresh();
        }


    }

        private void setCellValueFactory() {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("SupplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("SupplierName"));
        colSupplierAddress.setCellValueFactory(new PropertyValueFactory<>("SupplierAddress"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("ContactNumber"));

    }

private  void setSupplierCount(int supplierCount){
    lblSupplierCount.setText(String.valueOf(this.supplierCount));

}

    private  int supplierCount = 0;

    public  int getSupplierCount() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "SELECT COUNT(*) FROM supplier";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                supplierCount = resultSet.getInt(1);
                int setSupplierCount = (supplierCount);
            }
        }

        return supplierCount;
    }
    @FXML
    void btmCanselOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/View/SubView/MaterialSupllier_form.fxml"));
        Parent root=loader.load();
        Scene scene=new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btmChartsOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/View/SubView/SupplierCharts_form.fxml"));
        Parent root=loader.load();
        Scene scene=new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();


    }

    private void clearFields() {
        txtSupplierId.setText("");
        txtSupplierName.setText("");
        txtSupplierAddress.setText("");
        txtTell.setText("");
    }
    @FXML
    void btmSaveOnAction(ActionEvent event) {
        String id = txtSupplierId.getText();
        String name = txtSupplierName.getText();
        String address = txtSupplierAddress.getText();
        int tel = Integer.parseInt(txtTell.getText());

        Supplier supplier = new Supplier(id, name, address, tel);

        try {
            boolean isSaved = SupplierDTO.insert(supplier);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier  saved!").show();

                clearFields();


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML

    void btmUpdateOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/View/SubView/supplierUpdate_form.fxml"));
        Parent root=loader.load();
        Scene scene=new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void btmClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }


}
