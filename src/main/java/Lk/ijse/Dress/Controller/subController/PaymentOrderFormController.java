package Lk.ijse.Dress.Controller.subController;

import Lk.ijse.Dress.EmailSender;
import Lk.ijse.Dress.DTO.Enum.PaymentType;
import Lk.ijse.Dress.DTO.Order;
import Lk.ijse.Dress.DTO.Payment;
import Lk.ijse.Dress.DTO.PaymentOrder;
import Lk.ijse.Dress.Repository.PaymentRepo;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PaymentOrderFormController {

    @FXML
    private TextField txtEmail;

    @FXML
    private JFXButton btmUpdate;

    @FXML
    private JFXButton btnCansel;

    @FXML
    private JFXComboBox<PaymentType> comboType;

    @FXML
    private DatePicker date;

    @FXML
    private Label lblCusId;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPaymentId;

    @FXML
    private Label lblamount;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtPayemnt;

    public void initialize() {
        ObservableList<PaymentType> oblist = FXCollections.observableArrayList(PaymentType.values());
        comboType.setItems(oblist);
        comboType.setValue(PaymentType.FullPayment);
    }

    public void setOrderDetails(Order order, Payment payment, String name) {
        lblCusId.setText(order.getCusId());
        lblPaymentId.setText(payment.getPaymentId());
        lblName.setText(name);
        lblamount.setText(String.valueOf(payment.getAmount()));
    }

    @FXML
    void btmUpdateOnAction(ActionEvent event) {
        String pay = lblPaymentId.getText();
        String cus = lblCusId.getText();
        String name = lblName.getText();
        double total = Double.parseDouble(lblamount.getText());
        String nic = txtNic.getText();
        PaymentType type = comboType.getValue();
        double amount1 = Double.parseDouble(txtPayemnt.getText());
        double amount2 = total - amount1;

        PaymentOrder paymentOrder = new PaymentOrder(pay, cus, name, total, nic, type, amount2);
        try {
            boolean isSaved = PaymentRepo.insert(paymentOrder);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment successfully saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        lblCusId.setText("");
        lblPaymentId.setText("");
        lblName.setText("");
        lblamount.setText("");
        txtNic.setText("");
        txtPayemnt.setText("");
        txtEmail.setText("");
        date.setValue(null);
        comboType.setValue(PaymentType.FullPayment);
    }

    @FXML
    void btnCanselOnAction(ActionEvent event) {
        // Add any specific cancel functionality if required
    }

    private void chooseFileAndSendEmail() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Bill File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            EmailSender emailSender = new EmailSender();
            String recipientEmail = txtEmail.getText();
            emailSender.sendEmailWithAttachment(recipientEmail, selectedFile);
        } else {
            System.out.println("File selection cancelled.");
        }
    }

    public static List<Integer> generateInvoiceNumbers() {
        Random random = new Random();
        List<Integer> invoiceNumbers = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            int invoiceNumber = 1000000 + random.nextInt(9000000);
            invoiceNumbers.add(invoiceNumber);
        }

        return invoiceNumbers;
    }

    @FXML
    public void btmSendBlll(ActionEvent actionEvent) {
        List<Integer> id = generateInvoiceNumbers();
        System.out.println("Generated Invoice Number: " + id);

        String payId = lblPaymentId.getText();
        String cusId = lblCusId.getText();
        String name = lblName.getText();
        String nic = txtNic.getText();
        LocalDate date1 = date.getValue();
        double total = Double.parseDouble(lblamount.getText());
        double amountDue = Double.parseDouble(txtPayemnt.getText());
        PaymentType paymentType = comboType.getValue();

        String pdfPath = "C:\\Users\\PRIYANJANA\\Desktop\\Dress\\bill.pdf";
        String imagePath = "C:\\Games\\Dress2\\src\\main\\resources\\Style\\luxora fahsions 1.png";

        try {
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            ImageData imageData = ImageDataFactory.create(imagePath);

            // Table and document creation
            float[] twocolWidth = {285f + 150f, 285f};
            float[] fullWidth = {190f * 3};
            Paragraph onsp = new Paragraph("\n");
            Table table = new Table(twocolWidth);
            table.addCell(new Cell().add(new Paragraph("LUXORA FASHIONS")).setBorder(Border.NO_BORDER).setBold());

            Table nestedTable = new Table(new float[]{twocolWidth[1] / 2, twocolWidth[1] / 2});
            nestedTable.addCell(getHeaderText("INVOICE NUMBER"));
            for (int number : id) {
                nestedTable.addCell(getHeaderTextValue(String.valueOf(number)));
            }
            nestedTable.addCell(getHeaderText("INVOICE DATE"));
            nestedTable.addCell(getHeaderTextValue(date1.toString()));
            table.addCell(new Cell().add(nestedTable).setBorder(Border.NO_BORDER));

            DeviceRgb gray = new DeviceRgb(128, 128, 128);
            Border gb = new SolidBorder(gray, 2f);
            Table driver = new Table(fullWidth);
            driver.setBorder(gb);

            document.add(new com.itextpdf.layout.element.Image(imageData));
            document.add(table);
            document.add(onsp);
            document.add(driver);
            document.add(onsp);
            document.add(new Paragraph("Payment ID: " + payId));
            document.add(new Paragraph("Customer ID: " + cusId));
            document.add(new Paragraph("Customer Name: " + name));
            document.add(new Paragraph("Customer NIC: " + nic));
            document.add(new Paragraph("Date: " + date1));
            document.add(new Paragraph("Total: Rs" + total));
            document.add(new Paragraph("Payment Type: " + paymentType));
            document.add(new Paragraph("Payment: Rs" + amountDue));

            document.close();
            System.out.println("Bill generated: " + pdfPath);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        chooseFileAndSendEmail();
    }

    static Cell getHeaderText(String value) {
        return new Cell().add(new Paragraph(value)).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
    }

    static Cell getHeaderTextValue(String value) {
        return new Cell().add(new Paragraph(value)).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    static Cell getCellOfText(String value, boolean isBold) {
        Cell myCell = new Cell().add(new Paragraph(value).setFontSize(10f)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
        return isBold ? myCell.setBold() : myCell;
    }


}
