package Lk.ijse.Dress.Controller.subController;

import Lk.ijse.Dress.EmailSender;
import Lk.ijse.Dress.DTO.Enum.PaymentType;
import Lk.ijse.Dress.DTO.PaymentOrder;
import Lk.ijse.Dress.Repository.PaymentRepo;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public class MakeOrd2PaymentFormController {

    public TextField txtemail;
    @FXML
    private JFXButton cansel;

    @FXML
    private JFXComboBox<PaymentType> comboType;

    @FXML
    private Label lblAmountDue;

    @FXML
    private Label lblPayId;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblcusid;

    @FXML
    private Label lblname;

    @FXML
    private TextField txtpayemnt;

    @FXML
    private JFXButton update;
    MakeOrdPaymentFormController makeOrdPaymentFormController=new MakeOrdPaymentFormController();

    @FXML
    void initialize() throws SQLException {
        loadOrderDetails();
        ObservableList<PaymentType> oblist = FXCollections.observableArrayList(PaymentType.values());
        comboType.setItems(oblist);
        comboType.setValue(PaymentType.FullPayment);
        comboType.setValue(PaymentType.HalfPayment);
    }
    public void loadOrderDetails() throws SQLException {
        String nic = makeOrdPaymentFormController.getId();
        if (nic != null) {
            System.out.println("NIC: " + nic);
            try {
                List<PaymentOrder> paymentOrders = PaymentRepo.getPaymentOrdersByNic(nic);
                if (!paymentOrders.isEmpty()) {
                    PaymentOrder paymentOrder = paymentOrders.get(0);
                    lblPayId.setText(paymentOrder.getPayid());
                    lblcusid.setText(paymentOrder.getCusid());
                    lblname.setText(paymentOrder.getName());
                    lblTotal.setText(String.valueOf(paymentOrder.getTotal()));
                    lblAmountDue.setText(String.valueOf(paymentOrder.getAmount()));
                } else {
                    System.out.println("Can't find NIC number.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void canselonAction(ActionEvent event) {

    }

    @FXML
    void comboTypeOnAction(ActionEvent event) {

    }

    @FXML
    void updateonAction(ActionEvent event) {
        String nic = makeOrdPaymentFormController.getId();
        double amount = Double.parseDouble(txtpayemnt.getText());
        PaymentType paymentType = PaymentType.valueOf(String.valueOf(comboType.getValue()));

        try {
            boolean isUpdated = PaymentRepo.updatePaymentOrder(nic, amount, paymentType);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment order updated successfully!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Failed to update payment order.").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
public  void addPayment(String payId, String cusId, String name, double total, PaymentType paymentType, double amountDue){
    String pdfPath = "C:\\Users\\PRIYANJANA\\Desktop\\Dress\\bill.pdf";
    try {
        PdfWriter writer = new PdfWriter(pdfPath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Bill"));
        document.add(new Paragraph("Payment ID: " + payId));
        document.add(new Paragraph("Customer ID: " + cusId));
        document.add(new Paragraph("Customer Name: " + name));
        document.add(new Paragraph("Total: $" + total));
        document.add(new Paragraph("Payment Type: " + paymentType));
        document.add(new Paragraph("Amount Due: $" + amountDue));

        document.close();
        System.out.println("Bill generated: " + pdfPath);
        new Alert(Alert.AlertType.CONFIRMATION, "Generate bill  successfully!").show();

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }

}


    @FXML
    public void genarateBIllOnAction(ActionEvent actionEvent) {


        String payId = lblPayId.getText();
        String cusId = lblcusid.getText();
        String name = lblname.getText();
        double total = Double.parseDouble(lblTotal.getText());
        double amountDue = Double.parseDouble(lblAmountDue.getText());
        PaymentType paymentType = comboType.getValue();

        String pdfPath = "C:\\Users\\PRIYANJANA\\Desktop\\Dress\\bill.pdf";
        try {
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Bill"));
            document.add(new Paragraph("Payment ID: " + payId));
            document.add(new Paragraph("Customer ID: " + cusId));
            document.add(new Paragraph("Customer Name: " + name));
            document.add(new Paragraph("Total: $" + total));
            document.add(new Paragraph("Payment Type: " + paymentType));
            document.add(new Paragraph("Amount Due: $" + amountDue));

            document.close();
            System.out.println("Bill generated: " + pdfPath);
            new Alert(Alert.AlertType.CONFIRMATION, "Generate bill  successfully!").show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        chooseFileAndSendEmail();
    }

    private void chooseFileAndSendEmail() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Bill File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            EmailSender emailSender = new EmailSender();
            String recipientEmail = txtemail.getText();
            emailSender.sendEmailWithAttachment(recipientEmail, selectedFile);
        } else {
            System.out.println("File selection cancelled.");
        }
    }
    }

