package RemoteControl;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;

public class RemoteInputExecutor implements Runnable {
    private Socket socket;

    public RemoteInputExecutor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Robot robot = new Robot();
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            while (true) {
                String msg = dis.readUTF();
                String[] parts = msg.split(" ");
                switch (parts[0]) {
                    case "KEY_PRESS":
                        robot.keyPress(Integer.parseInt(parts[1]));
                        break;
                    case "KEY_RELEASE":
                        robot.keyRelease(Integer.parseInt(parts[1]));
                        break;
                    case "MOUSE_PRESS":
                        robot.mousePress(InputEvent.getMaskForButton(Integer.parseInt(parts[1])));
                        break;
                    case "MOUSE_RELEASE":
                        robot.mouseRelease(InputEvent.getMaskForButton(Integer.parseInt(parts[1])));
                        break;
                    case "MOUSE_MOVE":
                        robot.mouseMove(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
