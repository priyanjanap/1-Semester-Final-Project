package Lk.ijse.Dress.Controller.subController;

import Lk.ijse.Dress.Controller.subController.QRScanner;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class EmployeeNicFormController {
    public AnchorPane employeAnchor;
    @FXML
    private ImageView colseImage;
    @FXML
    private TextField nictext;

    @FXML
    private JFXButton scanButton;

    @FXML
    private JFXButton viewButton;

    @FXML
    void onCanselIconCLick(MouseEvent event) {
        Parent parent = employeAnchor.getParent();
        if (parent instanceof Pane) {
            Pane parentPane = (Pane) parent;
            parentPane.getChildren().remove(employeAnchor);
        }
    }


    private String filePath;
    public static String jobRoll;
    public void setJobRoll(String filePath) {
        this.jobRoll=filePath;
    }
    public  String getJobRoll(){

        return jobRoll;
    }
    @FXML
    void onScanClick(ActionEvent event) {
        QRScanner scanner = new QRScanner();
        scanner.scanQRCode(new QRScanner.QRScannerCallback() {
            @SneakyThrows
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    String[] parts = result.split(",");
                    if (parts.length >= 5) {
                         filePath = parts[3];
                         setJobRoll(filePath);
                            nictext.setText(parts[4]);

                            System.out.println(" jobroll    "+filePath);

                    } else {
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Data not found in QR code");
                            alert.showAndWait();
                        });
                    }
                }
                QRScanner.closeWebcamPanel();
            }
        });
    }

    @FXML
    private void navigateToNextPage() throws IOException {
        Parent rootNode = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/employeeReport_form.fxml")));
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @SneakyThrows
    private boolean validateData(String result) {
        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";
        String sqlQuery = "SELECT COUNT(*) AS count FROM qr_codes WHERE qr_code_data = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                byte[] qrCodeData = resultSet.getBytes("qr_code_data");
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(qrCodeData));

                String qrCodeText = decodeQRCode(image);
                System.out.println("QR Code Text: " + qrCodeText);
            } else {
                System.out.println("No QR code data found.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String decodeQRCode(BufferedImage image) {
        try {
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
            Result result = new MultiFormatReader().decode(binaryBitmap);
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("QR Code not found in the image.");
            return null;
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

        System.out.println("Value of nic2 before accessing: " + nic2);

        System.out.println("NIC entered by user: " + nic);

        String url = "jdbc:mysql://localhost:3306/luxora";
        String username = "root";
        String password = "p1a2s3i4n5@P";

        String sqlQuery = "SELECT COUNT(*) AS count FROM qr_codes WHERE nic_number= ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            statement.setString(1, nic);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                if (count > 0) {
                    setNic2(nic);
                    System.out.println("Value of nic2 after accessing: " + nic2);

                    Parent rootNode = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/employeeReport_form.fxml")));
                    Scene scene = new Scene(rootNode);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();

                    QRScanner.closeWebcamPanel();

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
