package hu.mywebshop.service;

import hu.mywebshop.entity.User;
import hu.mywebshop.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        // Jelszó titkosítása
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public void setOnlyPrepaid(Long userId, boolean onlyPrepaid) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setOnlyPrepaid(onlyPrepaid);
        userRepository.save(user);
    }

    // Egyéb felhasználói logika: pl. adatok módosítása
}
