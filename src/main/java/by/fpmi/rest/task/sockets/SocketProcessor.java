package by.fpmi.rest.task.sockets;

import by.fpmi.rest.task.api.ContactController;
import by.fpmi.rest.task.api.NonExistedContactException;
import by.fpmi.rest.task.dao.ContactDao;
import by.fpmi.rest.task.dao.ContactDaoMock;
import by.fpmi.rest.task.entities.Contact;
import by.fpmi.rest.task.sockets.parsers.ClientRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SocketProcessor implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(SocketProcessor.class);
    private static final ContactDao DAO = new ContactDaoMock();

    private final Socket socket;
    private final InputStream inputStream;
    private final OutputStream outputStream;
    private ClientRequest clientRequest = new ClientRequest();
    private ObjectMapper mapper = new ObjectMapper();

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
        } catch (IOException | NonExistedContactException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }

        }
    }

    private void handleResponse() throws IOException, NonExistedContactException {
        String sentContent = "";
        RequestType type = clientRequest.getRequestType();
        if (type == null) {
            return;
        }
        ContactController controller = new ContactController(DAO);
        switch (type) {
            case GET:
                String address = clientRequest.getAddress();
                if (address != "") {
                    UUID searchedId = UUID.fromString(address);
                    Contact contact = controller.getContact(searchedId);
                    sentContent = mapper.writeValueAsString(contact);
                } else {
                    List<Contact> contacts = controller.getAll();
                    sentContent = mapper.writeValueAsString(contacts);
                }
                break;
            case PUT:
                UUID updateId = UUID.fromString(clientRequest.getAddress());
                Optional<Contact> contactToUpdate = clientRequest.getBody();
                contactToUpdate.ifPresent(contact -> controller.updateContact(contact, updateId));
                break;
            case POST:
                Optional<Contact> newContact = clientRequest.getBody();
                newContact.ifPresent(controller::addContact);
                break;
            case DELETE:
                UUID deleteId = UUID.fromString(clientRequest.getAddress());
                controller.removeContact(deleteId);
                break;
            default:
                sentContent = "Unknown request";
        }

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
            if (string != null) {
                if (string.contains("HTTP")) {
                    defineRequestType(string);
                }
            }
        }
        while (string != null && string.trim().length() != 0);
        clientRequest.setBody(parseBody(reader));
    }

    private Optional<Contact> parseBody(BufferedReader reader) throws IOException {
        StringBuilder body = new StringBuilder();
        String currentString;
        while (true) {
            currentString = reader.readLine();
            if (currentString == null) {
                break;
            }
            body.append(currentString);
        }

        String result = body.toString();
        if (result.isEmpty()) {
            return Optional.empty();
        }
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        Optional<Contact> contact = Optional.ofNullable(mapper.readValue(result, Contact.class));
        return contact;
    }

    private void defineRequestType(String requestLine) {

        String[] requests = requestLine.split(" ");
        String requestType = requests[0].trim();
        RequestType type = RequestType.valueOf(requestType);
        clientRequest.setRequestType(type);

        if (hasAddressExtended(requests)) {
            String address = requests[1].replace("/", "");
            clientRequest.setAddress(address);
        }
    }

    private boolean hasAddressExtended(String[] requests) {
        return requests.length > 2;
    }
}
