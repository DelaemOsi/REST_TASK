package by.fpmi.rest.task.api;

import by.fpmi.rest.task.entities.Contact;
import by.fpmi.rest.task.service.ClientService;
import java.util.List;

import java.util.UUID;

public class ContactController {
    private final ClientService service;

    public ContactController(ClientService service) {
        this.service = service;
    }


    public List<Contact> getAll(){
        return service.getAll();
    }

    public void addContact(Contact contact){
        service.addContact(contact);
    }

    public void updateContact(Contact contact, UUID id){
        service.updateContact(contact, id);
    }

    public void removeContact(UUID id){
        service.removeContact(id);
    }

    public Contact getContact(UUID id){
        return service.getContact(id);
    }
}
