package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.DTO.Order_discount;
import Lk.ijse.Dress.DTO.PaymentDiscount;
import Lk.ijse.Dress.db.DbConnection;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDiscountDTO {


    public static boolean save(List<Order_discount> odList) throws SQLException {
        for (Order_discount od : odList) {
            boolean isSaved = save( od);
            if(!isSaved) {
                return false;
            }
        }
        return true;
    }@SneakyThrows
    private static boolean save(Order_discount od) throws SQLException {
        String sql = "INSERT INTO order_discount VALUES(?, ?, ?, ?)";


        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, od.getOrderId());
        pstm.setString(2, od.getItem_id());
        pstm.setDouble(3, od.getUnit_price());
        pstm.setInt(4, od.getQty());


        return pstm.executeUpdate() > 0;
    }@SneakyThrows
    public static List<PaymentDiscount> getAllPaymentDiscounts() throws SQLException {
        String sql = "SELECT * FROM paymentdiscount";
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        List<PaymentDiscount> paymentDiscounts = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                PaymentDiscount paymentDiscount = new PaymentDiscount(
                        resultSet.getString("payid"),
                        resultSet.getString("cusid"),
                        resultSet.getString("name"),
                        resultSet.getDouble("total"),
                        resultSet.getDate("date"),
                        resultSet.getString("item_id")
                );
                paymentDiscounts.add(paymentDiscount);
            }
        }
        return paymentDiscounts;
    }
}
