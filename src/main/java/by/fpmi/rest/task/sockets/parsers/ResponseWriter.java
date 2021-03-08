package by.fpmi.rest.task.sockets.parsers;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseWriter {
    private final OutputStream out;

    public ResponseWriter(OutputStream outputStream) {
        this.out = outputStream;
    }

    public void write(String sentContent) throws IOException {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: REST/2021-09-09\r\n" +
                "Content-Type: application/json\r\n" +
                "Content-Length: " + sentContent.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response + sentContent;
        out.write(result.getBytes());
        out.flush();
    }
}
