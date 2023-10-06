package in.astro.service;

import in.astro.model.Contact;
import in.astro.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IContactService {
    public String saveContact(Contact contact);
    public Page<Contact> findContactUserId(Integer id, Integer page);
    public Contact getContactDetails(Integer cid, User user);
    public Optional<Contact> findById(Integer cid);
    public String deleteContact(Integer cid);
    public List<Contact> searchByName(String name,User user);
}
