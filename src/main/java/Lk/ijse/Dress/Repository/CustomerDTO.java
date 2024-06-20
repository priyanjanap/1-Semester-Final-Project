package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.DTO.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDTO {
    public static boolean save(Customer customer) throws SQLException {

        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "INSERT INTO customer VALUES(?, ?, ?, ?,?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setObject(1, customer.getCustomer_Id());
            pstm.setObject(2, customer.getCustomer_name());
            pstm.setObject(3, customer.getCustomer_Address());
            pstm.setObject(4, customer.getCustomer_contact_Number());
            pstm.setObject(5, customer.getEmail());
            return pstm.executeUpdate() > 0;
        }
    }
    public static Customer searchById(String customerId) throws SQLException {

        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "SELECT * FROM customer WHERE Customer_Id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, customerId);

            try (ResultSet resultSet = pstm.executeQuery()) {
                if (resultSet.next()) {
                    String cusId = resultSet.getString(1);
                    String name = resultSet.getString(2);
                    String address = resultSet.getString(3);
                    int tel = resultSet.getInt(4);
                    String email = resultSet.getString(5);

                    return new Customer(cusId, name, address, tel, email);
                }
            }
        }
        return null;
    }
    public static boolean update(Customer customer) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "UPDATE customer SET Customer_name = ?, Customer_Address = ?, Customer_contact_number = ?, email = ? WHERE Customer_Id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, customer.getCustomer_name());
            pstm.setString(2, customer.getCustomer_Address());
            pstm.setInt(3, customer.getCustomer_contact_Number());
            pstm.setString(4, customer.getEmail());
            pstm.setString(5, customer.getCustomer_Id());

            return pstm.executeUpdate() > 0;
        }
    }
    public static boolean delete(String customerId) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "DELETE FROM customer WHERE Customer_Id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, customerId);

            return pstm.executeUpdate() > 0;
        }
    }
    public static List<Customer> getAllCustomers() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "SELECT * FROM customer";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            List<Customer> cusList = new ArrayList<>();
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                int tel = resultSet.getInt(4);
                String email = resultSet.getString(5);

                cusList.add(new Customer(id, name, address, tel, email));
            }
            return cusList;
        }
    }
    public static List<String> getAllCustomerIds() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "SELECT Customer_Id FROM customer";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            List<String> idList = new ArrayList<>();
            while (resultSet.next()) {
                idList.add(resultSet.getString(1));
            }
            return idList;
        }
    }

}
