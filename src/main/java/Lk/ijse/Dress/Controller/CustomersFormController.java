package Lk.ijse.Dress.Controller;

import Lk.ijse.Dress.Repository.CustomerDTO;
import Lk.ijse.Dress.DTO.Customer;
import Lk.ijse.Dress.DTO.tm.Customertm;
import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Optional;

import static Lk.ijse.Dress.Repository.CustomerDTO.delete;

public class CustomersFormController {

    public JFXButton btmCharts;

    public AnchorPane anchorpane;

    @FXML
    private JFXButton btmAddnewCustomer;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContactNumber;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colEdit;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private Label lblCustomerCount;

    @FXML
    private TableView<Customertm> tblCustomer;
    public void initialize(){
        setCellValueFactory();
        loadAllCustomer();
        try {
            CustomerCount = getCustomerCount();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setCustomerCount(CustomerCount);
    }

    private void setCellValueFactory() {

        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("CustomerAddress"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("tell"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEdit.setCellValueFactory(new PropertyValueFactory<>("edit"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }
    private int CustomerCount;
    private void setCustomerCount(int CustomerCount){
        lblCustomerCount.setText(String.valueOf(CustomerCount));

    }
    private int getCustomerCount() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "SELECT COUNT(*) FROM customer";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                CustomerCount = resultSet.getInt(1);
                int setSupplierCount = (CustomerCount);
            }
        }
        return CustomerCount;
    }
    ObservableList<Customertm> oblist= FXCollections.observableArrayList();

    public  void loadAllCustomer(){
        try {
            List<Customer> customerList = CustomerDTO.getAllCustomers();
            for (Customer model : customerList) {
                Customertm customertm=new Customertm(
                        model.getCustomer_Id(),
                        model.getCustomer_name(),
                        model.getCustomer_Address(),
                        model.getCustomer_contact_Number(),
                        model.getEmail(),
                        getJfxbutton2(),
                        getJfxButton()

                );
                oblist.add(customertm);
            }
            tblCustomer.setItems(oblist);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    private  JFXButton getJfxbutton2(){

        JFXButton Edit = new JFXButton("Edit");
        Edit.setCursor(Cursor.HAND);

        Edit.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Do you want to edit this record?");
            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(yesButton, noButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == yesButton) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/SubView/updateCustomer_form.fxml"));
                try {
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return Edit;
    }
    private JFXButton getJfxButton() {
        JFXButton btnRemove = new JFXButton("btnRemove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            Customertm selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                try {
                    boolean deleted = delete(selectedCustomer.getCustomerId());
                    if (deleted) {
                        oblist.remove(selectedCustomer);
                        tblCustomer.refresh();
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

    @FXML
    void btmAddnewCustomerOnAction(ActionEvent event) throws IOException {
        Timeline timeline = getTimeline();
        timeline.play();
    }
    private Timeline getTimeline() {
        Duration duration = Duration.seconds(3);
        Timeline timeline = new Timeline(new KeyFrame(duration, event -> {
            FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("/View/AddNewCustomer_form.fxml"));
            Parent parent = null;
            try {
                parent = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            AddNewCustomerFormController controller=fxmlLoader.getController();
            anchorpane.getChildren().clear();
            anchorpane.getChildren().add(parent);




        }, new KeyValue[]{}));
        return timeline;
    }



    public void btmChartsOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/View/SubView/CustomerCharts_form.fxml"));
        Parent root= loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
