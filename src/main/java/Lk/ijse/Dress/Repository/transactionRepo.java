package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.DTO.PaymentRental;
import Lk.ijse.Dress.DTO.PlaceOrder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class transactionRepo {

    /* public static boolean placeOrder(PlaceOrder po) throws SQLException {

         String url = "jdbc:mysql://localhost:3306/luxora";
         String username = "root";
         String password = "p1a2s3i4n5@P";
         Connection connection = DriverManager.getConnection(url,username,password);
         connection.setAutoCommit(false);

         try {
             boolean isOrderSaved = OrdersDTO.save(po.getOrder());
             System.out.println("00");
             if (isOrderSaved) {
                 System.out.println("1");
                 boolean isQtyUpdated = MaterialDTO.update(po.getOdlist());
                 if (isQtyUpdated) {
                     System.out.println("12");
                     boolean isOrderDetailSaved = OrderMaterialDTO.save(po.getOdlist());
                     if (isOrderDetailSaved) {
                         System.out.println("123");
                         boolean ispayemntSaved=PaymentDTO.save(po.getPayment());
                         if (ispayemntSaved) {
                             System.out.println("1234");
                             connection.commit();
                             return true;
                         }

                     }
                 }
             }
             connection.rollback();
             return false;
         } catch (Exception e) {
             connection.rollback();
             return false;
         } finally {
             connection.setAutoCommit(true);
         }
     }*/
    public static boolean placeOrder(PlaceOrder po) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            connection.setAutoCommit(false);

            boolean isOrderSaved = OrdersDTO.save(po.getOrder());
            boolean isQtyUpdated = MaterialDTO.update(po.getOdlist());
            boolean isOrderDetailSaved = OrderMaterialDTO.save(po.getOdlist());
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
    }

    public static boolean save1(List<PaymentRental> odList) throws SQLException {
        for (PaymentRental od : odList) {
            boolean isSaved = save1(od);
            if (!isSaved) {
                return false;
            }
        }
        return true;
    }

    public static boolean save1(PaymentRental od) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "INSERT INTO PaymentOrder (payid, cusid, name, total, nic, paymentType, amount) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, od.getPayid());
            statement.setString(2, od.getCusid());
            statement.setString(3, od.getCusname());
            statement.setDouble(4, od.getTotal());
            statement.setString(5, od.getNic());
            statement.setString(6, String.valueOf(od.getPaymentType()));
            statement.setDouble(7, od.getAmount());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }

}
