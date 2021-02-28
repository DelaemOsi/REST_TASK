package by.fpmi.rest.task.repository;

import by.fpmi.rest.task.entities.Contact;
import by.fpmi.rest.task.repository.specificationss.ContactSpecification;

import java.util.Collection;

public interface ContactRepository {

    void addContact(Contact contact);

    void removeContact(Contact contact);

    void updateContact(Contact contact);

    Collection<Contact> query(ContactSpecification specification);

}
