package by.fpmi.rest.task.sockets.parsers;

import by.fpmi.rest.task.entities.Contact;
import by.fpmi.rest.task.sockets.RequestType;
import by.fpmi.rest.task.sockets.data.ClientRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RequestReader {

    private final ObjectMapper mapper = new ObjectMapper();
    private final InputStream inputStream;

    public RequestReader(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ClientRequest read() throws IOException {
        ClientRequest request = new ClientRequest();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String requestLine;
        do {
            requestLine = reader.readLine();
            if (requestLine == null) {
                continue;
            }
            if (requestLine.contains("HTTP")) {
                String[] requests = requestLine.split(" ");
                RequestType type = defineRequestType(requests);
                request.setRequestType(type);
                String address = defineAddress(requests);
                request.setAddress(address);
            }
        }
        while (requestLine != null && requestLine.trim().length() != 0);
        RequestType requestType = request.getRequestType();
        if (requestType == RequestType.POST || requestType == RequestType.PUT) {
            Contact contact = parseBody(reader);
            request.setBody(contact);
        }
        return request;
    }

    private Contact parseBody(BufferedReader reader) throws IOException {
        StringBuilder body = new StringBuilder();
        while (true) {
            String currentString = reader.readLine();
            body.append(currentString);
            String trimmedString = currentString.trim();
            if (trimmedString.isEmpty() || trimmedString.equals("}")) {
                break;
            }
        }
        String result = body.toString();
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        return mapper.readValue(result, Contact.class);

    }

    private RequestType defineRequestType(String[] requests) {
        String requestType = requests[0].trim();
        return RequestType.valueOf(requestType);
    }

    private String defineAddress(String[] requests) {
        String address = requests[1].trim();
        return address.replace("/", "");
    }


}
