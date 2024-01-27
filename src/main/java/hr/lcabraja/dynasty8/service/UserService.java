package hr.lcabraja.dynasty8.service;

import hr.lcabraja.dynasty8.domain.Portfolio;
import hr.lcabraja.dynasty8.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    long getCount();
    Optional<User> findUserByUsername(String username);
    User createUser(User user);

    User updateUser(User user);
    boolean deleteUser(Long userId);

    User getUserById(long userId);

    List<User> getAllUsers();

}
