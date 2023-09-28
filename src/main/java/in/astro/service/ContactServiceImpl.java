package in.astro.service;

import in.astro.dao.IContactRepository;
import in.astro.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements IContactService{
    @Autowired
    private IContactRepository repository;
    @Override
    public String saveContact(Contact contact) {

        return repository.save(contact).getName()+"has Successfully added into contact list";
    }

    @Override
    public List<Contact> findContactUserId(Integer id) {
        return repository.findByUserId(id);
    }
}
