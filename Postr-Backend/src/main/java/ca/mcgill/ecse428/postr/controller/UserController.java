package ca.mcgill.ecse428.postr.controller;

import ca.mcgill.ecse428.postr.dto.UserDTO;
import ca.mcgill.ecse428.postr.model.User;
import ca.mcgill.ecse428.postr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint to create a new user
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User newUser = new User(userDTO.getEmail(), userDTO.getPassword());
        User savedUser = userService.createUser(newUser);  // Use the service to create user
        UserDTO savedUserDTO = userService.convertToDTO(savedUser);  // Convert user model to DTO
        return new ResponseEntity<>(savedUserDTO, HttpStatus.CREATED);  // Return created status
    }

    // Endpoint to get user details by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> userOpt = userService.getUserById(id);  // Fetch user by ID
        if (userOpt.isPresent()) {
            UserDTO userDTO = userService.convertToDTO(userOpt.get());  // Convert user to DTO
            return new ResponseEntity<>(userDTO, HttpStatus.OK);  // Return the user in DTO format
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if user not found
    }

    // Endpoint to update user details
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        Optional<User> existingUserOpt = userService.getUserById(id);  // Check if user exists
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setEmail(userDTO.getEmail());  // Update user data
            existingUser.setPassword(userDTO.getPassword());
            User updatedUser = userService.updateUser(existingUser);  // Save updated user
            UserDTO updatedUserDTO = userService.convertToDTO(updatedUser);  // Convert to DTO
            return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);  // Return updated user DTO
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if user not found
    }

    // Endpoint to delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> existingUserOpt = userService.getUserById(id);  // Check if user exists
        if (existingUserOpt.isPresent()) {
            userService.deleteUser(existingUserOpt.get());  // Delete user from service
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Return 204 (no content)
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if user not found
    }
}
