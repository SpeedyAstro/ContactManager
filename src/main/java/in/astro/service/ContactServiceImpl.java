package in.astro.service;

import in.astro.dao.IContactRepository;
import in.astro.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<Contact> findContactUserId(Integer id, Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return repository.findByUserId(id, pageable);
    }
}
