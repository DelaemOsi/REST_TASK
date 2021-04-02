package by.fpmi.rest.task.sockets.data;

import java.util.ArrayList;
import java.util.List;

public class Response {
    private List<String> headers = new ArrayList<>();
    private String requestLine;

    public void addHeader(String name, String value) {
        String newHeader = name + ":" +  value;
        headers.add(newHeader);
    }

    public String buildResponse() {
       String headersString =  String.join("\r\n", headers);
        return requestLine + "\r\n" + headersString + "\r\n";
    }

    public void setLine(String line) {
        this.requestLine = line;
    }
}
