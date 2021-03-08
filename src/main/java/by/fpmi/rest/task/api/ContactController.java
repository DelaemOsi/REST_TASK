package by.fpmi.rest.task.api;

import by.fpmi.rest.task.dao.ContactDao;
import by.fpmi.rest.task.dao.ContactDaoMock;
import by.fpmi.rest.task.entities.Contact;

import java.util.List;
import java.util.UUID;


public class ContactController {
    public static final ContactDao contactDao = new ContactDaoMock();

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
                () -> new NonExistedContactException("Contact with id " + id + " don't exists")
        );
    }
}
