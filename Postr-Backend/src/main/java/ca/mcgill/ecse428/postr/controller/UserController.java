
package ca.mcgill.ecse428.postr.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse428.postr.service.UserService;

import ca.mcgill.ecse428.postr.dto.UserResponseDTO;
import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.dto.ErrorDTO;
import ca.mcgill.ecse428.postr.dto.PosterListDTO;
import ca.mcgill.ecse428.postr.dto.PosterResponseDTO;
import ca.mcgill.ecse428.postr.dto.UserRequestDTO;



@RestController
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;


    @GetMapping("/users/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        try {
            return new ResponseEntity<>(new UserResponseDTO(userService.getUserByEmail(email)), HttpStatus.OK);
        } catch (Exception e) {
            throw new IllegalArgumentException("User not found");
        }
        
    } 

    @GetMapping("/users/id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(new UserResponseDTO(userService.getUserById(id)), HttpStatus.OK);
        } catch (Exception e) {
            throw new IllegalArgumentException("User not found");
        }

    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO userRequestDTO) {

        try {
            return new ResponseEntity<>(new UserResponseDTO(userService.createUser(userRequestDTO.getEmail(), userRequestDTO.getPassword())), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/login/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
        try {
            Long response = userService.logIn(email, password);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/{userId}/purchase/{posterId}")
    public ResponseEntity<?> purchasePoster(@PathVariable Long userId, @PathVariable Long posterId) {
        try {
            userService.purchasePoster(userId, posterId);
            return new ResponseEntity<>("Poster purchased successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/{userId}/purchases")
    public ResponseEntity<?> getPurchasedPosters(@PathVariable Long userId) {
        try{
            List<PosterResponseDTO> posters = new ArrayList<>();
            for (Poster poster : userService.getPurchasedPostersByUser(userId)) {
                posters.add(new PosterResponseDTO(poster));
            } return new ResponseEntity<>(new PosterListDTO(posters), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new ErrorDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
}
