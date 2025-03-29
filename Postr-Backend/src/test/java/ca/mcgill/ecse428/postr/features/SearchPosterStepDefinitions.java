package ca.mcgill.ecse428.postr.features;

import ca.mcgill.ecse428.postr.controller.PosterController;
import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.model.User;
import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.dao.UserRepository;
import ca.mcgill.ecse428.postr.dto.ErrorDTO;
import ca.mcgill.ecse428.postr.dto.PosterResponseDTO;
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

public class SearchPosterStepDefinitions {

    @Autowired
    private PosterRepository posterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PosterController posterController;

    private ResponseEntity<?> controllerResponse;
    private User loggedInUser;

    private void clearDatabase() {
        posterRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Given("the following user exist in the system search filter")
    public void theFollowingUsersExistInTheSystemUpdatePoster(DataTable dataTable) {
        clearDatabase();
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            User user = new User();
            user.setEmail(row.get("email"));
            user.setPassword(row.get("password"));
            userRepository.save(user);
        }
    }

    @Given("the following posters exist in the system search filter")
    public void theFollowingPostersExistInTheSystemUpdatePoster(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            Poster poster = new Poster();
            poster.setTitle(row.get("title"));
            poster.setPrice(Float.parseFloat(row.get("price")));
            String ownerEmail = row.get("user");
            assertNotNull(ownerEmail, "Poster owner email is missing.");
            User owner = userRepository.findUserByEmail(ownerEmail);
            assertNotNull(owner, "Owner user must exist in the database.");
            poster.setUser(owner);
            poster.setCategory(row.get("category"));
            posterRepository.save(poster);
        }
    }

    @Given("the user is on the \"Shop Posters\" page search filter")
    public void theUserIsOnTheShopPostersPage() {
        if (loggedInUser == null) {
            loggedInUser = userRepository.findUserByEmail("jeff@ap.com");
        }
        assertNotNull(loggedInUser, "Logged in user must not be null.");
    }

    @When("they filter by \"Digital Art\" search filter")
    public void theyFilterByDigitalArt() {
        controllerResponse = posterController.getPostersByCategory("Digital Art");
    }

    @Then("they should see the following posters displayed search filter")
    public void theyShouldSeeTheFollowingPostersDisplayed(DataTable dataTable) {
        List<Map<String, String>> expectedPosters = dataTable.asMaps(String.class, String.class);
        List<PosterResponseDTO> actualPosters = (List<PosterResponseDTO>) controllerResponse.getBody();

        assertEquals(expectedPosters.size(), actualPosters.size(), "Number of posters displayed does not match.");

        for (int i = 0; i < expectedPosters.size(); i++) {
            Map<String, String> expectedPoster = expectedPosters.get(i);
            PosterResponseDTO actualPoster = actualPosters.get(i);

            assertEquals(expectedPoster.get("title"), actualPoster.getTitle(), "Poster title does not match.");
        }
    }

    @When("they filter by price range \"1.00\" to \"2.50\" search filter")
    public void theyFilterByPriceRange() {
        controllerResponse = posterController.getPostersByPriceRange(1.00, 2.50);
    }

    @When("they filter by price range \"10.00\" to \"20.00\" search filter")
    public void theyFilterByPriceRangeError() {
        controllerResponse = posterController.getPostersByPriceRange(10.00, 20.00);
    }

    @Then("they should see a message {string} search filter")
    public void theyShouldSeeAMessageNoPostersMatchTheSelectedCriteria(String expectedMessage) {
        assertNotNull(controllerResponse, "Response should not be null.");
        assertNotNull(controllerResponse.getBody(), "Response body should not be null.");
        System.out.println("The response body of search "+ controllerResponse.getBody());
        ErrorDTO errorDTO = (ErrorDTO) controllerResponse.getBody();
        assertEquals(expectedMessage, errorDTO.getError(), "Error message mismatch.");
    }
}
