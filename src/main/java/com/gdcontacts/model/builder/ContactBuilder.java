package main.java.com.gdcontacts.model.builder;

import main.java.com.gdcontacts.model.Contact;

/**
 * Essa classe visa implementar um padrão de projeto para criação de objetos do tipo Contact (Contato).
 *
 * @author João Guedes.
 */
public class ContactBuilder {

    private final Contact contact;

    public ContactBuilder() {
        contact = new Contact();
    }

    public ContactBuilder id(String id) {
        this.contact.setId(id);
        return this;
    }

    public ContactBuilder name(String name) {
        this.contact.setName(name);
        return this;
    }

    public ContactBuilder telephone(String telephone) {
        this.contact.setTelephone(telephone);
        return this;
    }

    public ContactBuilder email(String email) {
        this.contact.setEmail(email);
        return this;
    }

    public Contact build() {
        return this.contact;
    }

}