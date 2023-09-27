package in.astro.dao;

import in.astro.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IContactRepository extends JpaRepository<Contact,Integer> {
}
