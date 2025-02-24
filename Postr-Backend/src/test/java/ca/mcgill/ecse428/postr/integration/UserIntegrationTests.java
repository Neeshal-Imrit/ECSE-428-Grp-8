package ca.mcgill.ecse428.postr.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import ca.mcgill.ecse428.postr.dao.UserRepository;

import ca.mcgill.ecse428.postr.dto.UserRequestDTO;
import ca.mcgill.ecse428.postr.dto.UserResponseDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserIntegrationTests {
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private UserRepository userRepository;

    // Attributes for user
    private static final String EMAIL = "bob@gmail.com";
    private static final String PASSWORD = "passwordstrong";

    @AfterAll
    public void clearDatabase() {
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateUser() {
        UserRequestDTO userRequestDTO = new UserRequestDTO(EMAIL, PASSWORD);
        UserResponseDTO userResponseDTO = client.postForObject("/users", userRequestDTO, UserResponseDTO.class);
        assertEquals(EMAIL, userResponseDTO.getEmail());
        assertEquals(PASSWORD, userResponseDTO.getPassword());
    }

    @Test
    @Order(2)
    public void testGetUserByEmail() {
        UserResponseDTO userResponseDTO = client.getForObject("/users/" + EMAIL, UserResponseDTO.class);
        assertEquals(EMAIL, userResponseDTO.getEmail());
        assertEquals(PASSWORD, userResponseDTO.getPassword());
    }

    @Test
    @Order(3)
    public void testLogin() {
        boolean response = client.getForObject("/login/" + EMAIL + "/" + PASSWORD, Boolean.class);
        assertEquals(true, response);
    }

    @Test
    @Order(4)
    public void testLoginInvalidPassword() {
        boolean response = client.getForObject("/login/" + EMAIL + "/" + "wrongpassword", Boolean.class);
        assertEquals(false, response);
    }

    @Test
    @Order(5)
    public void testLoginInvalidEmail() {
        boolean response = client.getForObject("/login/" + "wrongemail" + "/" + PASSWORD, Boolean.class);
        assertEquals(false, response);
    }


    // Add your test methods here
}