package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.DTO.Rental;
import javafx.scene.control.Alert;
import lombok.SneakyThrows;

import java.sql.*;

public class RentalREPO {
    @SneakyThrows
    public static String getAllRentalIds() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";


        String sql = "SELECT  rental_id FROM rental ORDER BY rental_id DESC LIMIT 1";

        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getString("rental_id");
            } else {
                return null;
            }
        }
    }
    public static boolean insert(Rental rental) {

        String insertQuery = "INSERT INTO rental VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/luxora","root","p1a2s3i4n5@P");
             PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {

            pstmt.setString(1, rental.getRentalId());
            pstmt.setString(2, rental.getCustomeid());
            pstmt.setString(3, rental.getPaymentId());
            pstmt.setDate(4, Date.valueOf(rental.getStDate()));
            pstmt.setDate(5, Date.valueOf(rental.getLastDate()));
            pstmt.setInt(6, rental.getTimeDuration());
            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                System.err.println("Error: Duplicate rental ID");
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

}


