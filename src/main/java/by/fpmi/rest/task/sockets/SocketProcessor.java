package by.fpmi.rest.task.sockets;

import by.fpmi.rest.task.entities.Contact;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class SocketProcessor implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(SocketProcessor.class);

    private final Socket socket;
    private final InputStream inputStream;
    private final OutputStream outputStream;
    private RequestType type;
    private List<String> requestContent = new ArrayList<>();
    private String requestAddress;
    private String message;

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
        String string;
        do {
            string = reader.readLine();
            if(string != null) {
                requestContent.add(string);
                if (string.contains("HTTP")) {
                    defineRequestType(string);
                }
            }
        }
        while (string != null && string.trim().length() != 0);
        reader.readLine();
        parseBody(reader);
    }

    private Optional<Contact> parseBody(BufferedReader reader) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String body = "";
        String currentString;
        do {
            currentString = reader.readLine();
            requestContent.add(currentString);
            body = body.concat(currentString);
        }
        while (currentString != null && currentString.trim().length() != 0);
        Optional<Contact>contact = Optional.of(mapper.readValue(body, Contact.class));
        return contact;
    }

    private void defineRequestType(String requestLine) {

        String[] requests = requestLine.split(" ");
        String requestType = requests[0].trim();
        type = RequestType.valueOf(requestType);

        if (hasAddressExtended(requests)) {
            requestAddress = requests[1];
        }
        message = requestLine;
//        if (true) {
//            Contact newContact = new Gson()
//        }
    }

    private boolean hasAddressExtended(String[] requests) {
        return requests.length > 2;
    }
}
