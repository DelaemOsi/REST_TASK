package by.fpmi.rest.task.dao;

import by.fpmi.rest.task.entities.Contact;

import java.util.*;

public class ContactDaoMock implements ContactDao {

    private final Map<UUID, Contact> mockedDb = new HashMap<>();

    @Override
    public void insertContact(Contact contact, UUID id) {
        Contact newContact = Contact.Builder.newInstance()
                .withId(id)
                .withName(contact.getName())
                .withSurname(contact.getSurname())
                .withPhone(contact.getPhone())
                .build();
        mockedDb.put(id, newContact);
    }

    @Override
    public void removeContact(UUID id) {
        mockedDb.remove(id);
    }

    @Override
    public void updateContact(Contact updatedContact, UUID id) {
        Optional<Contact> contact = get(id);
        contact.ifPresent((c) -> updateContact(c, updatedContact));
        //TODO: what we should do when contact not found?
    }

    private void updateContact(Contact old, Contact updated) {
        var newName = Optional.ofNullable(updated.getName());
        newName.ifPresent(old::setName);

        var newSurname = Optional.ofNullable(updated.getSurname());
        newSurname.ifPresent(old::setSurname);

        var newPhone = Optional.ofNullable(updated.getPhone());
        newPhone.ifPresent(old::setPhone);
    }

    @Override
    public List<Contact> getAll() {
        return new ArrayList<>(mockedDb.values());
    }

    @Override
    public Optional<Contact> get(UUID id) {
        Contact contact = mockedDb.get(id);
        return Optional.of(contact);
    }
}
