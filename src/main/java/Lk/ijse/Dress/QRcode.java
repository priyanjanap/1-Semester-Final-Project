package Lk.ijse.Dress;

import Lk.ijse.Dress.DTO.QR;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.nio.file.Paths;

public class QRcode {

    public static boolean QrGenarater(QR qr) {
        String data = qr.getId() + "," + qr.getName() + "," + qr.getTell() + "," + qr.getJobRoll() + "," + qr.getNic();

        String path = "C:\\Users\\PRIYANJANA\\Desktop\\Dress\\QR.jpg";

        try {
            BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 1000, 1000);
            MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));
            System.out.println("Data: " + data);
            return true;
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
