package ca.mcgill.ecse428.postr.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse428.postr.dao.UserRepository;
import ca.mcgill.ecse428.postr.dto.ErrorDTO;
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
        ResponseEntity<ErrorDTO> response = client.getForEntity("/login/" + EMAIL + "/" + "wrongpassword", ErrorDTO.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid password", response.getBody().getError());  // This will now match the error message
    }

    @Test
    @Order(5)
    public void testLoginInvalidEmail() {
        ResponseEntity<ErrorDTO> response = client.getForEntity("/login/" + "wrongemail" + "/" + PASSWORD, ErrorDTO.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid email", response.getBody().getError());  // Ensure it matches the error message
    }

    @Test
    @Order(6)
    public void testGetUserById() {
        Long Id = userRepository.findUserByEmail(EMAIL).getId();
        UserResponseDTO userResponseDTO = client.getForObject("/users/id/"+ Id, UserResponseDTO.class);
        assertEquals(EMAIL, userResponseDTO.getEmail());
        assertEquals(PASSWORD, userResponseDTO.getPassword());
    }

}