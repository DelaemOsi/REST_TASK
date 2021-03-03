package by.fpmi.rest.task.entities;

import java.util.Objects;
import java.util.UUID;

public class Contact {
    private UUID id;
    private String name;
    private String surname;
    private String phone;

    public Contact(UUID id, String name, String surname, String phone) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

    public Contact() {

    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;

        Contact contact = (Contact) o;

        if (!Objects.equals(id, contact.id)) {
            return false;
        }
        if (!Objects.equals(name, contact.name)) {
            return false;
        }
        if (!Objects.equals(surname, contact.surname)) {
            return false;
        }
        return Objects.equals(phone, contact.phone);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }
}
