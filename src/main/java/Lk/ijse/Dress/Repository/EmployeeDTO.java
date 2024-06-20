package Lk.ijse.Dress.Repository;

import Lk.ijse.Dress.DTO.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDTO {
    public static List<Employee> LoadAllEmployee2() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "SELECT * FROM employee";
        List<Employee> employeeList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getDouble(5),
                        resultSet.getString(6),
                        resultSet.getDate(7),
                        resultSet.getInt(8)
                );
                employeeList.add(employee);
            }
        }

        return employeeList;
    }

    public static boolean insertEmployee(Employee employee) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
//String employeeId = UUID.randomUUID().toString();
       // employee.setEmployeeId(employeeId);

        String sql = "INSERT INTO employee (Employee_ID, Employee_name, Employee_address,age, salary, gender, date_of_birth, phone_number) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employee.getEmployeeId());
            preparedStatement.setString(2, employee.getEmployeeName());
            preparedStatement.setString(3, employee.getEmployeeAddress());
            preparedStatement.setInt(4, employee.getEmployeeAge());
            preparedStatement.setDouble(5, employee.getSalary());
            preparedStatement.setString(6, employee.getGender());
            preparedStatement.setDate(7, employee.getDateOfBirth());
            preparedStatement.setInt(8, employee.getContactNumber());

            int rowsAffected;
            rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }
    public static Employee getEmployeeById(String employeeId) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "SELECT * FROM employee WHERE Employee_ID = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, employeeId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Employee employee = new Employee();
                    employee.setEmployeeId(resultSet.getString("Employee_ID"));
                    employee.setEmployeeName(resultSet.getString("Employee_name"));
                    employee.setSalary(resultSet.getDouble("salary"));
                    employee.setContactNumber(resultSet.getInt("phone_number"));

                    return employee;
                } else {
                    return null;
                }
            }
        }
    }

    public static boolean updateEmployee(Employee employee) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "UPDATE employee Employee_name = ?, Employee_address = ?, age = ?, salary = ?, gender = ?, date_of_birth = ?, phone_number = ? WHERE Employee_ID = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, employee.getEmployeeName());
            preparedStatement.setString(2, employee.getEmployeeAddress());
            preparedStatement.setInt(3, employee.getEmployeeAge());
            preparedStatement.setDouble(4, employee.getSalary());
            preparedStatement.setString(5, employee.getGender());
            preparedStatement.setDate(6, new java.sql.Date(employee.getDateOfBirth().getTime()));
            preparedStatement.setInt(7, employee.getContactNumber());
            preparedStatement.setString(8, employee.getEmployeeId());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }
    public static boolean updateEmployee2(Employee employee) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        StringBuilder sqlBuilder = new StringBuilder("UPDATE employee SET ");
        List<Object> params = new ArrayList<>();

        if (employee.getEmployeeName() != null) {
            sqlBuilder.append("Employee_name = ?, ");
            params.add(employee.getEmployeeName());
        }
        if (employee.getEmployeeAddress() != null) {
            sqlBuilder.append("Employee_address = ?, ");
            params.add(employee.getEmployeeAddress());
        }
        if (employee.getContactNumber() != 0) {
            sqlBuilder.append("phone_number = ?, ");
            params.add(employee.getContactNumber());
        }
        if (employee.getSalary() != 0.0) {
            sqlBuilder.append("salary = ?, ");
            params.add(employee.getSalary());
        }

        sqlBuilder.setLength(sqlBuilder.length() - 2);
        sqlBuilder.append(" WHERE Employee_ID = ?");
        params.add(employee.getEmployeeId());

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

    public static boolean deleteEmployee(String employeeId) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "DELETE FROM employee WHERE Employee_ID = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employeeId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }
    public static Employee searchEmployeeById(String employeeId) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "SELECT * FROM employee WHERE Employee_Id = ?";
        Employee employee = null;

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, employeeId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee = new Employee(
                        resultSet.getString("Employee_Id"),
                        resultSet.getString("Employee_name"),
                        resultSet.getString("Employee_address"),
                        resultSet.getInt("age"),
                        resultSet.getDouble("salary"),
                        resultSet.getString("gender"),
                        resultSet.getDate("date_of_birth"),
                        resultSet.getInt("phone_number")
                );
            }
        }

        return employee;
    }



    public static List<String> getAllEmployeeIds() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sql = "SELECT Employee_ID FROM employee";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            List<String> idList = new ArrayList<>();
            while (resultSet.next()) {
                idList.add(resultSet.getString(1));
            }
            return idList;
        }
    }



}



