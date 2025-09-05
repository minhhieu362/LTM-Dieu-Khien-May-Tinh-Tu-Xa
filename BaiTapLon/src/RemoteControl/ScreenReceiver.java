package RemoteControl;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ScreenReceiver extends JFrame implements Runnable {
    private Socket socket;
    private JLabel lblScreen;

    public ScreenReceiver(Socket socket) {
        this.socket = socket;
        setTitle("Remote Screen");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lblScreen = new JLabel();
        add(new JScrollPane(lblScreen));
        setVisible(true);
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            while (true) {
                int length = dis.readInt();
                byte[] data = new byte[length];
                dis.readFully(data);

                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                if (img != null) {
                    lblScreen.setIcon(new ImageIcon(img));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

