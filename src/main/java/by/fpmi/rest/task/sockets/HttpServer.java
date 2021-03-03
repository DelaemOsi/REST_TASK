package by.fpmi.rest.task.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new SocketProcessor(clientSocket)).start();
        }
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = new HttpServer();
        server.run();
    }
}
