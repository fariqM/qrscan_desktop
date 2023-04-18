package aicis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends JFrame {

    private final JLabel qrCodeLabel;
    private final JButton scanButton;
    private final WebcamPanel webcamPanel;

    public App() {
        super("QR Code Scanner");

        qrCodeLabel = new JLabel("Scan a QR code");
        scanButton = new JButton("Scan");

        webcamPanel = new WebcamPanel(Webcam.getDefault());
        webcamPanel.setPreferredSize(new Dimension(640, 480));

        JPanel controlPanel = new JPanel();
        controlPanel.add(qrCodeLabel);
        controlPanel.add(scanButton);

        getContentPane().add(webcamPanel, BorderLayout.CENTER);
        getContentPane().add(controlPanel, BorderLayout.SOUTH);

        scanButton.addActionListener(e -> {
            BufferedImage image = webcamPanel.getImage();
            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            MultiFormatReader reader = new MultiFormatReader();
            try {
                Result result = reader.decode(bitmap);
                qrCodeLabel.setText(result.getText());
            } catch (NotFoundException ex) {
                qrCodeLabel.setText("No QR code found");
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}
