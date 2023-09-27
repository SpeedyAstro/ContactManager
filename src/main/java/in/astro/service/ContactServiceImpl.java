package in.astro.service;

import in.astro.dao.IContactRepository;
import in.astro.model.Contact;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements IContactService{
    private IContactRepository repository;
    @Override
    public String saveContact(Contact contact) {

        return repository.save(contact).getName()+"has Successfully added into contact list";
    }
}
