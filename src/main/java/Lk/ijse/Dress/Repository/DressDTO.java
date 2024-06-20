package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.DTO.Dress;
import Lk.ijse.Dress.DTO.RentalDress;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DressDTO {
    public static List<String> getAllDressIds() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "SELECT Dress_Id FROM dress";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            List<String> idList = new ArrayList<>();
            while (resultSet.next()) {
                idList.add(resultSet.getString("Dress_Id"));
            }
            return idList;
        }
    }
    public static List<Dress> getAllDress(String id) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "SELECT * FROM dress";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            List<Dress> rentalList = new ArrayList<>();
            while (resultSet.next()) {
                id = resultSet.getString("Dress_Id");
                String name = resultSet.getString("desginName");
                int days = resultSet.getInt("days");
                double pricePerDate = resultSet.getDouble("Peymant_per_date");
                Date startDate = resultSet.getDate("Rental_date");
                Date lastDate = resultSet.getDate("ReturnDate");

                Dress rental = new Dress(id, name, days, pricePerDate, startDate, lastDate);
                rentalList.add(rental);
            }
            return rentalList;
        }
    }
    public static boolean insertDress(Dress rental) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "INSERT INTO  dress(Dress_Id, desginName, Design, days, Peymant_per_date, Rental_date, ReturnDate) " +
                "VALUES (?, ?, ?, ?, ?, ?, )";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, rental.getId());
            pstmt.setString(2, rental.getName());
            pstmt.setInt(3, rental.getDays());
            pstmt.setDouble(4, rental.getPircePerDate());
            pstmt.setDate(5, new java.sql.Date(rental.getStdate().getTime()));
            pstmt.setDate(6, new java.sql.Date(rental.getLastDate().getTime()));

            pstmt.executeUpdate();
        }
        return false;
    }
    public static Dress getDressById(String dressId) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "SELECT Dress_Id, desginName, days, Peymant_per_date, Rental_date, ReturnDate " +
                "FROM dress " +
                "WHERE Dress_Id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, dressId);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String id = resultSet.getString("Dress_Id");
                String name = resultSet.getString("desginName");
                int days = resultSet.getInt("days");
                double pricePerDate = resultSet.getDouble("Peymant_per_date");
                Date startDate = resultSet.getDate("Rental_date");
                Date lastDate = resultSet.getDate("ReturnDate");

                return new Dress(id, name, days, pricePerDate, startDate, lastDate);
            }
        }
        return null;
    }
    public static boolean update(List<RentalDress> odList) throws SQLException {
        for (RentalDress od : odList) {
            boolean isUpdateQty = updateDress(od.getDressId(), od.getStdate(),od.getLastDate() );
            if(!isUpdateQty) {
                return false;
            }
        }
        return true;
    }
    public static boolean updateDress(String Dress_Id, LocalDate rentalDate, LocalDate returnDate) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "UPDATE dress SET  Rental_date = ?, ReturnDate = ? WHERE Dress_Id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(rentalDate));
            pstmt.setDate(2, Date.valueOf(returnDate));
            pstmt.setString(3, Dress_Id);

            pstmt.executeUpdate();
        }
        return false;
    }

}




