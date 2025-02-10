package ca.mcgill.ecse428.postr.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse428.postr.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        userRepository.deleteAll();
    }

    @Test
    public void testUserPersistence() {
        // Create and save a User
        String email = "testuser@gmail.com";
        String password = "securepassword";
        User user = new User(email, password);
        user = userRepository.save(user);

        // Retrieve from database
        User userFromDB = userRepository.findUserById(user.getId());
        
        // Assertions
        assertNotNull(userFromDB);
        assertEquals(user.getId(), userFromDB.getId());
        assertEquals(email, userFromDB.getEmail());
        assertEquals(password, userFromDB.getPassword());
    }
}