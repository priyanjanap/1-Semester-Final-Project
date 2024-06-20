package Lk.ijse.Dress.Controller;

import Lk.ijse.Dress.db.DbConnection;
import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    public StackPane Stackpane;
    @FXML
    private JFXButton btmLogin;
    @FXML
    private AnchorPane MainAnchorPane;
    public TextField txtUserName;
    public PasswordField txtPassword;
    public Label lbldisplay;
    public Label lblcount;



    public Button btmSignup;
    public Hyperlink changeYourPassword;

    int remainingAttempts= 3;
    int count=0;
    private boolean loginDisabled = false;

    public void btmLoginOnAction(ActionEvent actionEvent) throws IOException, SQLException {


        String userId = txtUserName.getText();
        String password = txtPassword.getText();

        if (userId.isBlank() && password.isBlank()) {
            lbldisplay.setText("Please enter both username and password.");
            return;
        }


        validateLogin(userId, password);
    }
    @SneakyThrows
    public void validateLogin(String userId, String password) throws SQLException, IOException {
        String sql = "SELECT Username, password FROM useraccount WHERE Username = ?";
        Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql); {
            preparedStatement.setString(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String dbPassword = resultSet.getString("password");

                    if (password.equals(dbPassword)) {
                        lbldisplay.setText("Welcome");
                        Timeline timeline = getTimeline();
                        timeline.play();


                    } else {
                        handleInvalidLogin();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Username does not exist").show();
                    }
                }


        }
    }
    private Timeline getTimeline() {
        Duration duration = Duration.seconds(1.5);
        Timeline timeline = new Timeline(new KeyFrame(duration, event -> {
            Parent rootNode = null;
            try {
                rootNode = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/dashboard1_form.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(rootNode);
            Stage stage = new Stage();


            stage.initStyle(StageStyle.UNDECORATED);
           // stage.setMaximized(true);

            stage.setScene(scene);
            stage.show();


            Stage window = (Stage) txtPassword.getScene().getWindow();
            window.close();
        }, new KeyValue[]{}));
        return timeline;
    }


    private void openCountDownForm() throws IOException {
        Parent rootNode = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/VIEW2/CountDown_form.fxml")));
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();
        Duration duration = Duration.seconds(16);
        Timeline timeline = new Timeline(new KeyFrame(duration, event -> {
            stage.close();
            lbldisplay.setText("");
            loginDisabled = false;
        }));
        timeline.play();
    }

    private void handleInvalidLogin() throws IOException {lbldisplay.setText("invalid login. Please try again");
        count++;
        int ref = (3 - count);
        lblcount.setText("You have " + ref + " remaining");

        if (ref == 2) {
            lbldisplay.setText("Invalid login attempts exceeded. Please try again later.");
            lblcount.setText("You have 2 remaining");

            lbldisplay.setStyle("-fx-text-fill: red;");
            lblcount.setStyle("-fx-text-fill: red;");
        } else if (ref == 1) {
            lbldisplay.setText("Invalid login attempts exceeded. Please try again later.");
            lblcount.setText("You have 1 remaining");

            lbldisplay.setStyle("-fx-text-fill:red;");
            lblcount.setStyle("-fx-text-fill: red;");
        } else if (ref == 0) {
            openCountDownForm();
            lblcount.setText("");

            PauseTransition pause = new PauseTransition(Duration.seconds(15));
            pause.setOnFinished(event -> {
                lbldisplay.setText("now you can insert UserName and Password");
                lbldisplay.setStyle("-fx-text-fill: blue");
            });
            pause.play();
        } else {
            lbldisplay.setText("Invalid login attempts exceeded. Please try again later.");
            lblcount.setText("You have " + ref + " remaining");

            lbldisplay.setStyle("-fx-text-fill: red;");
            lblcount.setStyle("-fx-text-fill: red;");
        }

    }

   public void btmSignUp(ActionEvent actionEvent) throws IOException, SQLException{/*
        Parent rootNode = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/signup_form.fxml")));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();*/

       Parent rootNode= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/signup_form.fxml")));
       Scene scene=btmSignup.getScene();
       rootNode.translateXProperty().set(-scene.getWidth());
     //  StackPane  Stackpane=(StackPane)scene.getRoot();
       Stackpane.getChildren().add(rootNode);
       Timeline timeline=new Timeline();
       KeyValue keyValue=new KeyValue(rootNode.translateXProperty(),0, Interpolator.EASE_IN);
       KeyFrame keyFrame= new KeyFrame(Duration.seconds(1),keyValue);
       timeline.getKeyFrames().add(keyFrame);
       timeline.setOnFinished(event1 -> {
           Stackpane.getChildren().removeAll(MainAnchorPane);
       });
       timeline.play();
    }



    public void hyChangeYPassowrdOnAction(ActionEvent actionEvent) throws IOException {

        Parent rootNode = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/VIEW2/change_Password_form.fxml")));
        Scene scene = changeYourPassword.getScene();
        scene.setRoot(rootNode);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



}