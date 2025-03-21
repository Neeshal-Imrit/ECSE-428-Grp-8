package ca.mcgill.ecse428.postr.service;
import ca.mcgill.ecse428.postr.dao.UserRepository;
import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.exception.PostrException;
import jakarta.transaction.Transactional;
import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * This is the service class for the User entity. 
 * 
 * It is used to interact with the database and perform CRUD operations on the User entity.
 *
 * @author Neeshal
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PosterRepository posterRepository;


    @Transactional
    public User createUser(String email, String password) {
        // Validate if the password is strong enough
        if (password.length() < 8) {
            throw new PostrException(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters long");
        }

        // Validate if the email is valid
        if (!email.contains("@")) {
            throw new PostrException(HttpStatus.BAD_REQUEST, "Invalid email");
        }

        // Validate if the email is already in use
        if (userRepository.findUserByEmail(email) != null) {
            throw new PostrException(HttpStatus.BAD_REQUEST, "Email already in use");
        }

        User foundUser = userRepository.findUserByEmail(email);
        if (foundUser != null){
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User(email, password);
        return userRepository.save(user);
    }

    @Transactional
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Transactional
    public boolean logIn(String email, String password) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Invalid email");
        }
        // ONLY ADDED this
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        return user.getPassword().equals(password);
    }

    @Transactional
    public User getUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Transactional
    public void purchasePoster(Long userId, Long posterId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Invalid user id");
        }
        Poster boughtPoster = posterRepository.findPosterById(posterId);
        if (boughtPoster == null) {
            throw new IllegalArgumentException("Invalid poster id");
        }
        user.addPosterPurchase(boughtPoster);

    }

}
