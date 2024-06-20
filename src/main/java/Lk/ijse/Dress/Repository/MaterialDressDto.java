package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.DTO.MaterialDress;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDressDto {
    public static List<MaterialDress> loadAllMaterial() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "SELECT * FROM material_dress";
        List<MaterialDress> materialDresseslList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String materialId = resultSet.getString(1);
                int qty= Integer.parseInt(resultSet.getString(2));
                String Dressid= resultSet.getString(3);
                double price=resultSet.getDouble(4);
                double total=qty*price;
                MaterialDress materialModel = new MaterialDress(materialId,qty,Dressid,price,total);
                materialDresseslList.add(materialModel);
            }
        }

        return materialDresseslList;
    }
}
