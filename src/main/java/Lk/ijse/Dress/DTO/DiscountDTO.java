package Lk.ijse.Dress.DTO;

import Lk.ijse.Dress.Model.Discount;
import Lk.ijse.Dress.db.DBconnection1;
import Lk.ijse.Dress.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountDTO {
    public static boolean save(Discount discount) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO discount VALUES(?, ?, ?, ?)";

        Connection connection = DBconnection1.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, discount.getItem_id());
        pstm.setObject(2, discount.getDescription());
        pstm.setObject(3, discount.getUnit_price());
        pstm.setObject(4, discount.getQty());

        return pstm.executeUpdate() > 0;
    }

    public static List<String> getCodes() throws SQLException {
        String sql = "SELECT item_id FROM discount";
        ResultSet resultSet = null;
        resultSet = DbConnection.getInstance()
                .getConnection()
                .prepareStatement(sql)
                .executeQuery();

        List<String> codeList = new ArrayList<>();
        while (resultSet.next()) {
            codeList.add(resultSet.getString(1));
        }
        return codeList;
    }

    public static Discount searchById(String item_id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM discount WHERE item_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
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
}
