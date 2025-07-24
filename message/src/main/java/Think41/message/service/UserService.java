package Think41.message.service;

import Think41.message.exception.exceptions.DuplicateUserException;
import Think41.message.model.User;
import Think41.message.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User addUser(String name) {
        if (userRepository.existsByName(name)) {
            throw new DuplicateUserException("User already exists: " + name);
        }
        return userRepository.save(new User(name));
    }
}