package meika.poika;

import meika.poika.data.handlers.InputOverlay;
import utils.TelemetryData;
import utils.TelemetryDataParser;
import utils.udp.UdpListener;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

public class App {

    private static JFrame frame;

    public static void main(String[] args) throws IOException {
        UdpListener<TelemetryData> l = new UdpListener<>(InetAddress.getByName("localhost"), 20777, new TelemetryDataParser());
        frame = new JFrame("Telemetry app");
        InputOverlay overlay = new InputOverlay();
        // initVisuals(frame, overlay);
        l.addHandler(overlay);
        l.run();
    }

    private static void initVisuals(JFrame frame, JPanel content) {
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 0));
        frame.setAlwaysOnTop(true);

        frame.setSize(600, 500);
        frame.setLocation(500, 250);
        /**
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height - frame.getHeight() / 2);
*/
        frame.getContentPane().setLayout(new java.awt.BorderLayout());
        // frame.getContentPane().add(new JTextField("text field north"), java.awt.BorderLayout.NORTH);
        // frame.getContentPane().add(new JTextField("text field south"), java.awt.BorderLayout.SOUTH);
        frame.setContentPane(content);
        frame.setVisible(true);
    }


}
