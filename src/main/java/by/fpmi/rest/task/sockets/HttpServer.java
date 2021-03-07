package by.fpmi.rest.task.sockets;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private final static int THREADS_COUNT = 32;

    public void run() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(8080);
        ExecutorService service = Executors.newFixedThreadPool(32);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            service.execute(new SocketProcessor(clientSocket));
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpServer server = new HttpServer();
        server.run();
    }
}
