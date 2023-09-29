package in.astro.service;

import in.astro.model.Contact;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IContactService {
    public String saveContact(Contact contact);
    public Page<Contact> findContactUserId(Integer id, Integer page);
    public Contact getContactDetails(Integer cid);
}
