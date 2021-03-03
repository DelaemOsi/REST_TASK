package by.fpmi.rest.task.dao;

import by.fpmi.rest.task.entities.Contact;

import java.util.*;

public interface ContactDao {

    void insertContact(Contact contact, UUID id);

    default void addContact(Contact contact){
        UUID id = UUID.randomUUID();
        insertContact(contact, id);
    }

    void removeContact(UUID contact);

    void updateContact(Contact contact, UUID id);

    Optional<Contact> get(UUID id);

    List<Contact> getAll();
}
