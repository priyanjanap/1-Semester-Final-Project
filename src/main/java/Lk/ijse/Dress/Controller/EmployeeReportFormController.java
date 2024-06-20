package Lk.ijse.Dress.Controller;

import Lk.ijse.Dress.Controller.subController.EmployeeNicFormController;
import Lk.ijse.Dress.Repository.EmployeeDTO;
import Lk.ijse.Dress.DTO.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

import static Lk.ijse.Dress.Repository.EmployeeReportDTO.getEmpIdByNIC;

public class EmployeeReportFormController {

    @FXML
    private ImageView ImageEmployeeOR;

    @FXML
    private ImageView ImageEmployeephoto;

    @FXML
    private BarChart<String,Number> attendanceChart;

    @FXML
    private Label lblAge;

    @FXML
    private Label lblDob;

    @FXML
    private Label lblEmployeeId;

    @FXML
    private Label lblEmployeeNme;

    @FXML
    private Label lblEmpoyeeAddress;

    @FXML
    private Label lblJobRoll;

    @FXML
    private Label lblNic;

    @FXML
    private Label lblPhoneNumber;

    @FXML
    private Label lblSalary;

    @FXML
    void initialize() throws SQLException {
        loadEmployeeDetails();
        getQrCodes();
        loadEmployeeImage(empId,ImageEmployeephoto);
        loadQrImage(nic,ImageEmployeeOR);
        generateAttendanceChart();
    }
  static EmployeeNicFormController employeeNicFormController=new EmployeeNicFormController();


public  String id;
public  String empId;
private String nic;
    public void loadEmployeeDetails() throws SQLException {
        String id = employeeNicFormController.getId();
        System.out.println(id);
        String nicNumber = id;
        String empId = getEmpIdByNIC(nicNumber);
        if (empId != null) {
            System.out.println("Employee ID: " + empId);
            try {
             Employee employee = EmployeeDTO.getEmployeeById(empId);
                if (employee != null) {
                    lblEmployeeId.setText(employee.getEmployeeId());
                    lblEmployeeNme.setText(employee.getEmployeeName());
                    lblEmpoyeeAddress.setText(employee.getEmployeeAddress());
                    lblAge.setText(String.valueOf(employee.getEmployeeAge()));
                    lblDob.setText(String.valueOf(employee.getDateOfBirth()));
                    lblPhoneNumber.setText(String.valueOf(employee.getContactNumber()));
                    lblSalary.setText(String.valueOf(employee.getSalary()));
                } else {
                    System.out.println("Employee not found for NIC: " + nicNumber);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public  void getQrCodes(){
        String nic=employeeNicFormController.getId();
        lblNic.setText(nic);
        String job=employeeNicFormController.getJobRoll();
        lblJobRoll.setText(job);
    }





    public void loadEmployeeImage(String empId, ImageView ImageEmployeephoto) {
        String id2 = employeeNicFormController.getId();
        String empId1 = getEmpIdByNIC(id2);

        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "SELECT image_data FROM employeeimages WHERE empId = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, empId1);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Blob blob = resultSet.getBlob("image_data");
                if (blob != null) {
                    try (InputStream inputStream = blob.getBinaryStream()) {
                        BufferedImage bufferedImage = ImageIO.read(inputStream);

                        if (bufferedImage != null) {
                            int width = bufferedImage.getWidth();
                            int height = bufferedImage.getHeight();

                            WritableImage fxImage = new WritableImage(width, height);
                            PixelWriter pixelWriter = fxImage.getPixelWriter();

                            for (int y = 0; y < height; y++) {
                                for (int x = 0; x < width; x++) {
                                    int argb = bufferedImage.getRGB(x, y);
                                    pixelWriter.setArgb(x, y, argb);
                                }
                            }

                            ImageEmployeephoto.setImage(fxImage);
                        }
                    }
                } else {
                    System.out.println("No image found for employee ID: " + empId1);
                }
            } else {
                System.out.println("Employee ID not found: " + empId1);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }
    public void loadQrImage(String nic, ImageView ImageEmployeeOR) {
        String id2 = employeeNicFormController.getId();

        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "SELECT qr_code_data FROM qr_codes WHERE nic_number = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id2);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Blob blob = resultSet.getBlob("qr_code_data");
                if (blob != null) {
                    try (InputStream inputStream = blob.getBinaryStream()) {
                        BufferedImage bufferedImage = ImageIO.read(inputStream);

                        if (bufferedImage != null) {
                            int width = bufferedImage.getWidth();
                            int height = bufferedImage.getHeight();

                            WritableImage fxImage = new WritableImage(width, height);
                            PixelWriter pixelWriter = fxImage.getPixelWriter();

                            for (int y = 0; y < height; y++) {
                                for (int x = 0; x < width; x++) {
                                    int argb = bufferedImage.getRGB(x, y);
                                    pixelWriter.setArgb(x, y, argb);
                                }
                            }

                            ImageEmployeeOR.setImage(fxImage);
                        }
                    }
                } else {
                    System.out.println("No image found for employee ID: " + id2);
                }
            } else {
                System.out.println("Employee ID not found: " + id2);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }

    public void generateAttendanceChart() throws SQLException {
        String id2 = employeeNicFormController.getId();
        String empId1 = getEmpIdByNIC(id2);

        ObservableList<XYChart.Series<String, Number>> data = retrieveAllAttendanceDataFromDatabase(empId1);

        attendanceChart.getData().clear();
        attendanceChart.getData().addAll(data);
    }

    private ObservableList<XYChart.Series<String, Number>> retrieveAllAttendanceDataFromDatabase(String empId) throws SQLException {
        ObservableList<XYChart.Series<String, Number>> allData = FXCollections.observableArrayList();

        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "SELECT employee_id, DATE_FORMAT(attendance_date, '%Y-%m-%d') AS month, COUNT(*) AS present_count FROM attendance WHERE employee_id = ? AND attendance_status = 'Present'  GROUP BY employee_id, DATE_FORMAT(attendance_date, '%Y-%m-%d') ORDER BY month";


        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, empId);

            ResultSet resultSet = preparedStatement.executeQuery();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Employee " + empId);
            System.out.println(empId);

            while (resultSet.next()) {
                String month = resultSet.getString("month");
                int presentCount = resultSet.getInt("present_count");
                System.out.println(month + ": " + presentCount);
                series.getData().add(new XYChart.Data<>(month, presentCount));
            }

            allData.add(series);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allData;
    }
}