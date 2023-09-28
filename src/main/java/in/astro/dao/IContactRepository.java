package in.astro.dao;

import in.astro.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IContactRepository extends JpaRepository<Contact,Integer> {
    public List<Contact> findByUserId(Integer id);
}
