package by.fpmi.rest.task.dao;

import by.fpmi.rest.task.entities.Contact;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ContactDaoMock implements ContactDao {

    private Map<UUID, Contact>contacts = new HashMap<>();

    @Override
    public void addContact(Contact contact) {
        UUID id = UUID.randomUUID();
    }

    @Override
    public void removeContact(UUID contact) {

    }

    @Override
    public void updateContact(Contact contact, UUID id) {

    }

    @Override
    public Contact get(UUID id) {
        return null;
    }

    @Override
    public List<Contact> getAll() {
        return null;
    }
}
