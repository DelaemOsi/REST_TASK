package by.fpmi.rest.task.repository.specificationss;

import by.fpmi.rest.task.entities.Contact;

public interface ContactSpecification {
    boolean applies(Contact contact);

}
