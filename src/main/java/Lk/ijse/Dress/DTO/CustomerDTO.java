package Lk.ijse.Dress.DTO;

import Lk.ijse.Dress.Model.Customer;
import Lk.ijse.Dress.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDTO {
    public static boolean save(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer VALUES(?, ?, ?, ?,?)";

        Connection connection = null;
        connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, customer.getCustomer_Id());
        pstm.setObject(2, customer.getCustomer_name());
        pstm.setObject(3, customer.getCustomer_Address());
        pstm.setObject(4, customer.getCustomer_contact_Number());
        pstm.setObject(5,customer.getEmail());
        return pstm.executeUpdate() > 0;
    }

    public static Customer searchById(String Customer_Id) throws SQLException {
        String sql = "SELECT * FROM customer WHERE Customer_Id = ?";

        Connection connection = null;
        connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, Customer_Id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String cus_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            int tel = Integer.parseInt(resultSet.getString(4));
            String email=resultSet.getString(5);

            Customer customer = new Customer(cus_id, name, address,tel,email);

            return customer;
        }

        return null;
    }

    public static boolean update(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET Customer_name = ?, Customer_Address = ?, Customer_contact_number = ? ,email=? WHERE Customer_Id = ?";

        Connection connection = null;
        connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, customer.getCustomer_name());
        pstm.setObject(2, customer.getCustomer_Address());
        pstm.setObject(3, customer.getCustomer_contact_Number());
        pstm.setObject(4, customer.getCustomer_Id());
    pstm.setObject(5,customer.getEmail());
        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String Customer_Id) throws SQLException {
        String sql = "DELETE FROM customer WHERE Customer_Id = ?";

        Connection connection = null;
        connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, Customer_Id );

        return pstm.executeUpdate() > 0;
    }

    public static List<Customer> getAll() throws SQLException {
        String sql = "SELECT * FROM customer";

        PreparedStatement pstm = null;
        pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Customer> cusList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            int tel = Integer.parseInt(resultSet.getString(4));
            String email= resultSet.getString(5);
            Customer customer = new Customer(id, name, address, tel,email);
            cusList.add(customer);
        }
        return cusList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT  Customer_Id FROM customer";
        PreparedStatement
        pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }
}
