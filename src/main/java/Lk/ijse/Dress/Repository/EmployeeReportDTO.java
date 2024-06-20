package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.DTO.QrCodes;
import Lk.ijse.Dress.QRController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeReportDTO {
    public static ResultSet getAllValuesByNic(QrCodes nic) {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sqlQuery = "SELECT * FROM qr_codes WHERE nic_number = ?";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sqlQuery);

            String nicNumber = nic.getNic();
            statement.setString(1, nicNumber);

            return statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static List<QrCodes> getAllEmployees() throws SQLException {
        List<QrCodes> qrCodes = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "SELECT * FROM qr_codes";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
               int id=resultSet.getInt("id");
               Byte image=resultSet.getByte("qr_code_data");
               String nic=resultSet.getString("nic_number");
               String empid=resultSet.getString("empId");

                QrCodes qrCodes1 = new QrCodes(id,image,nic,empid);
                qrCodes.add(qrCodes1);

            }
        }

        return qrCodes;
    }
    public static void displayQR2() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/luxora", "root", "p1a2s3i4n5@P");
            PreparedStatement statement = connection.prepareStatement("SELECT qr_code_data FROM qr_codes WHERE empId = ?");
            statement.setInt(1, 1);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                byte[] imageData = resultSet.getBytes("qr_code_data");
                Image qrImage = new Image(new ByteArrayInputStream(imageData));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("QR Code");
                alert.setHeaderText(null);

                ImageView imageView = new ImageView(qrImage);
                imageView.setFitWidth(500);
                imageView.setFitHeight(500);
                alert.getDialogPane().setContent(imageView);

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(QRController.class.getResource("alret.css").toExternalForm());

                alert.getButtonTypes().setAll(ButtonType.OK);

                alert.showAndWait();
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String getEmpIdByNIC(String nicNumber) {
        String empId = null;

        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String query = "SELECT empId FROM qr_codes WHERE nic_number = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nicNumber);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                empId = rs.getString("empId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empId;
    }


}


