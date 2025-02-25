package ca.mcgill.ecse428.postr.features;

import ca.mcgill.ecse428.postr.service.UserService;
import ca.mcgill.ecse428.postr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.java.en.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class UserSignInStepDefinitions {

    @Autowired
    private UserService userService;  // This will be injected by Spring

    private String email;
    private String password;
    private String errorMessage;
    private boolean redirectedToHomePage;

    @Given("the following users exist in the system")
    public void theFollowingUsersExistInTheSystem(io.cucumber.datatable.DataTable dataTable) {
        // Logic to create users
    }

    @Given("the user is on the sign-in page")
    public void theUserIsOnTheSignInPage() {
        // Simulate user being on the page
    }

    @When("he enters a valid email {string} and password {string}")
    public void heEntersAValidEmailAndPassword(String email, String password) {
        // Logic to perform sign-in
    }

    @Then("he should be redirected to the home page")
    public void heShouldBeRedirectedToTheHomePage() {
        // Assertion for home page redirection
    }

    @Then("he should see an error message {string}")
    public void heShouldSeeAnErrorMessage(String expectedErrorMessage) {
        // Assertion for error message
    }
}
