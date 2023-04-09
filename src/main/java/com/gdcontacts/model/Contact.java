package main.java.com.gdcontacts.model;

import main.java.com.gdcontacts.model.builder.ContactBuilder;

import java.util.Objects;

/**
 * Essa classe representa um contato.
 *
 * @author João Guedes.
 */
public class Contact implements Comparable<Contact> {

    private String id;
    private String name;
    private String telephone;
    private String email;

    public Contact(String id, String name, String telephone, String email) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.email = email;
    }

    public Contact(){
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Instancia um builder para a criação de um contato.
     */
    public static ContactBuilder builder() {
        return new ContactBuilder();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.telephone, this.email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        return  Objects.equals(this.id, contact.id) &&
                Objects.equals(this.name, contact.name) &&
                Objects.equals(this.telephone, contact.telephone) &&
                Objects.equals(this.email, contact.email);
    }

    @Override
    public int compareTo(Contact contact) {
        int result = this.name.compareToIgnoreCase(contact.getName());
        if (result != 0) {
            return result;
        }
        result = this.telephone.compareToIgnoreCase(contact.getTelephone());
        if (result != 0) {
            return result;
        }
        return this.email.compareToIgnoreCase(contact.getEmail());
    }

    @Override
    public String toString() {
        return "-----------------------------------------------------" +
                "\nNome: " + this.getName() +
                "\nTelefone: " + this.getTelephone() +
                "\nEmail: " + this.getEmail() +
                "\n-----------------------------------------------------";
    }

}