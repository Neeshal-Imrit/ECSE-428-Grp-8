package ca.mcgill.ecse428.postr.features;

import ca.mcgill.ecse428.postr.controller.PosterController;
import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.dao.UserRepository;
import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.dto.PosterResponseDTO;
import ca.mcgill.ecse428.postr.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ViewAllPostersStepDefinitions {

    @Autowired
    private PosterRepository posterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PosterController posterController;

    private ResponseEntity<?> controllerResponse;
    private List<PosterResponseDTO> posters;

    private void clearDatabase() {
        posterRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Given("the following users exist in the system view all posters")
    public void theFollowingUsersExistInTheSystem(DataTable dataTable) {
        clearDatabase();
        List<Map<String, String>> rows = dataTable.asMaps();
        for (Map<String, String> row : rows) {
            User user = new User();
            user.setEmail(row.get("email"));
            user.setPassword(row.get("password"));
            userRepository.save(user);
        }
    }

    @Given("the user is logged in as {string} view all posters")
    public void theUserIsLoggedInAs(String email) {
        System.out.println("User logged in as: " + email);
    }

    @Given("the following posters exist in the system view all posters")
    public void theFollowingPostersExistInTheSystem(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (Map<String, String> row : rows) {
            User user = userRepository.findUserByEmail(row.get("user"));
            Poster poster = new Poster();
            poster.setTitle(row.get("title"));
            poster.setDescription(row.get("description"));
            poster.setPrice(Float.parseFloat(row.get("price")));
            poster.setImageData(row.get("imageData").getBytes());
            poster.setUser(user);
            posterRepository.save(poster);
        }
    }

    @Given("there are no posters available in the system view all posters")
    public void thereAreNoPostersAvailableInTheSystem() {
        clearDatabase();
    }

    @Given("the user is on the shop page view all posters")
    public void theUserIsOnTheShopPage() {
        controllerResponse = ResponseEntity.ok(posterController.getAllPosters());
    }

    @When("the user navigates to the posters listing page")
    public void theUserNavigatesToThePostersListingPage() {
        controllerResponse = ResponseEntity.ok(posterController.getAllPosters());
    }

    @Then("they should see the following posters displayed view all posters")
    public void theyShouldSeeTheFollowingPostersDisplayed(DataTable dataTable) {
        if (controllerResponse.getStatusCode() == HttpStatus.OK) {
            posters = (List<PosterResponseDTO>) controllerResponse.getBody();
            List<Map<String, String>> expectedPosters = dataTable.asMaps();
            for (Map<String, String> expectedPoster : expectedPosters) {
                boolean found = posters.stream().anyMatch(p ->
                        p.getTitle().equals(expectedPoster.get("title").trim()) &&
                                p.getDescription().equals(expectedPoster.get("description").trim()) &&
                                p.getPrice() == Float.parseFloat(expectedPoster.get("price").trim())
                );
                assertTrue(found, "Expected poster not found: " + expectedPoster.get("title"));
            }
        } else {
            fail("Failed to retrieve posters.");
        }
    }

    @Then("they should see a message {string} view all posters")
    public void theyShouldSeeAMessage(String expectedMessage) {
        if (controllerResponse.getStatusCode() == HttpStatus.OK && posters == null) {
            assertEquals("No posters available at the moment", expectedMessage);
        } else {
            fail("Expected no posters, but some were found.");
        }
    }
}
