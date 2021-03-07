package by.fpmi.rest.task.sockets.parsers;

import by.fpmi.rest.task.entities.Contact;

import java.util.Optional;

public class ClientRequest {
    private String message;
    private Optional<Contact>body;
}
