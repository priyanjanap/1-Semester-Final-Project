package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.DTO.MaterialModel;
import Lk.ijse.Dress.DTO.OrderMaterial;
import Lk.ijse.Dress.db.DbConnection;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDTO {


    public static List<MaterialModel> loadAllMaterial() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "SELECT * FROM material";
        List<MaterialModel> materialList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String materialId = resultSet.getString(1);
                String materialName = resultSet.getString(2);
                int qty = resultSet.getInt(3);
                double price = resultSet.getDouble(4);

                MaterialModel materialModel = new MaterialModel(materialId, materialName, qty, price);
                materialList.add(materialModel);
            }
        }

        return materialList;
    }

    public static List<MaterialModel> loadAllMaterial2(String id) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "SELECT * FROM material";
        List<MaterialModel> materialList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String materialId = resultSet.getString(1);
                String materialName = resultSet.getString(2);
                int qty = resultSet.getInt(3);
                double price = resultSet.getDouble(4);

                materialList.add(new MaterialModel(materialId,materialName,qty,price));
            }
            return  materialList;
        }
    }
    public static boolean save(MaterialModel materialModel) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";


        String sql = "INSERT INTO material VALUES(?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setObject(1, materialModel.getMaterialId());
            pstm.setObject(2, materialModel.getMaterialName());
            pstm.setObject(3, materialModel.getQty());
            pstm.setObject(4, materialModel.getPrice());

            return pstm.executeUpdate() > 0;
        }
    }

    @SneakyThrows
    public static boolean delete(String materialId) throws SQLException {
        String sql = "DELETE FROM material WHERE Material_Id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, materialId);

        return pstm.executeUpdate() > 0;
    }
    public static MaterialModel searchById(String materialId) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "SELECT * FROM material WHERE Material_Id = ?";

        Connection connection=DriverManager.getConnection(url,username,password);
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, materialId);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            int qty = resultSet.getInt(3);
            double price = resultSet.getDouble(4);

            MaterialModel material = new MaterialModel(id, name, qty, price);

            return material;
        }

        return null;
    }
    public static List<String> getAllMaterialIds() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "SELECT material_Id FROM material";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            List<String> materialIds = new ArrayList<>();
            while (resultSet.next()) {
                String materialId = resultSet.getString("Material_Id");
                materialIds.add(materialId);
            }

            return materialIds;
        }
    }

    public static boolean update(MaterialModel material) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        StringBuilder sqlBuilder = new StringBuilder("UPDATE material SET ");
        List<Object> params = new ArrayList<>();

        if (material.getMaterialName() != null) {
            sqlBuilder.append("Material_name = ?, ");
            params.add(material.getMaterialName());
        }
        if (material.getQty() != 0) {
            sqlBuilder.append("quantity = ?, ");
            params.add(material.getQty());
        }
        if (material.getPrice() != 0) {
            sqlBuilder.append("price = ?, ");
            params.add(material.getPrice());
        }

        sqlBuilder.setLength(sqlBuilder.length() - 2);
        sqlBuilder.append(" WHERE Material_Id = ?");
        params.add(material.getMaterialId());

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
    public static boolean update(List<OrderMaterial> odList) throws SQLException {
        for (OrderMaterial od : odList) {
            boolean isUpdateQty = updateQty(od.getId2(), od.getQty());
            if(!isUpdateQty) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(String itemCode, int qty) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "UPDATE material SET quantity = quantity - ? WHERE Material_Id = ?";

       Connection connection=DriverManager.getConnection(url,username,password);
       PreparedStatement pstm=connection
                .prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, itemCode);

        return pstm.executeUpdate() > 0;
    }

}
