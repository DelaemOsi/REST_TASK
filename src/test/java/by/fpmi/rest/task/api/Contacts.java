package by.fpmi.rest.task.api;

import java.util.List;
import by.fpmi.rest.task.entities.Contact;

public class Contacts {

    private List<Contact> contacts;

    public Contacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Contact> getContacts() {
        return contacts;
    }
}
