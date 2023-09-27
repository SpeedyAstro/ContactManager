package in.astro.service;

import in.astro.dao.IUserRepository;
import in.astro.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private IUserRepository repository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Override
    public String saveUser(User user) {
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        return "User Registered Successfully with id :"+repository.save(user).getId();
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> optional = repository.findByEmail(email);
        return optional.get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = repository.findByEmail(username);
        if (optional.isEmpty()){
            throw new IllegalArgumentException("User not found");
        }else {
            User user = optional.get();
            org.springframework.security.core.userdetails.User user1 = new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),
                    List.of(new SimpleGrantedAuthority(user.getRole())));
            return user1;
        }
    }


}
