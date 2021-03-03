package by.fpmi.rest.task.dao;

import by.fpmi.rest.task.entities.Contact;

import java.util.*;

public interface ContactDao {

    void addContact(Contact contact);

    void removeContact(UUID contact);

    void updateContact(Contact contact, UUID id);

    Contact get(UUID id);

    List<Contact> getAll();
}
