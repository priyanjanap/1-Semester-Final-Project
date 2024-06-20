package Lk.ijse.Dress.Controller;

import Lk.ijse.Dress.Controller.subController.DashboardAnchorPane1FormController;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {
    public JFXButton btmSettingOnAction;
    @FXML
    private AnchorPane subAnchorPane3;

    public Label lblTime;
    public Label lblDate;
    public TextField txtSearchBar;
    public JFXButton btmLogOut2;
    @FXML
    private AnchorPane mainAncPane;
    @FXML
    private JFXButton btmDashboard;
    @FXML
    private JFXButton btmCustomer;


    @FXML
    private JFXButton btmDress;

    @FXML
    private JFXButton btmEmployee;


    @FXML
    private JFXButton btmMaterial;

    @FXML
    private JFXButton btmOrder;

    @FXML
    private Label btmOrders;

    @FXML
    private JFXButton btmPayment;

    @FXML
    private JFXButton btmProduct;

    @FXML
    private JFXButton btmSupplier;


    @FXML
    private JFXButton btmrentals;

    @FXML
    private Label lblDay;

    @FXML
    private AnchorPane mainAnchorePane;
    ReuseMethod reuseMethod = new ReuseMethod();


    @FXML
    void btmDashboardOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dashboard2_form.fxml"));
        Parent root = fxmlLoader.load();
        DashboardPaneFormController controller = fxmlLoader.getController();
        colourRemove();
        mainAnchorePane.getChildren().clear();
        mainAnchorePane.getChildren().add(root);
        reuseMethod.clickChangeButtonBoarderColor(btmDashboard, btmDress, btmCustomer, btmPayment, btmEmployee, btmMaterial, btmOrder, btmProduct, btmrentals, btmSupplier);
    }


    @FXML
    void btmCustomerOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/customers_form.fxml"));
        Parent root = fxmlLoader.load();
        CustomersFormController controller = fxmlLoader.getController();
        colourRemove();

        mainAnchorePane.getChildren().clear();
        mainAnchorePane.getChildren().add(root);
        reuseMethod.clickChangeButtonBoarderColor(btmCustomer, btmDashboard, btmDress, btmPayment, btmEmployee, btmMaterial, btmOrder, btmProduct, btmrentals, btmSupplier);

    }

    @FXML
    void btmDressOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dress_form.fxml"));
        Parent root = fxmlLoader.load();
        DressFormController controller = fxmlLoader.getController();
        colourRemove();

        mainAnchorePane.getChildren().clear();
        mainAnchorePane.getChildren().add(root);


        reuseMethod.clickChangeButtonBoarderColor(btmDress, btmCustomer, btmDashboard, btmPayment, btmEmployee, btmMaterial, btmOrder, btmProduct, btmrentals, btmSupplier);
    }

    @FXML
    void btmEmployeeOnAction(ActionEvent event) throws IOException {
        colourRemove();


        reuseMethod.changeOnlyAnchorPane("/View/employee_form.fxml", mainAnchorePane);
        reuseMethod.clickChangeButtonBoarderColor(btmEmployee, btmCustomer, btmDashboard, btmDress, btmPayment, btmMaterial, btmOrder, btmProduct, btmrentals, btmSupplier);


    }


    @FXML
    void btmMaterialOnAction(ActionEvent event) throws IOException {
        colourRemove();

        reuseMethod.changeOnlyAnchorPane("/View/materials_form.fxml", mainAnchorePane);
        reuseMethod.clickChangeButtonBoarderColor(btmMaterial, btmCustomer, btmDashboard, btmDress, btmPayment, btmEmployee, btmOrder, btmProduct, btmrentals, btmSupplier);
    }

    @FXML
    void btmOrdersOnAction(ActionEvent event) throws IOException {
        colourRemove();

        reuseMethod.changeOnlyAnchorPane("/View/orders_form.fxml", mainAnchorePane);
        reuseMethod.clickChangeButtonBoarderColor(btmOrder, btmCustomer, btmDashboard, btmDress, btmPayment, btmEmployee, btmMaterial, btmProduct, btmrentals, btmSupplier);
    }

    @FXML
    void btmPaymentOnAction(ActionEvent event) throws IOException {
        colourRemove();

        reuseMethod.changeOnlyAnchorPane("/View/payment_form.fxml", mainAnchorePane);
        reuseMethod.clickChangeButtonBoarderColor(btmPayment, btmCustomer, btmDashboard, btmDress, btmEmployee, btmMaterial, btmOrder, btmProduct, btmrentals, btmSupplier);
    }

    @FXML
    void btmProductOnAction(ActionEvent event) throws IOException {
        colourRemove();

        reuseMethod.changeOnlyAnchorPane("/View/products_form.fxml", mainAnchorePane);
        reuseMethod.clickChangeButtonBoarderColor(btmProduct, btmCustomer, btmDashboard, btmDress, btmPayment, btmEmployee, btmMaterial, btmOrder, btmrentals, btmSupplier);
    }

    @FXML
    void btmSupplierOnAction(ActionEvent event) throws IOException {
        colourRemove();

        reuseMethod.changeOnlyAnchorPane("/View/suppliers_form.fxml", mainAnchorePane);
        reuseMethod.clickChangeButtonBoarderColor(btmSupplier, btmCustomer, btmDashboard, btmDress, btmPayment, btmEmployee, btmMaterial, btmOrder, btmProduct, btmrentals);
    }


    @FXML
    void btmrentalsOnAction(ActionEvent event) throws IOException {
        colourRemove();

        reuseMethod.changeOnlyAnchorPane("/View/rentals_form.fxml", mainAnchorePane);
        reuseMethod.clickChangeButtonBoarderColor(btmrentals, btmCustomer, btmDashboard, btmDress, btmPayment, btmEmployee, btmMaterial, btmOrder, btmProduct, btmSupplier);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colourRemove();
        startDateTimeService();
        //setDate();
        try {
            reuseMethod.changeOnlyAnchorPane("/View/dashboard2_form.fxml", mainAnchorePane);
            reuseMethod.clickChangeButtonBoarderColor(btmDashboard, btmCustomer, btmDress, btmPayment, btmEmployee, btmMaterial, btmOrder, btmProduct, btmrentals, btmSupplier);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setLogoutAction();
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDay.setText(String.valueOf(now));
    }

    public void colourRemove() {
        /*
        btmDashboard.setStyle("-fx-background-color:rgba(5, 75, 180, 1) ");
        btmCustomer.setStyle("-fx-background-color:rgba(5, 75, 180, 1) ");
        btmDress.setStyle("-fx-background-color:rgba(5, 75, 180, 1) ");
        btmPayment.setStyle("-fx-background-color:rgba(5, 75, 180, 1) ");
        btmEmployee.setStyle("-fx-background-color:rgba(5, 75, 180, 1) ");
        btmMaterial.setStyle("-fx-background-color:rgba(5, 75, 180, 1) ");
        btmOrder.setStyle("-fx-background-color:rgba(5, 75, 180, 1) ");
        btmProduct.setStyle("-fx-background-color:rgba(5, 75, 180, 1) ");
        btmrentals.setStyle("-fx-background-color:rgba(5, 75, 180, 1) ");
        btmSupplier.setStyle("-fx-background-color:rgba(5, 75, 180, 1) ");
        btmDashboard.setTextFill(Color.rgb(255, 255, 255, 1));
        btmCustomer.setTextFill(Color.rgb(255, 255, 255, 1));
        btmDress.setTextFill(Color.rgb(255, 255, 255, 1));
        btmEmployee.setTextFill(Color.rgb(255, 255, 255, 1));
        btmMaterial.setTextFill(Color.rgb(255, 255, 255, 1));
        btmOrder.setTextFill(Color.rgb(255, 255, 255, 1));
        btmProduct.setTextFill(Color.rgb(255, 255, 255, 1));
        btmrentals.setTextFill(Color.rgb(255, 255, 255, 1));
        btmSupplier.setTextFill(Color.rgb(255, 255, 255, 1));

         */
    }

    private void startDateTimeService() {
        Thread dateTimeThread = new Thread(() -> {
            while (true) {
                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

                Platform.runLater(() -> {
                    lblDate.setText(dateFormatter.format(localDate));
                    lblTime.setText(timeFormatter.format(localTime));
                    // refreshDashboard();
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        dateTimeThread.setDaemon(true);
        dateTimeThread.start();
    }
@SneakyThrows
    public void btmSettingOnAction(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/View/setting.fxml"));
        Parent parent= fxmlLoader.load();
        settingformController settingformController=fxmlLoader.getController();
        mainAnchorePane.getChildren().clear();
        mainAnchorePane.getChildren().add(parent);
    }

    private boolean isLoggedOut = false;

    /*
       public void btmLogOutOnAction(ActionEvent actionEvent) throws IOException {
           if (!isLoggedOut) {
                subAnchorPane3.setDisable(true);
                btmLogOut2.setOnAction(event -> {
                    isLoggedOut = true;
                    subAnchorPane3.setDisable(false);
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AnchorPaneMoves/dashboardAnchorPane1_form.fxml"));
                    Parent parent = null;
                    try {
                        parent = fxmlLoader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    DashboardAnchorPane1FormController dashboardAnchorPane1FormController = fxmlLoader.getController();
                    subAnchorPane3.getChildren().clear();
                    subAnchorPane3.getChildren().add(parent);
                });
            }if (!isLoggedOut) {
               subAnchorPane3.setDisable(true);
           }
        }

             */
    private void setLogoutAction() {
        btmLogOut2.setOnAction(event -> {
            subAnchorPane3.setDisable(false);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AnchorPaneMoves/dashboardAnchorPane1_form.fxml"));
            Parent parent;
            try {
                parent = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            DashboardAnchorPane1FormController dashboardAnchorPane1FormController = fxmlLoader.getController();
            subAnchorPane3.getChildren().clear();
            subAnchorPane3.getChildren().add(parent);
        });
    }

    public void btmLogOutOnAction(ActionEvent actionEvent) {
        if (isLoggedOut) {
            setLogoutAction();
        } else {
            btmLogOut2.setOnAction(null);
        }
    }

}