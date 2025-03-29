package ca.mcgill.ecse428.postr.features;

import ca.mcgill.ecse428.postr.controller.LikeController;
import ca.mcgill.ecse428.postr.controller.UserController;
import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.dao.UserRepository;
import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.model.User;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LikePosterStepDefinitions {

    @Autowired
    private PosterRepository posterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeController likeController;

    @Autowired
    private UserController userController;

    private ResponseEntity<?> controllerResponse;
    private User loggedInUser;

    private void clearDatabase() {
        posterRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Given("the following users exist in the system like poster")
    public void theFollowingUsersExistInTheSystemLike(DataTable dataTable) {
        clearDatabase();
        List<Map<String, String>> rows = dataTable.asMaps();
        for (var row : rows) {
            User user = new User();
            user.setEmail(row.get("email"));
            user.setPassword(row.get("password"));
            userRepository.save(user);
        }
    }

    @Given("the following posters exist in the system like poster")
    public void theFollowingPostersExistInTheSystemForLiking(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (var row : rows) {
            Poster poster = new Poster();
            poster.setTitle(row.get("title"));
            poster.setNumLikes(Integer.parseInt(row.get("likes")));
            posterRepository.save(poster);
        }
    }

    @Given("the user is logged in as {string} for liking posters")
    public void theUserIsLoggedInAsForLikingPosters(String email) {
        loggedInUser = userRepository.findUserByEmail(email);
        assertNotNull(loggedInUser, "User must exist to log in.");
    }

    @Given("the user is logged in as {string} before liking posters")
    public void theUserIsLoggedInBeforeLikingPosters(String email) {
        loggedInUser = userRepository.findUserByEmail(email);
        assertNotNull(loggedInUser, "User must exist to log in.");
    }

    @Given("the user is not logged in to like posters")
    public void theUserIsNotLoggedInToLikePosters() {
        loggedInUser = null; // Ensure no user is logged in
    }

    @Given("they already liked the {string}")
    public void theyAlreadyLikedThePoster(String posterTitle) {
        assertNotNull(loggedInUser, "User must be logged in before liking a poster.");
        Poster poster = posterRepository.findPosterByTitle(posterTitle);
        assertNotNull(poster, "Poster must exist.");
        likeController.likePoster(loggedInUser.getId(), poster.getId());
    }

    @When("they like the {string}")
    public void theyLikeThePoster(String posterTitle) {
        Poster poster = posterRepository.findPosterByTitle(posterTitle);
        assertNotNull(poster, "Poster must exist.");
        controllerResponse = likeController.likePoster(loggedInUser.getId(), poster.getId());
    }

    @When("they attempt to like {string}")
    public void theyAttemptToLikePoster(String posterTitle) {
        Poster poster = posterRepository.findPosterByTitle(posterTitle);
        if (loggedInUser == null) {
            controllerResponse = ResponseEntity.badRequest().body("Please sign in to like posters");
        } else if (poster != null && poster.getUser() != null && poster.getUser().getId().equals(loggedInUser.getId())) {
            controllerResponse = ResponseEntity.badRequest().body("You cannot like your own poster");
        } else {
            controllerResponse = likeController.likePoster(loggedInUser.getId(), poster.getId());
        }
    }

    @Then("the {string} should have {int} likes")
    public void thePosterShouldHaveLikes(String posterTitle, int expectedLikes) {
        Poster poster = posterRepository.findPosterByTitle(posterTitle);
        assertNotNull(poster, "Poster should exist.");
        assertEquals(expectedLikes, poster.getNumLikes(), "Like count mismatch.");
    }

    @Then("they should see an error message {string}")
    public void theyShouldSeeAnErrorMessage(String expectedMessage) {
        assertNotNull(controllerResponse, "Response should not be null.");
        assertEquals(400, controllerResponse.getStatusCode().value(), "Expected HTTP 400 error.");
        Object responseBody = controllerResponse.getBody();
        assertNotNull(responseBody, "Response body should not be null.");
        assertEquals(expectedMessage, responseBody.toString(), "Error message mismatch.");
    }
}
