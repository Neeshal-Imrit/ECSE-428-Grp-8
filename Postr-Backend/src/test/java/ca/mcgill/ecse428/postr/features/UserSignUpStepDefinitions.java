package ca.mcgill.ecse428.postr.features;

import ca.mcgill.ecse428.postr.controller.UserController;
import ca.mcgill.ecse428.postr.model.User;
import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.dao.UserRepository;
import ca.mcgill.ecse428.postr.dto.ErrorDTO;
import ca.mcgill.ecse428.postr.dto.UserRequestDTO;
import ca.mcgill.ecse428.postr.service.UserService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.junit.jupiter.api.AfterAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserSignUpStepDefinitions {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @Autowired
private PosterRepository posterRepository;

    @Autowired
    private UserService userService;

    private ResponseEntity<?> controllerResponse;

    @AfterAll
    public void clearDatabase() {
        // First delete posters
        posterRepository.deleteAll();
        // Then delete users
        userRepository.deleteAll();
    }

    @Given("the following users exist in the system for sign-in")
    public void theFollowingUsersExistInTheSystemSignUp(DataTable dataTable) {
        clearDatabase();

        List<Map<String, String>> rows = dataTable.asMaps();
        for (var row : rows) {
            User user = new User();
            user.setEmail(row.get("email"));
            user.setPassword(row.get("password"));
            userRepository.save(user);
        }
    }

    @Given("the user is on the sign-up page")
    public void theUserIsOnTheSignUpPage() {
        // No action needed since this is a UI step
    }

    @When("he enters an email {string} and he enters a password {string}")
    public void heEntersAnEmailAndPassword(String email, String password) {
        UserRequestDTO requestDTO = new UserRequestDTO(email, password);
        controllerResponse = userController.createUser(requestDTO);
    }

    @When("he enters an email {string} that is already registered and he enters a password {string}")
    public void heEntersAnAlreadyRegisteredEmailAndPassword(String email, String password) {
        UserRequestDTO requestDTO = new UserRequestDTO(email, password);
        controllerResponse = userController.createUser(requestDTO);
    }

    @Then("he should see an error message sign up {string}")
    public void heShouldSeeAnErrorMessageSignUp(String expectedMessage) {
        assertNotNull(controllerResponse);
        assertEquals(HttpStatus.BAD_REQUEST, controllerResponse.getStatusCode());
        assertInstanceOf(ErrorDTO.class, controllerResponse.getBody());
        ErrorDTO error = (ErrorDTO) controllerResponse.getBody();
        assertEquals(expectedMessage, error.getError());
    }

    @Then("the following users shall exist in the system")
    public void theFollowingUsersShallExistInTheSystem(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (var row : rows) {
            String email = row.get("email");
            User user = userRepository.findUserByEmail(email);
            assertNotNull(user, "User with email " + email + " does not exist");
            assertEquals(row.get("password"), user.getPassword(), "Password mismatch for user " + email);
        }
    }
}
