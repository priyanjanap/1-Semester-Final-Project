package Lk.ijse.Dress;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.sql.*;

public class QRController {

    @FXML
    public static void displayQR() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/luxora", "root", "p1a2s3i4n5@P");
            PreparedStatement statement = connection.prepareStatement("SELECT qr_code_data FROM qr_codes WHERE empId = ?");
            statement.setInt(1, 1);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                byte[] imageData = resultSet.getBytes("qr_code_data");
                Image qrImage = new Image(new ByteArrayInputStream(imageData));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("QR Code");
                alert.setHeaderText(null);

                ImageView imageView = new ImageView(qrImage);
                imageView.setFitWidth(500);
                imageView.setFitHeight(500);
                alert.getDialogPane().setContent(imageView);

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(QRController.class.getResource("alret.css").toExternalForm());

                alert.getButtonTypes().setAll(ButtonType.OK);

                alert.showAndWait();
            }
           // connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
