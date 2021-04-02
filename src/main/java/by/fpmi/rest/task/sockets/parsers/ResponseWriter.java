package by.fpmi.rest.task.sockets.parsers;

import by.fpmi.rest.task.sockets.data.Response;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseWriter {
    private final OutputStream out;

    public ResponseWriter(OutputStream outputStream) {
        this.out = outputStream;
    }

    public void write(String sentContent) throws IOException {
        Response resp = new Response();
        resp.setLine("HTTP/1.1 200 OK");
        resp.addHeader("Server", "REST");
        resp.addHeader("Content-Type", "application/json");
        resp.addHeader("Content-Length", Integer.toString(sentContent.length()));
        resp.addHeader("Connection", "close");
        String result = resp.buildResponse()+ "\r\n" + sentContent;
        out.write(result.getBytes());
        out.flush();
    }
}
