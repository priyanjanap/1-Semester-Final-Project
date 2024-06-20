package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.DTO.Discount;
import Lk.ijse.Dress.DTO.Order_discount;
import Lk.ijse.Dress.db.DbConnection;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiscountDTO {

    @SneakyThrows
    public static boolean save(Discount discount) throws SQLException {
        String sql = "INSERT INTO discount VALUES(?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, discount.getItem_id());
        pstm.setObject(2, discount.getDescription());
        pstm.setObject(3, discount.getUnit_price());
        pstm.setObject(4, discount.getQty());

        return pstm.executeUpdate() > 0;
    }

    public static List<String> getCodes() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "SELECT item_id FROM discount";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {
            List<String> codeList = new ArrayList<>();
            while (resultSet.next()) {
                codeList.add(resultSet.getString(1));
            }
            return codeList;
        }
    }
    public static Discount searchById(String item_id) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "SELECT * FROM discount WHERE item_id = ?";
        PreparedStatement pstm = DriverManager.getConnection(url,username,password)
                .prepareStatement(sql);

        pstm.setObject(1, item_id);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Discount(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            );
        }
        return null;
    }
    public static boolean update(List<Order_discount> odList) throws SQLException {
        for (Order_discount od : odList) {
            boolean isUpdateQty = updateQty(od.getItem_id(), od.getQty());
            if(!isUpdateQty) {
                return false;
            }
        }
        return true;
    }
@SneakyThrows
    private static boolean updateQty(String itemCode, int qty) throws SQLException {

        String sql = "UPDATE discount SET qty = qty - ? WHERE item_id = ?";

PreparedStatement pstm=DbConnection.getInstance().getConnection()
        .prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setString(2, itemCode);

        return pstm.executeUpdate() > 0;
    }
}


