package Lk.ijse.Dress.Controller.subController;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class QRScanner {

    private static final Executor EXECUTOR = Executors.newSingleThreadExecutor();
    private static WebcamPanel panel;
    private static JFrame window;
    private static Webcam webcam;

    public void scanQRCode(QRScannerCallback callback) {
        webcam = Webcam.getDefault();
        if (webcam == null) {
            System.err.println("No webcam found");
            return;
        }

        webcam.setViewSize(new Dimension(640, 480));
        panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setFPSLimited(true);
        panel.setFPSLimit(20);

        window = new JFrame("QR Scanner");
        window.add(panel);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.pack();
        window.setVisible(true);

        if (!Webcam.getDiscoveryService().isRunning()) {
            Webcam.getDiscoveryService().start();
        }

        EXECUTOR.execute(() -> {
            do {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                BufferedImage image = webcam.getImage();
                if (image != null) {
                    String result = scan(image);
                    if (result != null) {
                        callback.onSuccess(result);
                        break;
                    }
                }
            } while (true);
        });
    }

    private String scan(BufferedImage image) {
        try {
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
            Result result = new MultiFormatReader().decode(binaryBitmap);
            return result.getText();
        } catch (NotFoundException e) {
            return null;
        }
    }

    public interface QRScannerCallback {
        void onSuccess(String result);
    }

    public static void closeWebcamPanel() {
        try {
            if (panel != null) {
                panel.stop();
            }
            if (window != null) {
                window.dispose();
            }
        } finally {
            if (webcam != null) {
                webcam.close();
            }
            if (Webcam.getDiscoveryService().isRunning()) {
                Webcam.getDiscoveryService().stop();
            }
        }
    }
}
