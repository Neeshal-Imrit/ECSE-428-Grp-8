package ca.mcgill.ecse428.postr.features;

import ca.mcgill.ecse428.postr.controller.UserController;
import ca.mcgill.ecse428.postr.model.User;
import ca.mcgill.ecse428.postr.dao.UserRepository;
import ca.mcgill.ecse428.postr.dto.ErrorDTO;
import ca.mcgill.ecse428.postr.service.UserService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserSignInStepDefinitions {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    private ResponseEntity<?> controllerResponse;

    private void clearDatabase() {
        userRepository.deleteAll();
    }

    @Given("the following users exist in the system for sign up")
    public void theFollowingUsersExistInTheSystem(DataTable dataTable) {
        clearDatabase();

        List<Map<String, String>> rows = dataTable.asMaps();
        for (var row : rows) {
            User user = new User();
            user.setEmail(row.get("email"));
            user.setPassword(row.get("password"));
            userRepository.save(user);
        }
    }

    @Given("the user is on the sign-in page")
    public void theUserIsOnTheSignInPage() {
        // Simulate the user being on the sign-in page (no action needed for this case)
    }

    @When("he enters an email {string} and password {string}")
    public void heEntersAValidEmailAndPassword(String email, String password) {
        controllerResponse = userController.login(email, password);  // Use the class-level variable
    }

    @Then("he should be redirected to the home page")
    public void heShouldBeRedirectedToTheHomePage() {
        assertEquals(200, controllerResponse.getStatusCode().value());
        assertNotNull(controllerResponse.getBody());// Assuming the body contains a message or redirection path
    }

    @When("he enters an email {string} and an invalid password {string}")
    public void heEntersanInvalidPassword(String email, String password) {
        controllerResponse = userController.login(email, password);  // Use the class-level variable
    }

    @Then("he should see an error message sign in {string}")
    public void heShouldSeeAnErrorMessageSignIn(String expectedErrorMessage) {
        assertEquals(400, controllerResponse.getStatusCode().value()); // Assuming a 400 status for invalid login
        assertEquals(expectedErrorMessage, ((ErrorDTO) controllerResponse.getBody()).getError());
    }

    @When("he enters an invalid email {string} and a password {string}")
    public void heEntersAnInvalidEmail(String email, String password) {
        controllerResponse = userController.login(email, password);  // Use the class-level variable
    }

    @Then("he should see an error message {string} for invalid email")
    public void heShouldSeeAnErrorMessageForInvalidEmail(String expectedErrorMessage) {
        assertEquals(400, controllerResponse.getStatusCode().value()); // Assuming a 400 status for invalid login
        assertEquals(expectedErrorMessage, controllerResponse.getBody());
    }
}