package SM.SocialMedia.service;

import SM.SocialMedia.entity.User;
import SM.SocialMedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new IllegalStateException("Username already taken");
        }

        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalStateException("A user with this email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
