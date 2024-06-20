package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.Controller.subController.DiscountFormController;
import Lk.ijse.Dress.DTO.*;
import Lk.ijse.Dress.DTO.Enum.PaymentType;
import Lk.ijse.Dress.db.DbConnection;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepo {
    static DiscountFormController discountFormController= new DiscountFormController();
    public static String getCurrentId() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "SELECT Payment_Id FROM payment ORDER BY Payment_Id DESC LIMIT 1";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {
            if (resultSet.next()) {
                String Payment_Id = resultSet.getString(1);
                return Payment_Id;
            }
            return null;
        }

    }
    public static boolean save(List<Payment> odList) throws SQLException {
        for (Payment od : odList) {
            boolean isSaved = save(od);
            if(!isSaved) {
                return false;
            }
        }
        return true;
    }



    @SneakyThrows
    public static boolean save(Payment od) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "INSERT INTO payment VALUES(?, ?, ?)";
        Connection connection = DriverManager.getConnection(url,username,password);
        PreparedStatement pstm = connection.prepareStatement(sql);


        pstm.setString(1, od.getPaymentId());
        pstm.setDate(2, ((Date) od.getDate()));
        pstm.setDouble(3,  od.getAmount());



        return pstm.executeUpdate() > 0;
    }
    public static double amount;


    public static boolean insert(PaymentOrder paymentOrder) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "INSERT INTO PaymentOrder (payid, cusid, name, total, nic, paymentType, amount) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, paymentOrder.getPayid());
            statement.setString(2, paymentOrder.getCusid());
            statement.setString(3, paymentOrder.getName());
            statement.setDouble(4, paymentOrder.getTotal());
            statement.setString(5, paymentOrder.getNic());
            statement.setString(6, String.valueOf(paymentOrder.getPaymentType()));
            statement.setDouble(7, paymentOrder.getAmount());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @SneakyThrows
    public static boolean insert3(PaymentDiscount paymentOrder) throws SQLException {


        String sql = "INSERT INTO paymentdiscount (payid, cusid, name, total, Date,item_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, paymentOrder.getPayid());
            statement.setString(2, paymentOrder.getCusid());
            statement.setString(3, paymentOrder.getName());
            statement.setDouble(4, paymentOrder.getTotal());
            statement.setString(5, String.valueOf(paymentOrder.getDate()));
            statement.setString(6,paymentOrder.getId());




            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
    @SneakyThrows
    public static List<PaymentOrder> getPaymentOrdersByNic(String nic) throws SQLException {
        String sql = "SELECT * FROM PaymentOrder WHERE nic = ?";
        List<PaymentOrder> paymentOrders = new ArrayList<>();

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nic);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    PaymentOrder paymentOrder = new PaymentOrder();
                    paymentOrder.setPayid(resultSet.getString("payid"));
                    paymentOrder.setCusid(resultSet.getString("cusid"));
                    paymentOrder.setName(resultSet.getString("name"));
                    paymentOrder.setTotal(resultSet.getDouble("total"));
                    paymentOrder.setNic(resultSet.getString("nic"));
                    paymentOrder.setPaymentType(PaymentType.valueOf(resultSet.getString("paymentType")));
                    paymentOrder.setAmount(resultSet.getDouble("amount"));
                    paymentOrders.add(paymentOrder);
                }
            }
        }
        return paymentOrders;
    }
    @SneakyThrows
    public static boolean updatePaymentOrder(String nic, double amount, PaymentType paymentType) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "UPDATE PaymentOrder SET amount = ?, paymentType = ? WHERE nic = ?";

        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, amount);
            statement.setString(2, paymentType.name());
            statement.setString(3, nic);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }

}
