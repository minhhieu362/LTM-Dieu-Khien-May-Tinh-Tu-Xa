package RemoteControl;

import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Đã kết nối tới server");

            // Nhận màn hình
            Thread screenReceiver = new Thread(new ScreenReceiver(socket));
            screenReceiver.start();

            // Gửi input
            Thread inputHandler = new Thread(new InputHandler(socket));
            inputHandler.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
