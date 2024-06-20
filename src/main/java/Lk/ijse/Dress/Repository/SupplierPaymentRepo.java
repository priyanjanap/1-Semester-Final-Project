package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.DTO.SupplierPayment;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierPaymentRepo {

  @SneakyThrows
    public static boolean insertSupplierPayment(SupplierPayment supplierPayment) throws SQLException {
      String url = "jdbc:mysql://localhost:3306/luxora";
      String username = "root";
      String password = "p1a2s3i4n5@P";

      String sql = "INSERT INTO supplierpayment (PayID, Supplier_Id,supplier_name,amount,date) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, supplierPayment.getId1());
            statement.setString(2, supplierPayment.getId2());
            statement.setString(3, supplierPayment.getName());
            statement.setDouble(4, supplierPayment.getAmount());
            statement.setDate(5, Date.valueOf(String.valueOf(supplierPayment.getDate())));

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
    public static List<SupplierPayment> getAllSupplierPayments() throws SQLException {
        List<SupplierPayment> supplierPayments = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "SELECT * FROM supplierpayment";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String payId = resultSet.getString("PayID");
                String supplierId = resultSet.getString("Supplier_Id");
                String supplierName = resultSet.getString("supplier_name");
                double amount = resultSet.getDouble("amount");
                Date date = resultSet.getDate("date");

                SupplierPayment supplierPayment = new SupplierPayment(payId, supplierId, supplierName, amount, date);
                supplierPayments.add(supplierPayment);
            }
        }

        return supplierPayments;
    }

}