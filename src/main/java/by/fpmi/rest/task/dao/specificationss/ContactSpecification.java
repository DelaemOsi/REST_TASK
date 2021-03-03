package by.fpmi.rest.task.dao.specificationss;

import by.fpmi.rest.task.entities.Contact;

public interface ContactSpecification {
    boolean applies(Contact contact);

}
