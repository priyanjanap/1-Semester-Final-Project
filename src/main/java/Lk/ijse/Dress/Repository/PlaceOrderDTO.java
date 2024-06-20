package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.DTO.Enum.PaymentType;
import Lk.ijse.Dress.DTO.PaymentOrder;
import Lk.ijse.Dress.DTO.PlaceDiscount;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderDTO {
    public static boolean placeOrder(PlaceDiscount po) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            connection.setAutoCommit(false);

            boolean isOrderSaved = OrdersDTO.save(po.getOrder());
            boolean isQtyUpdated = DiscountDTO.update(po.getOdList());
            boolean isOrderDetailSaved = OrderDiscountDTO.save(po.getOdList());
            boolean isPaymentSaved = PaymentRepo.save(po.getPayment());

            if (isOrderSaved && isQtyUpdated && isOrderDetailSaved && isPaymentSaved) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }@SneakyThrows
    public static List<PaymentOrder> getAllPaymentOrders() throws SQLException {
        String sql = "SELECT * FROM PaymentOrder";
        List<PaymentOrder> paymentOrders = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

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

        return paymentOrders;
    }

}
