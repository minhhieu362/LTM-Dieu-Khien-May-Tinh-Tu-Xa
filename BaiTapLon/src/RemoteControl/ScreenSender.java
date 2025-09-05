package RemoteControl;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import javax.imageio.ImageIO;

public class ScreenSender implements Runnable {
    private Socket socket;

    public ScreenSender(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

            while (true) {
                BufferedImage screenshot = robot.createScreenCapture(screenRect);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(screenshot, "jpg", baos);
                byte[] imageBytes = baos.toByteArray();

                dos.writeInt(imageBytes.length);
                dos.write(imageBytes);
                dos.flush();

                Thread.sleep(200); // ~5 FPS
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
