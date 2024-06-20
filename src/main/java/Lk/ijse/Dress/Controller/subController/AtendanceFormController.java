package Lk.ijse.Dress.Controller.subController;

import Lk.ijse.Dress.Repository.EmployeeAttDTO;
import Lk.ijse.Dress.DTO.EmployeeAtt;
import Lk.ijse.Dress.DTO.Enum.PunStatus;
import Lk.ijse.Dress.DTO.Enum.Status;
import Lk.ijse.Dress.DTO.tm.EmployeeAtandancetm;
import Lk.ijse.Dress.QRcode;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class AtendanceFormController  {

    public TextField txtEmployeeName;
    @FXML
    private DatePicker AttendanceDate;

    @FXML
    private TableColumn<?, ?> ColEmployeeId;

    @FXML
    private ChoiceBox<String> StatsuChoiceBox;

    @FXML
    private JFXButton btmCansel;

    @FXML
    private JFXButton btmScanner;

    @FXML
    private JFXButton btmUpdate;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colPunStatus;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private ChoiceBox<String> punStatusChoiceBox;

    @FXML
    private TableView<EmployeeAtandancetm> tblEmployeeAttendance;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private ImageView imageView;

    private QRcode qrCode;



    @FXML
    void initialize() {
        qrCode = new QRcode();


        StatsuChoiceBox.getItems().addAll("Present", "Absent", "Late");
        punStatusChoiceBox.getItems().addAll("ON_TIME", "Late");


        StatsuChoiceBox.getSelectionModel().selectFirst();
        punStatusChoiceBox.getSelectionModel().selectFirst();
        setCellValueFactory();
    }
    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColEmployeeId.setCellValueFactory(new PropertyValueFactory<>("id2"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("datePicker"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("statusChoiceBox"));
        colPunStatus.setCellValueFactory(new PropertyValueFactory<>("punStatusChoiceBox"));
    }
    ObservableList<EmployeeAtandancetm> oblist= FXCollections.observableArrayList();

    public  void loadEmployeesAttendance(){
        String id = txtEmployeeId.getText();
        LocalDate date = AttendanceDate.getValue();
        Status status = Status.valueOf(StatsuChoiceBox.getValue());
        PunStatus punStatus = PunStatus.valueOf(punStatusChoiceBox.getValue());
        EmployeeAtt employeeAtandancetm = new EmployeeAtt(id, id, date, status, punStatus);
        try {
            boolean isInserted = EmployeeAttDTO.insert(employeeAtandancetm);
            if (isInserted) {
                 new Alert(Alert.AlertType.INFORMATION,"Data Save Successfully in Database ");

                tblEmployeeAttendance.setItems(oblist);
                tblEmployeeAttendance.refresh();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }finally {
        }

    }
    @FXML
    void btmUpdateOnAction(ActionEvent event) {

        loadEmployeesAttendance();

    }
    @FXML
    void btmCanseOnAction(ActionEvent event) {
        QRScanner.closeWebcamPanel();
    }

    private LocalTime startTime;

    @FXML
    void btmScannerOnAction(ActionEvent actionEvent) {
        QRScanner scanner = new QRScanner();
        startTime=LocalTime.now();

        scanner.scanQRCode(new QRScanner.QRScannerCallback() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    String[] parts = result.split(",");
                    if (parts.length >= 2) {
                        txtEmployeeId.setText(parts[0]);
                        txtEmployeeName.setText(parts[1]);
                    }
                    calculateScanTime(startTime);
                    QRScanner.closeWebcamPanel();
                }
            }
        });
    }
    private void showColoredAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.initStyle(StageStyle.TRANSPARENT);

        alert.getDialogPane().setStyle("-fx-text-fill: green; -fx-font-family: 'Arial';");



        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }
    private void showColoredAlert2(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.initStyle(StageStyle.TRANSPARENT);

        alert.getDialogPane().setStyle("-fx-text-fill: red; -fx-font-family: 'Arial';");



        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }
    private void showColoredAlert3(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.initStyle(StageStyle.TRANSPARENT);

        alert.getDialogPane().setStyle("-fx-text-fill: blue; -fx-font-family: 'Arial';");


        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }
private  LocalTime scanTime;
    private void calculateScanTime(LocalTime startTime) {
        scanTime = LocalTime.of(8, 0);

        if (scanTime.isBefore(startTime)) {
            Platform.runLater(() -> showColoredAlert(Alert.AlertType.CONFIRMATION, "Employee arrived early!"));
        } else if (scanTime.equals(startTime)) {
            Platform.runLater(() -> showColoredAlert3(Alert.AlertType.CONFIRMATION, "It's exactly 8 a.m. now!"));
        } else if (scanTime.isAfter(startTime)) {
            Platform.runLater(() -> showColoredAlert2(Alert.AlertType.ERROR, "You are late"));
        }
    }


}
