package in.astro.dao;

import in.astro.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IContactRepository extends JpaRepository<Contact,Integer> {
    // current page + content per page
    public Page<Contact> findByUserId(Integer id, Pageable pageable);
}
