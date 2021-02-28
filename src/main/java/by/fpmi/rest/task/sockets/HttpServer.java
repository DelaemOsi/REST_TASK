package by.fpmi.rest.task.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        while (true) {
            Socket socket = server.accept();
            System.err.println("Client accepted");
            new Thread(new SocketProcessor(socket)).start();
        }
    }
}
