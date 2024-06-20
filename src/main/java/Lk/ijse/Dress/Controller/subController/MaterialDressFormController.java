package Lk.ijse.Dress.Controller.subController;

import Lk.ijse.Dress.Repository.MaterialDressDto;
import Lk.ijse.Dress.DTO.MaterialDress;
import Lk.ijse.Dress.DTO.tm.MaterialDresstm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class MaterialDressFormController {

    @FXML
    private TableColumn<?, ?> colId1;

    @FXML
    private TableColumn<?, ?> colId2;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableView<MaterialDresstm> tblMaDress;
    private MaterialDresstm MaterialDresstm;

    public void initialize() {
        setCellValueFactory();
        loadAllMatDress();
    }
    private  void setCellValueFactory(){
        colId1.setCellValueFactory(new PropertyValueFactory<>("id1"));
        colId2.setCellValueFactory(new PropertyValueFactory<>("id2"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
public  void loadAllMatDress(){
    ObservableList<MaterialDresstm> oblist= FXCollections.observableArrayList();
    try {
        List<MaterialDress> materialModelList = MaterialDressDto.loadAllMaterial();
        for (MaterialDress model : materialModelList) {
            MaterialDresstm materialtm=new MaterialDresstm(
                    model.getId1(),
                    model.getId2(),
                    model.getQty(),
                    model.getPrice(),
                    model.getTotal()
            );
            oblist.add(materialtm);
        }
        tblMaDress.setItems(oblist);

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }


}
}
