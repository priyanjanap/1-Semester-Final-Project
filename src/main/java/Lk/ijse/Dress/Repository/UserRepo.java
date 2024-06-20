package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.DTO.User;

import java.sql.*;

public class UserRepo {

     public   static User getUserName(String adminnameText){

         String url = "jdbc:mysql://localhost:3306/your_database_name";
         String user = "your_database_user";
         String password = "your_database_password";



         try {
          Connection connection = DriverManager.getConnection(url, user, password);

           Statement statement = connection.createStatement();

             String query = "SELECT Username FROM useraccount";
        ResultSet resultSet = statement.executeQuery(query);

             while (resultSet.next()) {
                 String username = resultSet.getString("Username");
                 System.out.println(username);
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return null;
     }
     }


