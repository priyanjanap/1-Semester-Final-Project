package Lk.ijse.Dress.Controller.subController;

import Lk.ijse.Dress.Repository.MaterialSupplierModelDTO;
import Lk.ijse.Dress.DTO.MaterialSupplierModel;
import Lk.ijse.Dress.DTO.tm.MaterialSupplierTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class MaterialSupllierFormController {

    @FXML
    private TableColumn<?, ?> colMaterialId;

    @FXML
    private TableColumn<?, ?> colMaterialname;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupllierId;

    @FXML
    private TableColumn<?, ?> colSupplierName;

    @FXML
    private TableView<MaterialSupplierTm> tblMaterialDress;
    public void initialize() {
        setCellValueFactory();
        loadAllMaterial();
    }
    private  void setCellValueFactory(){
        colSupllierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colMaterialId.setCellValueFactory(new PropertyValueFactory<>("materialId"));
        colMaterialname.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supllierName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    public  void loadAllMaterial(){
        ObservableList<MaterialSupplierTm> oblist= FXCollections.observableArrayList();

        try {
            String id = null;
            List<MaterialSupplierModel> materialModelList = MaterialSupplierModelDTO.loadAllMaterialSuppliers();
            for (MaterialSupplierModel model : materialModelList) {
                MaterialSupplierTm materialtm=new MaterialSupplierTm(
                        model.getSupplierId(),
                        model.getMaterialId(),
                        model.getMaterialName(),
                        model.getSupllierName(),
                        model.getQty(),
                        model.getPrice()
                );
                oblist.add(materialtm);
            }
            tblMaterialDress.setItems(oblist);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



}
