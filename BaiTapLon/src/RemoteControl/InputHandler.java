package RemoteControl;

import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import javax.swing.*;

public class InputHandler extends JFrame implements Runnable {
    private Socket socket;

    public InputHandler(Socket socket) {
        this.socket = socket;
        setTitle("Remote Input Handler");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new JLabel("Đang gửi sự kiện chuột/phím tới server..."));
        setVisible(true);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                sendEvent("KEY_PRESS " + e.getKeyCode());
            }
            public void keyReleased(KeyEvent e) {
                sendEvent("KEY_RELEASE " + e.getKeyCode());
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                sendEvent("MOUSE_PRESS " + e.getButton());
            }
            public void mouseReleased(MouseEvent e) {
                sendEvent("MOUSE_RELEASE " + e.getButton());
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                sendEvent("MOUSE_MOVE " + e.getX() + " " + e.getY());
            }
        });
    }

    private void sendEvent(String msg) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(msg);
            dos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {}
}

