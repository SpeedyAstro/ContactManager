package in.astro.service;

import in.astro.dao.IContactRepository;
import in.astro.model.Contact;
import in.astro.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
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

    @Override
    public Contact getContactDetails(Integer cid, User user) {
        return repository.findByCidAndUser(cid,user);
    }

    @Override
    public Optional<Contact> findById(Integer cid) {
        return repository.findById(cid);
    }

    @Override
    public String deleteContact(Integer cid) {
        repository.deleteById(cid);
        return "Deleted Successfully";
    }
}
