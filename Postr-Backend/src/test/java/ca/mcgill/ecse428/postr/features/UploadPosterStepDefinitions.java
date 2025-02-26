package ca.mcgill.ecse428.postr.features;

import ca.mcgill.ecse428.postr.controller.PosterController;
import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.dao.UserRepository;
import ca.mcgill.ecse428.postr.dto.PosterRequestDTO;
import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.model.User;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UploadPosterStepDefinitions {

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
    @Given("the following users exist in the system")
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

    @Given("the user is logged in as {string}")
    public void theUserIsLoggedInAs(String email) {
    loggedInUser = userRepository.findUserByEmail(email);
    assertNotNull(loggedInUser, "User must exist to log in.");
    
    // Force initialization of lazy collections
    loggedInUser.getPosters().size();
    }


    @Given("the following posters exist in the system")
    public void theFollowingPostersExistInTheSystem(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (var row : rows) {
            Poster poster = new Poster();
            poster.setTitle(row.get("title"));
            poster.setDescription(row.get("description"));
            poster.setPrice(Float.parseFloat(row.get("price")));
            poster.setImageData(Base64.getDecoder().decode(row.get("imageData")));
            User user = userRepository.findUserByEmail(row.get("user"));
            assertNotNull(user, "Poster must have a valid user.");
            poster.setUser(user);
            posterRepository.save(poster);
        }
    }


    @Given("the user is on the upload poster page")
    public void theUserIsOnTheUploadPosterPage() {
        // No action needed, just simulating the user navigation
    }

    @When("he enters a title {string} and a description {string} and a price {float} and an image file {string}")
    public void heEntersPosterDetails(String title, String description, float price, String imageData) {
        assertNotNull(loggedInUser, "User must be logged in to upload a poster.");
        PosterRequestDTO request = new PosterRequestDTO(
            title, description, price, Base64.getDecoder().decode(imageData), loggedInUser.getEmail()
        );
        try {
            controllerResponse = posterController.uploadPoster(request);
        } catch (Exception e) {
            controllerResponse = ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Transactional
    @Then("the following posters shall exist in the system")
    public void theFollowingPostersShallExistInTheSystem(DataTable dataTable) {
        List<Map<String, String>> expectedPosters = dataTable.asMaps();
        List<Poster> actualPosters = posterRepository.findAll();
        assertEquals(expectedPosters.size(), actualPosters.size(), "Mismatch in expected and actual posters.");
    }

    @Then("he should see an error message {string}")
    public void heShouldSeeAnErrorMessage(String expectedErrorMessage) {
        assertEquals(400, controllerResponse.getStatusCode().value(), "Expected HTTP 400 error.");
        assertNotNull(controllerResponse.getBody(), "Error message should be present.");
        assertEquals(expectedErrorMessage, controllerResponse.getBody().toString(), "Error message mismatch.");
    }
}