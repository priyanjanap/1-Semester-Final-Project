package Lk.ijse.Dress.Controller;

import Lk.ijse.Dress.Controller.subController.AddNewEmployeeFormController;
import Lk.ijse.Dress.Repository.EmployeeDTO;
import Lk.ijse.Dress.DTO.Employee;
import Lk.ijse.Dress.DTO.tm.Employeetm;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static Lk.ijse.Dress.Repository.CustomerDTO.delete;

public class EmployeeFormController implements Initializable {
    @FXML
    private Pane Pane2;

    public JFXButton btmAttendance;
    @FXML
    private TableColumn<Employeetm, String> ColEmployeeId;

    @FXML
    private JFXButton btmAddEmployee;

    @FXML
    private JFXButton btmCharts;

    @FXML
    private TableColumn<Employeetm, JFXButton> colAction;

    @FXML
    private TableColumn<Employeetm, String> colAdress;

    @FXML
    private TableColumn<Employeetm, Integer> colConnumber;

    @FXML
    private TableColumn<Employeetm, String> colDateOfBiarth;

    @FXML
    private TableColumn<Employeetm, JFXButton> colEdit;

    @FXML
    private TableColumn<Employeetm, Integer> colEmployeeAge;

    @FXML
    private TableColumn<Employeetm, String> colEmployeeName;

    @FXML
    private TableColumn<Employeetm, String> colGender;

    @FXML
    private TableColumn<Employeetm, Double> colSalary;


    @FXML
    private Label lblEmployeeCount;

    @FXML
    private Label lblSalaryCount;

    @FXML
    private TableView<Employeetm> tblEmployeeCart;
    private  int EmployeeCount;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactory();
       loadAllEmployee();
        try {
           EmployeeCount  = getEmployeeCount();
           totalSalary=getTotal();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        setEmployeeCount(EmployeeCount);
        setTotal(totalSalary);
    }

    private void setCellValueFactory() {
        ColEmployeeId.setCellValueFactory(new PropertyValueFactory<>("EmployeeId"));

        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("EmployeeName"));
        colAdress.setCellValueFactory(new PropertyValueFactory<>("EmployeeAddress"));
        colEmployeeAge.setCellValueFactory(new PropertyValueFactory<>("EmployeeAge"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("Salary"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        colDateOfBiarth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        colConnumber.setCellValueFactory(new PropertyValueFactory<>("ContactNumber"));
        colEdit.setCellValueFactory(new PropertyValueFactory<>("Edit"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));



    }

    ObservableList<Employeetm> obList = FXCollections.observableArrayList();

    private void loadAllEmployee() {
        try {
            List<Employee> employeeList = EmployeeDTO.LoadAllEmployee2();
            for (Employee employee : employeeList) {
                Employeetm employeetm = new Employeetm(
                        employee.getEmployeeId(),
                        employee.getEmployeeName(),
                        employee.getEmployeeAddress(),
                        employee.getEmployeeAge(),
                        employee.getSalary(),
                        employee.getGender(),
                        employee.getDateOfBirth(),
                        employee.getContactNumber(),
                        getJfxButton2(),
                        getJfxButton()
                );
                obList.add(employeetm);
            }
            tblEmployeeCart.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private JFXButton getJfxButton2() {

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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/SubView/employeeUpdate_form.fxml"));
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
            Employeetm selectedEmployee = tblEmployeeCart.getSelectionModel().getSelectedItem();
            if (selectedEmployee != null) {
                try {
                    boolean deleted = delete(selectedEmployee.getEmployeeId());
                    if (deleted) {
                        obList.remove(selectedEmployee);
                        tblEmployeeCart.refresh();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to delete Employee");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred while deleting Employee.");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a Employee to remove.");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        });
        return btnRemove;
    }


    private void setEmployeeCount(int EmployeeCount){
        lblEmployeeCount.setText(String.valueOf(EmployeeCount));
    }
private  double totalSalary;
    private void setTotal(double totalsalary){
        lblSalaryCount.setText(String.valueOf(totalsalary));
    }

    private double getTotal() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "SELECT SUM(salary) AS totalsalary FROM employee";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

        if (resultSet.next()) {
            totalSalary=resultSet.getDouble(1);
            //double totalSalary = resultSet.getDouble("totalsalary");
            System.out.println("Total Salary: " + totalSalary);
        }


        }return totalSalary;
    }
    private int getEmployeeCount() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "SELECT COUNT(*) AS EmployeeCount FROM employee";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("EmployeeCount");
            }
        }
        return 0;
    }

    @FXML
    void btmAddEmployeeOnAction(ActionEvent event) throws IOException {
       FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/View/SubView/addNewEmployee_form.fxml"));
       Parent root=fxmlLoader.load();
        AddNewEmployeeFormController controller=fxmlLoader.getController();
        Pane2.getChildren().clear();
        Pane2.getChildren().add(root);
        

    }

    @FXML
    void btmChartsonAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/SubView/employeeCharts_form.fxml")));
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();    }

    public void btmAtendnceOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/SubView/atendance_form.fxml")));
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private JFXButton btmQr;
    @FXML
    void btmQrOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/SubView/Qr.fxml")));
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    }

