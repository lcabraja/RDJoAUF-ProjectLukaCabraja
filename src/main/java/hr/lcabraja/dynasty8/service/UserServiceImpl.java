package hr.lcabraja.dynasty8.service;

import hr.lcabraja.dynasty8.domain.Portfolio;
import hr.lcabraja.dynasty8.domain.Role;
import hr.lcabraja.dynasty8.domain.User;
import hr.lcabraja.dynasty8.repository.PortfolioRepository;
import hr.lcabraja.dynasty8.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PortfolioRepository accountRepository;

    @Override
    public long getCount() {
        return userRepository.count();
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public User getUserById(long userId) {
        return userRepository.getReferenceById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
