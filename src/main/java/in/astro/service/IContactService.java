package in.astro.service;

import in.astro.model.Contact;

import java.util.List;

public interface IContactService {
    public String saveContact(Contact contact);
    public List<Contact> findContactUserId(Integer id);
}
