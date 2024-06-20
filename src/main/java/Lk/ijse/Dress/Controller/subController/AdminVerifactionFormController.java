package Lk.ijse.Dress.Controller.subController;

import Lk.ijse.Dress.db.DbConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class AdminVerifactionFormController {

    public ImageView colseImage;
    public AnchorPane sideAnchorPane5;
    @FXML
    private Label btmVerify;

    @FXML
    private TextField txtPassowrd;

    @FXML
    private TextField txtUserName;

    @FXML
    void btmVerifyOnAction(MouseEvent event){
        String username = txtUserName.getText();
    String password = txtPassowrd.getText();

    String sql = "SELECT COUNT(*) FROM useraccount WHERE Username=?";
        try (
    Connection connection = DbConnection.getInstance().getConnection();
    PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            if (count > 0) {
                validateLogin(username,password);                } else {
                new Alert(Alert.AlertType.ERROR, "Username does not exist").show();
            }
        }
    } catch (
    SQLException e) {
        throw new RuntimeException(e);
    } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
@SneakyThrows
public void validateLogin(String userId, String password) throws SQLException, IOException {
    String url = "jdbc:mysql://localhost:3306/luxora";
    String username = "root";
    String password1 = "p1a2s3i4n5@P";

    String sql = "SELECT Username, password FROM useraccount WHERE Username = ?";
    Connection connection = DriverManager.getConnection(url,username,password);
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    {
        preparedStatement.setString(1, userId);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                String dbPassword = resultSet.getString("password");

                if (password.equals(dbPassword)) {
                    Parent rootNode = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/newAdminAdd_form.fxm")));
                    Scene scene=new Scene(rootNode);
                    Stage stage=new Stage();
                    stage.setScene(scene);
                    stage.show();



                } else {

                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Username does not exist").show();
            }
        }


    }
}

    public void onCanselIconCLick(MouseEvent mouseEvent) {
        Parent parent = sideAnchorPane5.getParent();
        if (parent instanceof Pane) {
            Pane parentPane = (Pane) parent;
            parentPane.getChildren().remove(sideAnchorPane5);
        }
    }
}
