package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.DTO.Product;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDTO {
    public static List<Product> getAllProduct() throws SQLException {
        List<Product> products = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "SELECT * FROM product";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String productId = resultSet.getString("product_id");
                String productName = resultSet.getString("product_name");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");
                String type = resultSet.getString("product_type");
                String category = resultSet.getString("category");


                Product product = new Product(productId, productName, quantity, price, description, type, category);
                products.add(product);
            }
        }

        return products;
    }

    public static Product searchById(String productId) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "SELECT * FROM product WHERE product_id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, productId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String productName = resultSet.getString("product_name");
                    int quantity = resultSet.getInt("quantity");
                    double price = resultSet.getDouble("price");
                    String description = resultSet.getString("description");
                    String type = resultSet.getString("product_type");
                    String category = resultSet.getString("category");

                    return new Product(productId, productName, quantity, price, description, type, category);
                }
            }
        }

        return null;
    }


    public static boolean insert(Product product) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "INSERT INTO product (product_id, product_name, quantity, price, description, product_type, category) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getId());
            statement.setString(2, product.getName());
            statement.setInt(3, product.getQty());
            statement.setDouble(4, product.getPrice());
            statement.setString(5, product.getDescription());
            statement.setString(6, product.getType());
            statement.setString(7, product.getCategory());

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        }
    }
    public static boolean delete(String productId) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "DELETE FROM product WHERE product_id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, productId);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        }
    }
    public static List<String> getAllProductIds() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "SELECT Product_Id FROM product";

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
    public static void saveImageToDatabase(byte[] imageData) {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO images (image_data) VALUES (?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setBytes(1, imageData);
                pstmt.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Image saved to database successfully");
                System.out.println("Image saved to database successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean update(Product product) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        StringBuilder sqlBuilder = new StringBuilder("UPDATE product SET ");
        List<Object> params = new ArrayList<>();

        if (product.getName() != null) {
            sqlBuilder.append("product_name = ?, ");
            params.add(product.getName());
        }
        if (product.getQty() != 0) {
            sqlBuilder.append("quantity = ?, ");
            params.add(product.getQty());
        }
        if (product.getPrice() != 0.0) {
            sqlBuilder.append("price = ?, ");
            params.add(product.getPrice());
        }
        if (product.getDescription() != null) {
            sqlBuilder.append("description = ?, ");
            params.add(product.getDescription());
        }
        if (product.getType() != null) {
            sqlBuilder.append("product_type = ?, ");
            params.add(product.getType());
        }
        if (product.getCategory() != null) {
            sqlBuilder.append("category = ?, ");
            params.add(product.getCategory());
        }

        sqlBuilder.setLength(sqlBuilder.length() - 2);
        sqlBuilder.append(" WHERE product_id = ?");
        params.add(product.getId());

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


}