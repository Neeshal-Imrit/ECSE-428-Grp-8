package ca.mcgill.ecse428.postr.service;

import ca.mcgill.ecse428.postr.dao.UserRepository;
import ca.mcgill.ecse428.postr.dto.UserDTO;
import ca.mcgill.ecse428.postr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create a new user
    public User createUser(User user) {
        return userRepository.save(user);  // Save user and return the saved instance
    }

    // Retrieve a user by their ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);  // Fetch user from the repository
    }

    // Update an existing user
    public User updateUser(User user) {
        return userRepository.save(user);  // Save the updated user to the repository
    }

    // Delete a user
    public void deleteUser(User user) {
        userRepository.delete(user);  // Delete the user from the repository
    }

    // Convert User model to UserDTO
    public UserDTO convertToDTO(User user) {
        if (user == null) {
            return null;  // Return null if the user is null
        }
        return new UserDTO(user.getId(), user.getEmail(), user.getPassword());
    }
}
