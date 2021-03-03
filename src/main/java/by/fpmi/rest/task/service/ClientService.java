package by.fpmi.rest.task.service;

import by.fpmi.rest.task.dao.ContactDao;
import by.fpmi.rest.task.entities.Contact;

import java.util.List;
import java.util.UUID;

public class ClientService {

    private final ContactDao contactDao;

    public ClientService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public List<Contact> getAll(){
        return contactDao.getAll();
    }

    public void addContact(Contact contact){
        contactDao.addContact(contact);
    }

    public void updateContact(Contact contact, UUID id){
        contactDao.updateContact(contact, id);
    }

    public void removeContact(UUID id){
        contactDao.removeContact(id);
    }

    public Contact getContact(UUID id){
        return contactDao.get(id);
    }
}
