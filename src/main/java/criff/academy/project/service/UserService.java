package criff.academy.project.service;

import criff.academy.project.model.User;
import criff.academy.project.repository.UserRepository;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Logger;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(User user) {
        logger.info("Tentativo di salvare l'utente: {}", user.getUsername());
        if (emailExist(user.getUsername())) {
            logger.warn("Utente già esistente: {}", user.getUsername());
            throw new RuntimeException("Esiste già un account con quel nome utente: " + user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        logger.info("Utente salvato con successo: {}", savedUser.getUsername());
        return savedUser;
    }

    private boolean emailExist(String email) {
        Optional<User> user = userRepository.findByUsername(email);
        return user.isPresent();
    }
}
