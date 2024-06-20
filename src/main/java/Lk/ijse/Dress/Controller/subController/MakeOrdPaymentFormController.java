package Lk.ijse.Dress.Controller.subController;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.Objects;

public class MakeOrdPaymentFormController {

    @FXML
    private JFXButton bill;

    @FXML
    private ImageView colseImage;

    @FXML
    private TextField nictext;

    @FXML
    private AnchorPane payemntanchor4;

    @FXML
    void onCanselIconCLick(MouseEvent event) {
        Parent parent = payemntanchor4.getParent();
        if (parent instanceof Pane) {
            Pane parentPane = (Pane) parent;
            parentPane.getChildren().remove(payemntanchor4);
        }

    }
    public static String nic2;

    public String getId() {
        return nic2;
    }

    public void setNic2(String nic) {
        this.nic2 = nic;
    }

    @SneakyThrows
    @FXML
    void onviweClick(ActionEvent event) {
        String nic = nictext.getText();
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sqlQuery = "SELECT COUNT(*) AS count FROM paymentorder WHERE nic= ?";

        try (Connection connection = DriverManager.getConnection(url,username,password);
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            statement.setString(1, nic);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                if (count > 0) {
                    setNic2(nic);
                    Parent rootNode = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AnchorPaneMoves/makePaymentOrder2_form.fxml")));
                    Scene scene = new Scene(rootNode);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();


                } else {
                    new Alert(Alert.AlertType.ERROR, "NIC not found in database.").show();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
