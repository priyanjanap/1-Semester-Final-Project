package Lk.ijse.Dress;

import Lk.ijse.Dress.Controller.subController.MakeOrd2PaymentFormController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class EmailSender extends Application {
    @FXML
    private TextField recipientField;
    @FXML
    private Button chooseFileButton;
    @FXML
    private Button sendButton;
    private File selectedFile;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Email Sender with Attachment");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/ME4.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load(), 400, 150);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    private void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");

        selectedFile = fileChooser.showOpenDialog(new Stage());
    }

    @FXML
    private void sendEmail() {
        String recipient = recipientField.getText();
        if (recipient.isEmpty()) {
            System.out.println("Recipient field cannot be empty.");
            return;
        }

        if (selectedFile == null) {
            System.out.println("Please choose a file to attach.");
            return;
        }

        String senderEmail = "luxoradress@gmail.com";
        String senderPassword = "xfpfwejiagtmswmi";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");


        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Email with Attachment");

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Please find the attached file.");

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(selectedFile);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email sent successfully.");
            new Alert( Alert.AlertType.INFORMATION,"Email sent successfully").show();;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error occurred: " + ex.getMessage());
            new Alert( Alert.AlertType.ERROR,"Email not sent ").show();;

        }
    }
MakeOrd2PaymentFormController makeOrd2PaymentFormController=new MakeOrd2PaymentFormController();
    public void sendEmailWithAttachment(String recipientEmail, File attachmentFile) {
        String senderEmail = "luxoradress@gmail.com";
        String senderPassword = "xfpfwejiagtmswmi";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Bill Payment Receipt");

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Please find the attached bill receipt.");

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(attachmentFile);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email sent successfully.");
            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully").show();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error occurred: " + ex.getMessage());
            new Alert(Alert.AlertType.ERROR, "Email not sent").show();
        }
    }
    }

