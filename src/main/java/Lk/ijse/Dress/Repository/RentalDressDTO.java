package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.DTO.RentalDress;

import java.sql.*;
import java.util.List;

public class RentalDressDTO {
    public static boolean save(List<RentalDress> odList) throws SQLException {
        for (RentalDress od : odList) {
            boolean isSaved = save( od);
            if(!isSaved) {
                return false;
            }
        }
        return true;
    }
    public static boolean save(RentalDress od) throws SQLException {

        String sql = "INSERT INTO rentaldress (rentalID, DressId, price_per_day, stdate, lastDate) " +
                "VALUES (?, ?, ?, ?, ?)";
        Connection connection = null;
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/luxora","root","p1a2s3i4n5@P");
        PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, od.getRentalID());
            statement.setString(2,od.getDressId());
            statement.setDouble(3, od.getPrice_per_day());
            statement.setDate(4, Date.valueOf(od.getStdate()));
            statement.setDate(5, Date.valueOf(od.getLastDate()));

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

    }

}
