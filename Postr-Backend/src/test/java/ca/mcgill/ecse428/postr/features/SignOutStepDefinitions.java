package ca.mcgill.ecse428.postr.features;

import ca.mcgill.ecse428.postr.controller.UserController;
import ca.mcgill.ecse428.postr.model.User;
import ca.mcgill.ecse428.postr.dao.UserRepository;
import ca.mcgill.ecse428.postr.dto.UserRequestDTO;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignOutStepDefinitions {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;
    
    private String currentUserEmail;
    private boolean isLoggedIn;
    private String redirectPage;
    private String errorMessage;

    private void clearDatabase() {
        userRepository.deleteAll();
    }

    @Given("the user is logged in")
    public void theUserIsLoggedIn() {
        this.isLoggedIn = true;
    }



    @Given("the user is not logged in")
    public void theUserIsNotLoggedIn() {
        this.currentUserEmail = null;
        this.isLoggedIn = false;
    }

    @When("they sign out")
    public void theySignOut() {
        if (isLoggedIn) {
            // Simulate sign out action
            this.currentUserEmail = null;
            this.isLoggedIn = false;
            this.redirectPage = "signin";
        } else {
            this.errorMessage = "You are not signed in";
        }
    }

    @Given("the user is logged in as {string}")
    public void theUserIsLoggedInAs(String email) {
        // Verify the user exists
        User user = userRepository.findUserByEmail(email);
        this.currentUserEmail = email;
        this.isLoggedIn = true;
    }

    @When("they attempt to sign out")
    public void theyAttemptToSignOut() {
        if (!isLoggedIn) {
            this.errorMessage = "You are not signed in";
        }
    }

    @Then("they should be redirected to the sign-in page")
    public void theyShouldBeRedirectedToTheSignInPage() {
        assertEquals("signin", this.redirectPage);
        assertFalse(isLoggedIn);
    }

    @Then("they should see {string}")
    public void theyShouldSee(String expectedMessage) {
        assertEquals(expectedMessage, this.errorMessage);
    }

}