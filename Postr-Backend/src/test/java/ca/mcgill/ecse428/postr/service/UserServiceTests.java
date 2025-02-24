package ca.mcgill.ecse428.postr.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse428.postr.dao.UserRepository;
import ca.mcgill.ecse428.postr.service.UserService;
import ca.mcgill.ecse428.postr.model.User;


@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    private static final String EMAIL = "bob@gmail.com";
    private static final String PASSWORD = "password";

    @Test
    public void testCreateUser() {
        when(userRepository.save(any())).thenReturn(new User(EMAIL, PASSWORD));
        User user = userService.createUser(EMAIL, PASSWORD);
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPassword());
    }

    @Test
    public void testCreateUserInvalidPassword() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(EMAIL, "1234567");
        });
    }

    @Test
    public void testCreateUserInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser("bob", PASSWORD);
        });
    }

    @Test
    public void testGetUserByEmail() {
        when(userRepository.findUserByEmail(EMAIL)).thenReturn(new User(EMAIL, PASSWORD));
        User user = userService.getUserByEmail(EMAIL);
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPassword());
    }

    @Test
    public void testLogIn() {
        when(userRepository.findUserByEmail(EMAIL)).thenReturn(new User(EMAIL, PASSWORD));
        assertTrue(userService.logIn(EMAIL, PASSWORD));
    }

    @Test
    public void testLogInInvalidEmail() {
        when(userRepository.findUserByEmail(EMAIL)).thenReturn(null);
        assertFalse(userService.logIn(EMAIL, PASSWORD));
    }
    
}
