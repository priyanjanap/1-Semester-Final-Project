package Lk.ijse.Dress.Controller;

import Lk.ijse.Dress.Controller.subController.ProductUpdateFormController;
import Lk.ijse.Dress.Repository.ProductDTO;
import Lk.ijse.Dress.DTO.Product;
import Lk.ijse.Dress.DTO.tm.Producttm;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ProductFormController {

    public JFXButton btmUpdate;
    public Pane Pane1;
    public AnchorPane anchorPaneMain;
    @FXML
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPRice;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableColumn<?, ?> cplQty;

    @FXML
    private Label lblProductCount;

    @FXML
    private TableView<Producttm> tableProduct;
    public void initialize() throws SQLException {
        setCellValueFactory();
        loadAllProduct();
    }
    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cplQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPRice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colType.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("type"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));

    }
    public  void loadAllProduct() {
        ObservableList<Producttm> obList= FXCollections.observableArrayList();
        try {
            List<Product> productList = ProductDTO.getAllProduct();
            for (Product model : productList) {
                Producttm producttm=new Producttm(
                model.getId(),
                model.getName(),
                model.getQty(),
                model.getPrice(),
                model.getDescription(),
                model.getType(),
                model.getCategory()
                );
                obList.add(producttm);
            }
            tableProduct.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


        @FXML
    void btmAddNewProductOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/View/AddProduct_form.fxml"));
        Parent root=loader.load();
        Scene scene=new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void btmChartsOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/View/SubView/productCharts_form.fxml"));
        Parent root=loader.load();
        Scene scene=new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.show();

    }

    public void btmUpdateOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/View/SubView/ProductUpdate_form.fxml"));
        Parent root=loader.load();
        ProductUpdateFormController productUpdateFormController=loader.getController();
        anchorPaneMain.getChildren().clear();;
        anchorPaneMain.getChildren().add(root);

    }
}
