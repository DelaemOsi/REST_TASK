package by.fpmi.rest.task.sockets.parsers;

import by.fpmi.rest.task.api.ContactController;
import by.fpmi.rest.task.api.NonExistedContactException;
import by.fpmi.rest.task.entities.Contact;
import by.fpmi.rest.task.sockets.RequestType;
import by.fpmi.rest.task.sockets.data.ClientRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

public class RequestHandler {

    private final ObjectMapper mapper = new ObjectMapper();
    private final ResponseWriter responseWriter;


    public RequestHandler(OutputStream outputStream) {
        this.responseWriter = new ResponseWriter(outputStream);
    }

    public void handle(ClientRequest request) throws IOException, NonExistedContactException {
        String sentContent = "";
        RequestType type = request.getRequestType();
        if (type == null) {
            return;
        }
        ContactController controller = new ContactController();
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
        responseWriter.write(sentContent);
    }

    private void handleDelete(ClientRequest request, ContactController controller) {
        UUID deleteId = UUID.fromString(request.getAddress());
        controller.removeContact(deleteId);
    }

    private void handlePost(ClientRequest request, ContactController controller) {
        Contact newContact = request.getBody();
        controller.addContact(newContact);
    }

    private void handlePut(ClientRequest request, ContactController controller) {
        UUID updateId = UUID.fromString(request.getAddress());
        Contact contactToUpdate = request.getBody();
        controller.updateContact(contactToUpdate, updateId);
    }

    private String handleGet(ClientRequest request, ContactController controller) throws NonExistedContactException,
            JsonProcessingException {
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
