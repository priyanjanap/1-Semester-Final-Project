package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.DTO.OrderMaterial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderMaterialDTO {
    public static boolean save(List<OrderMaterial> odList) throws SQLException {
        for (OrderMaterial od : odList) {
            boolean isSaved = save(od);
            if(!isSaved) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(OrderMaterial od) throws SQLException {
        String sql = "INSERT INTO ordermaterial VALUES(?, ?, ?, ?)";
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

Connection connection= DriverManager.getConnection(url,username,password);
PreparedStatement pstm  =connection
                .prepareStatement(sql);

        pstm.setString(1, od.getId1());
        pstm.setString(2, od.getId2());
        pstm.setInt(3, od.getQty());
        pstm.setDouble(4, od.getPrice());

        return pstm.executeUpdate() > 0;
    }

}
