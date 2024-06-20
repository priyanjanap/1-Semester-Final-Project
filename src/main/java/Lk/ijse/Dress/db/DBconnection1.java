package Lk.ijse.Dress.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection1 {
    private static DBconnection1 dbConnection;
    private Connection connection;

    private DBconnection1() throws SQLException,ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/luxora","root","p1a2s3i4n5@P");

    }
    public static DBconnection1 getInstance() throws SQLException,ClassNotFoundException{
        return dbConnection==null ? dbConnection=new DBconnection1() : dbConnection;
    }
    public Connection getConnection(){
        return connection;
    }
}
