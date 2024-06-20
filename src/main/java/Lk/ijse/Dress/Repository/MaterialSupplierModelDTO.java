package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.DTO.MaterialSupplierModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialSupplierModelDTO {
    public static List<MaterialSupplierModel> loadAllMaterialSuppliers() throws SQLException {

        List<MaterialSupplierModel> materialSupplierList = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "SELECT * from  material_supplier";



        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String supplierId = resultSet.getString("Supplier_Id");
                String materialId = resultSet.getString("Material_Id");
                String materialName = resultSet.getString("materialName");
                String supplierName = resultSet.getString("supplierName");
                int qty = resultSet.getInt("qty");
                double price = resultSet.getDouble("price");

                MaterialSupplierModel materialSupplier = new MaterialSupplierModel(supplierId, materialId, materialName, supplierName, qty, price);
                materialSupplierList.add(materialSupplier);
            }
        }

        return materialSupplierList;
    }
}
