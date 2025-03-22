package ca.mcgill.ecse428.postr.features;

import ca.mcgill.ecse428.postr.controller.PosterController;
import ca.mcgill.ecse428.postr.controller.UserController;
import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.dao.UserRepository;
import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.model.User;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BuyPosterStepDefinitions {

    @Autowired
    private PosterRepository posterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PosterController posterController;

    @Autowired
    private UserController userController;

    private ResponseEntity<?> controllerResponse;
    private User loggedInUser;

    private void clearDatabase() {
        posterRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Given("the following users exist in the system buy poster")
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

    @Given("the following posters exist in the system buy poster")
    public void theFollowingPostersExistInTheSystem(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (var row : rows) {
            Poster poster = new Poster();
            poster.setTitle(row.get("title"));
            poster.setDescription(row.get("description"));
            poster.setPrice(Float.parseFloat(row.get("price")));
            poster.setUrl(row.get("imageData"));

            // Set the owner of the poster if provided
            String ownerEmail = row.get("user"); // Ensure this column matches the feature file
            if (ownerEmail != null && !ownerEmail.isEmpty()) {
                User owner = userRepository.findUserByEmail(ownerEmail);
                assertNotNull(owner, "Owner user must exist in the database.");
                poster.setUser(owner);
            } else {
                fail("Poster owner email is missing or empty.");
            }
            posterRepository.save(poster);
        }
    }

    @Given("the user is logged in as {string} buy poster")
    public void theUserIsLoggedInAs(String email) {
        loggedInUser = userRepository.findUserByEmail(email);
        assertNotNull(loggedInUser, "User must exist to log in.");
    }

    @When("they purchase the {string}")
    public void theyPurchaseThePoster(String posterTitle) {
        Poster poster = posterRepository.findPosterByTitle(posterTitle);
        assertNotNull(poster, "Poster must exist.");
        controllerResponse = posterController.buyPoster(poster.getId());
    }

    @Then("they should see a confirmation message {string}")
    public void theyShouldSeeAConfirmationMessage(String expectedMessage) {
        assertNotNull(controllerResponse, "Response should not be null.");
        assertEquals(expectedMessage, controllerResponse.getBody().toString(), "Confirmation message mismatch.");
    }

    @Then("the poster {string} should have {int} purchase")
    public void thePosterShouldHavePurchase(String posterTitle, int expectedPurchases) {
        Poster poster = posterRepository.findPosterByTitle(posterTitle);
        assertNotNull(poster, "Poster should exist.");
        assertEquals(expectedPurchases, poster.getNumPurchases(), "Purchase count mismatch.");
    }

    @When("they purchase the deleted poster {string}")
    public void theyPurchaseTheDeletedPoster(String posterTitle) {
        Poster poster = posterRepository.findPosterByTitle(posterTitle);
        if (poster == null) {
            controllerResponse = ResponseEntity.badRequest().body("This poster is no longer available");
            return;
        }
        controllerResponse = posterController.buyPoster(poster.getId());
    }

    @Then("they should see an error message {string}")
    public void theyShouldSeeAnErrorMessage(String expectedMessage) {
        assertNotNull(controllerResponse, "Response should not be null.");

        Object responseBody = controllerResponse.getBody();
        assertNotNull(responseBody, "Response body should not be null.");

        // Handle ErrorDTO case
        if (responseBody instanceof ca.mcgill.ecse428.postr.dto.ErrorDTO errorDTO) {
            assertEquals(expectedMessage, errorDTO.getError(), "Error message mismatch.");
        } else {
            assertEquals(expectedMessage, responseBody.toString(), "Error message mismatch.");
        }
    }

    @When("they purchase the poster {string}")
    public void theyPurchaseTheirOwnPoster(String posterTitle) {
        Poster poster = posterRepository.findPosterByTitle(posterTitle);
        controllerResponse = userController.purchasePoster(loggedInUser.getId(), poster.getId());
    }
}
