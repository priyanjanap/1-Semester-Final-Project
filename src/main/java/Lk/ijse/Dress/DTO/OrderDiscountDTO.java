package Lk.ijse.Dress.DTO;

import Lk.ijse.Dress.db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDiscountDTO {
    public static String getCurrentId() throws SQLException {
        String sql = "SELECT Order_id FROM orders ORDER BY Order_id DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String orderId = resultSet.getString(1);
            return orderId;
        }
        return null;
    }

}
