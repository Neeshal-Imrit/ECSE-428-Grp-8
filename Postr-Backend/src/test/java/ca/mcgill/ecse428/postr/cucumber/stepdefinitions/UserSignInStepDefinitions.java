package ca.mcgill.ecse428.postr.cucumber.stepdefinitions;

import ca.mcgill.ecse428.postr.service.UserService;
import ca.mcgill.ecse428.postr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.java.en.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class UserSignInStepDefinitions {

    @Autowired
    private UserService userService;

    private String email;
    private String password;
    private String errorMessage;
    private boolean redirectedToHomePage;

    @Given("the following users exist in the system")
    public void theFollowingUsersExistInTheSystem(io.cucumber.datatable.DataTable dataTable) {
        // Convert the DataTable into a list of lists
        List<List<String>> users = dataTable.asLists(String.class);

        // Loop through each row in the list
        users.forEach(row -> {
            String email = row.get(0);  // Get the email (first column)
            String password = row.get(1);  // Get the password (second column)

            try {
                userService.createUser(email, password);  // Create user using service
            } catch (IllegalArgumentException e) {
                // Handle exceptions (e.g., user already exists)
            }
        });
    }

    @Given("the user is on the sign-in page")
    public void theUserIsOnTheSignInPage() {
        // Simulate that the user is on the sign-in page
        // This could be a page object model or a simple flag in this case
    }

    @When("he enters a valid email {string} and password {string}")
    public void heEntersAValidEmailAndPassword(String email, String password) {
        this.email = email;
        this.password = password;
        try {
            this.redirectedToHomePage = userService.logIn(email, password);
        } catch (IllegalArgumentException e) {
            this.errorMessage = e.getMessage();
        }
    }

    @When("he enters a valid email {string}")
    public void heEntersAValidEmail(String email) {
        this.email = email;
    }

    @When("an incorrect password {string}")
    public void anIncorrectPassword(String password) {
        this.password = password;
        try {
            this.redirectedToHomePage = userService.logIn(email, password);
        } catch (IllegalArgumentException e) {
            this.errorMessage = e.getMessage();
        }
    }

    @Then("he should be redirected to the home page")
    public void heShouldBeRedirectedToTheHomePage() {
        assertTrue(redirectedToHomePage, "User was not redirected to the home page");
    }

    @Then("he should see an error message {string}")
    public void heShouldSeeAnErrorMessage(String expectedErrorMessage) {
        assertEquals(expectedErrorMessage, errorMessage, "Error message is not as expected");
    }
}
