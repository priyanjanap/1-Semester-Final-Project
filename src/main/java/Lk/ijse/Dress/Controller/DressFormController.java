package Lk.ijse.Dress.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class DressFormController {

    public AnchorPane anchorPaneDress;
    @FXML
    private JFXButton btmAddNewDress;

    @FXML
    private JFXButton btmCharts;

    @FXML
    private JFXButton btmViewDesgin;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDays;

    @FXML
    private TableColumn<?, ?> colDressId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPaymentPerDay;

    @FXML
    private TableColumn<?, ?> colRentalDate;

    @FXML
    private TableColumn<?, ?> colReturnDate;

    @FXML
    private TableView<?> tblViewDesgin;

    @FXML
    void btmAddNewDressOnAction(ActionEvent event) {

    }

    @FXML
    void btmChartsOnAction(ActionEvent event) {

    }

    @FXML
    void btmViewDesginOnAction(ActionEvent event) {

    }

}
