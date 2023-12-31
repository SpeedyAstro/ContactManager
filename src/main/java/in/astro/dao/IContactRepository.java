package in.astro.dao;

import in.astro.model.Contact;
import in.astro.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IContactRepository extends JpaRepository<Contact,Integer> {
    // current page + content per page
    public Page<Contact> findByUserId(Integer id, Pageable pageable);

    public Contact findByCidAndUser(Integer id, User user);
    public List<Contact> findByNameContainingAndUser(String name, User user);
}
