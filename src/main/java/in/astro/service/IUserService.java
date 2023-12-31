package in.astro.service;

import in.astro.helper.Message;
import in.astro.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    public String saveUser(User user);
    public User getUserByEmail(String email);
    public String updateUser(User user);
    public Message changePassword(String username, String old_password, String new_password);
}
