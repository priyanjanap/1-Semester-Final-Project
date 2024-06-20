package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.DTO.Order;
import lombok.SneakyThrows;

import java.sql.*;

public class OrdersDTO {

    public static String getCurrentId() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "SELECT Order_Id FROM orders ORDER BY Order_Id DESC LIMIT 1";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
        }
    }
    @SneakyThrows
    public static boolean save(Order order) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "INSERT INTO orders VALUES(?, ?, ?,?)";
        Connection connection= DriverManager.getConnection(url,username,password);
        PreparedStatement pstm=connection
        .prepareStatement(sql);

        pstm.setString(1, order.getOrderId());
        pstm.setDate(2,  order.getDate());
        pstm.setString(3, order.getCusId());
        pstm.setString(4, order.getPaymentId());


        return pstm.executeUpdate() > 0;
    }
}
