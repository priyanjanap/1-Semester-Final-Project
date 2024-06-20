package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.DTO.EmployeeAtt;

import java.sql.*;

public class EmployeeAttDTO {
    public static boolean insert(EmployeeAtt employeeAtt) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sql = "INSERT INTO attendance (employee_id, attendance_date, attendance_status, punctuality_status) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, employeeAtt.getId2());
            statement.setDate(2, Date.valueOf(String.valueOf(employeeAtt.getDate())));
            statement.setString(3, String.valueOf(employeeAtt.getStatus()));
            statement.setString(4, String.valueOf(employeeAtt.getStatus2()));

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    System.out.println("Generated ID: " + generatedId);
                }
                return true;
            } else {
                return false;
            }
        }
    }

}