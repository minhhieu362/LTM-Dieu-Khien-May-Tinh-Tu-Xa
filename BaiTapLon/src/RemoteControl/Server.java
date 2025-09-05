package RemoteControl;

import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server đang chờ client...");

            Socket socket = serverSocket.accept();
            System.out.println("Client đã kết nối: " + socket.getInetAddress());

            // Luồng gửi màn hình
            Thread screenSender = new Thread(new ScreenSender(socket));
            screenSender.start();

            // Luồng nhận input từ client
            Thread inputExecutor = new Thread(new RemoteInputExecutor(socket));
            inputExecutor.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

