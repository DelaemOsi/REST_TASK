package by.fpmi.rest.task.sockets.parsers;

import by.fpmi.rest.task.api.ContactController;
import by.fpmi.rest.task.api.NonExistedContactException;
import by.fpmi.rest.task.entities.Contact;
import by.fpmi.rest.task.sockets.RequestType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RequestHandler {


    private final ObjectMapper mapper = new ObjectMapper();
    private final OutputStream outputStream;

    public RequestHandler(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void handle(ClientRequest request) throws IOException, NonExistedContactException {
        String sentContent = "";
        RequestType type = request.getRequestType();
        if (type == null) {
            return;
        }
        ContactController controller = new ContactController();
        var tmp = ContactController.contactDao;
        switch (type) {
            case GET:
                sentContent = handleGet(request, controller);
                break;
            case PUT:
                handlePut(request, controller);
                break;
            case POST:
                handlePost(request, controller);
                break;
            case DELETE:
                handleDelete(request, controller);
                break;
            default:
                sentContent = "Unknown request";
        }

        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: REST/2021-09-09\r\n" +
                "Content-Type: application/json\r\n" +
                "Content-Length: " + sentContent.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response + sentContent;
        outputStream.write(result.getBytes());
        outputStream.flush();
    }

    private void handleDelete(ClientRequest request, ContactController controller) {
        UUID deleteId = UUID.fromString(request.getAddress());
        controller.removeContact(deleteId);
    }

    private void handlePost(ClientRequest request, ContactController controller) {
        Optional<Contact> newContact = request.getBody();
        newContact.ifPresent(controller::addContact);
    }

    private void handlePut(ClientRequest request, ContactController controller) {
        UUID updateId = UUID.fromString(request.getAddress());
        Optional<Contact> contactToUpdate = request.getBody();
        contactToUpdate.ifPresent(contact -> controller.updateContact(contact, updateId));
    }

    private String handleGet(ClientRequest request, ContactController controller) throws NonExistedContactException, JsonProcessingException {
        String sentContent;
        String address = request.getAddress();
        if (!address.equals("")) {
            UUID searchedId = UUID.fromString(address);
            Contact contact = controller.getContact(searchedId);
            sentContent = mapper.writeValueAsString(contact);
        } else {
            List<Contact> contacts = controller.getAll();
            sentContent = mapper.writeValueAsString(contacts);
        }
        return sentContent;
    }
}
