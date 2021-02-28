package by.fpmi.rest.task.sockets;

import java.io.*;
import java.net.Socket;

public class SocketProcessor implements Runnable {

    private final Socket socket;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public SocketProcessor(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    @Override
    public void run() {
        try {
            readInputHeaders();
            writeResponse("<html><body><h2>Made by</h2><p>Some text</p></body></html>");
        } catch (IOException throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void writeResponse(String sendedContent) throws IOException {
        String response = "HTTP/2.0 200 OK\r\n" +
                "Server: YarServer/2009-09-09\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + sendedContent.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response + sendedContent;
        outputStream.write(result.getBytes());
        outputStream.flush();
    }

    private void readInputHeaders() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        while (true) {
            String string = reader.readLine();
            if (string == null || string.trim().length() == 0) {
                break;
            }
        }
    }
}
