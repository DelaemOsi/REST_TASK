package by.fpmi.rest.task.entities;

import java.util.Objects;
import java.util.UUID;

public class Contact {
    private final UUID id;
    private String name;
    private String surname;
    private String phone;

    public Contact(UUID id, String name, String surname, String phone) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

    public Contact(Builder builder)
    {
        this.id = builder.id;
        this.name = builder.name;
        this.surname = builder.surname;
        this.phone = builder.phone;
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

    public static class Builder {

        private UUID id;
        private String name;
        private String surname;
        private String phone;

        public static Builder newInstance() {
            return new Builder();
        }

        private Builder() {
        }

        public Builder withId(UUID id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Contact build() {
            return new Contact(this);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
