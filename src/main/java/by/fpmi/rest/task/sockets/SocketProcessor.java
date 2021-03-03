package by.fpmi.rest.task.sockets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class SocketProcessor implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(SocketProcessor.class);

    private final Socket socket;
    private final InputStream inputStream;
    private final OutputStream outputStream;
    private RequestType type;
    private List<String> requestContent = new ArrayList<>();
    private String requestAddress;

    public SocketProcessor(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();

    }

    @Override
    public void run() {
        try {
            readInputHeaders();
            handleResponse();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }

        }
    }

    private void handleResponse() throws IOException {
        String sentContent = "";
        if (type == null) {
            return;
        }
        switch (type) {
            case GET:
                sentContent = "You sent get";
                break;
            case PUT:
                sentContent = "You sent put";
                break;
            case POST:
                sentContent = "You sent post";
                break;
            case DELETE:
                sentContent = "You sent delete";
                break;
            default:
                sentContent = "Unknown request";
        }
        sentContent += ": " + requestAddress;
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: REST/2021-09-09\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + sentContent.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response + sentContent;
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
            requestContent.add(string);
            if (string.contains("HTTP")) {
                defineRequestType(string);
                break;
            }
        }
    }

    private void defineRequestType(String requestLine) {
        String[] requests = requestLine.split(" ");
        String requestType = requests[0].trim();
        type = RequestType.valueOf(requestType);

        if(hasAddressExtended(requests)){
            requestAddress = requests[1];
        }

    }

    private boolean hasAddressExtended(String[] requests) {
        return requests.length > 2;
    }
}
