package in.astro.dao;

import in.astro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Integer> {
    public Optional<User> findByEmail(String email);
}
