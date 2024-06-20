package Lk.ijse.Dress.Controller.subController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class EmployeeChartsFormController {

    @FXML
    private BarChart<String, Number> barchartEmployee;


    public    void initialize() throws SQLException {
        generateAttendanceChart();
        }

    public void generateAttendanceChart() throws SQLException {
        ObservableList<XYChart.Series<String, Number>> data = retrieveAllAttendanceDataFromDatabase();

        barchartEmployee.getData().clear();

        boolean b = barchartEmployee.getData().addAll(data);
    }

    private ObservableList<XYChart.Series<String, Number>> retrieveAllAttendanceDataFromDatabase() throws SQLException {
        ObservableList<XYChart.Series<String, Number>> allData = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/luxora", "root", "p1a2s3i4n5@P");
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT employee_id, attendance_date, attendance_status FROM attendance");

            Map<String, XYChart.Series<String, Number>> seriesMap = new HashMap<>();

            while (resultSet.next()) {
                String employeeId = resultSet.getString("employee_id");
                java.sql.Date date = resultSet.getDate("attendance_date");
                String status = resultSet.getString("attendance_status");

                XYChart.Series<String, Number> series = seriesMap.computeIfAbsent(employeeId, k -> {
                    XYChart.Series<String, Number> newSeries = new XYChart.Series<>();
                    newSeries.setName("Employee " + k);
                    return newSeries;
                });

                series.getData().add(new XYChart.Data<>(date.toString(), 1));
            }

            allData.addAll(seriesMap.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allData;
    }

}
