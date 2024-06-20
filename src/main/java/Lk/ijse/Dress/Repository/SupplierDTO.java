package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.DTO.Supplier;
import Lk.ijse.Dress.db.DBconnection1;
import Lk.ijse.Dress.db.DbConnection;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDTO {

    private static void setSupplierCount() {


    }

    public static List<Supplier> getAllSuppliers() throws SQLException {
        List<Supplier> suppliers = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "SELECT * FROM supplier";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String supplierId = resultSet.getString("Supplier_Id");
                String supplierName = resultSet.getString("Supplier_name");
                String supplierAddress = resultSet.getString("Supplier_address");
                int contactNumber = Integer.parseInt(resultSet.getString("contact_number"));

                Supplier supplier = new Supplier(supplierId, supplierName, supplierAddress, contactNumber);
                suppliers.add(supplier);
            }
        }

        return suppliers;
    }

    public static boolean delete(String supplierId) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "DELETE FROM supplier WHERE Supplier_Id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, supplierId);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        }
    }

    public static boolean update(Supplier supplier) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        StringBuilder sqlBuilder = new StringBuilder("UPDATE supplier SET ");
        List<Object> params = new ArrayList<>();

        if (supplier.getSupplierName() != null) {
            sqlBuilder.append("Supplier_name = ?, ");
            params.add(supplier.getSupplierName());
        }
        if (supplier.getSupplierAddress() != null) {
            sqlBuilder.append("Supplier_address = ?, ");
            params.add(supplier.getSupplierAddress());
        }
        if (supplier.getContactNumber() != 0) {
            sqlBuilder.append("contact_number = ?, ");
            params.add(supplier.getContactNumber());
        }

        sqlBuilder.setLength(sqlBuilder.length() - 2);
        sqlBuilder.append(" WHERE supplier_id = ?");
        params.add(supplier.getSupplierId());

        String sql = sqlBuilder.toString();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                statement.setObject(i + 1, params.get(i));
            }

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
    public static  Supplier searchById(String id) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "SELECT Supplier_Id, Supplier_name, Supplier_address, contact_number FROM supplier WHERE Supplier_Id = ?";

        Supplier supplier = null;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String supplierId = resultSet.getString("Supplier_Id");
                    String supplierName = resultSet.getString("Supplier_name");
                    String supplierAddress = resultSet.getString("Supplier_address");
                    int contactNumber = Integer.parseInt(resultSet.getString("contact_number"));

                    supplier = new Supplier(supplierId, supplierName, supplierAddress, contactNumber);
                }
            }
        }

        return supplier;
    }

    public static boolean insert(Supplier supplier) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "INSERT INTO supplier (Supplier_Id, Supplier_name, Supplier_address, contact_number) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, supplier.getSupplierId());
            statement.setString(2, supplier.getSupplierName());
            statement.setString(3, supplier.getSupplierAddress());
            statement.setInt(4, supplier.getContactNumber());

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        }
    }

    public static Supplier getById(String supplierId) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE Supplier_Id = ?";

        try (Connection connection = DBconnection1.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, supplierId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Supplier(
                            resultSet.getString("Supplier_Id"),
                            resultSet.getString("Supplier_name"),
                            resultSet.getString("Supplier_address"),
                            resultSet.getInt("contact_number")
                    );
                } else {
                    return null;
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
        @SneakyThrows
public static List<String> getAllSupplierIds() throws SQLException {
    String sql = "SELECT Supplier_Id FROM supplier";
    List<String> supplierIds = new ArrayList<>();

    try (Connection connection = DbConnection.getInstance().getConnection();
         PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
            supplierIds.add(resultSet.getString("Supplier_Id"));
        }
    }
    return supplierIds;
}
}
