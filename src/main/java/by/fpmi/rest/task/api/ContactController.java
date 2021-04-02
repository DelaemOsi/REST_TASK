package by.fpmi.rest.task.api;

import by.fpmi.rest.task.dao.ContactDao;
import by.fpmi.rest.task.dao.ContactDaoImpl;
import by.fpmi.rest.task.entities.Contact;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;


public class ContactController {
    public static final ContactDao contactDao = new ContactDaoImpl();

    public ContactController() {
    }

    public List<Contact> getAll() {
        return contactDao.getAll();
    }

    public void addContact(Contact contact) {
        contactDao.addContact(contact);
    }

    public void updateContact(Contact contact, UUID id) {
        contactDao.updateContact(contact, id);
    }

    public void removeContact(UUID id) {
        contactDao.removeContact(id);
    }

    public Contact getContact(UUID id) throws NonExistedContactException {
        return contactDao.getContact(id).orElseThrow(
                () -> new NonExistedContactException(HttpStatus.NOT_FOUND, "Contact with id " + id + " don't exists")
        );
    }
}
