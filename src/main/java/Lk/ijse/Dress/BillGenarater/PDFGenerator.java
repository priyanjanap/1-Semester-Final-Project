package Lk.ijse.Dress.BillGenarater;

import Lk.ijse.Dress.DTO.Payment;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileNotFoundException;
import java.util.List;

public class PDFGenerator {
    public static void generate(List<Payment> items) {
        String pdfPath = "C:\\Users\\PRIYANJANA\\Desktop\\Dress\\bill.pdf";
        try {
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Bill"));

            float[] columnWidths = {200, 100, 100};
            Table table = new Table(columnWidths);
            table.addHeaderCell("Item Name");
            table.addHeaderCell("Quantity");
            table.addHeaderCell("Price");

           /* for (Item item : items) {
                table.addCell(item.getName());
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell(String.valueOf(item.getPrice()));
            }*/

          //  document.add(table);
            //document.close();
            System.out.println("Bill generated: " + pdfPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
