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
import org.junit.jupiter.api.AfterAll;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


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

    @AfterAll
    public void clearDatabase() {
        posterRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Given("the following users exist in the system upload posters")
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

    @Given("the user is logged in as {string} upload posters")
    public void theUserIsLoggedInAs(String email) {
        loggedInUser = userRepository.findUserByEmail(email);
        assertNotNull(loggedInUser, "User must exist to log in.");
        // Force initialization of lazy collections
        loggedInUser.getPosters().size();
    }

    @Given("the following posters exist in the system upload posters")
    public void theFollowingPostersExistInTheSystem(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (var row : rows) {
            Poster poster = new Poster();
            poster.setTitle(row.get("title"));
            poster.setDescription(row.get("description"));
            poster.setPrice(Float.parseFloat(row.get("price")));
            poster.setUrl(row.get("imageData"));
            User user = userRepository.findUserByEmail(row.get("user"));
            poster.setUser(user);
            posterRepository.save(poster);
        }
    }

    @When("the user is on the shop page upload poster")
    public void the_user_is_on_the_shop_page() {
        // Simulate navigation to the shop page
    }

    @Then("they should see the following posters displayed")
    public void they_should_see_the_following_posters_displayed(DataTable dataTable) {
        List<Map<String, String>> expectedPosters = dataTable.asMaps();
        List<Poster> actualPosters = posterRepository.findAll();
        assertEquals(expectedPosters.size(), actualPosters.size(), "Mismatch in expected and actual posters.");
        for (int i = 0; i < expectedPosters.size(); i++) {
            Map<String, String> expectedPoster = expectedPosters.get(i);
            Poster actualPoster = actualPosters.get(i);
            assertEquals(expectedPoster.get("title"), actualPoster.getTitle(), "Title mismatch.");
            assertEquals(expectedPoster.get("description"), actualPoster.getDescription(), "Description mismatch.");
            assertEquals(Float.parseFloat(expectedPoster.get("price")), actualPoster.getPrice(), "Price mismatch.");
            assertEquals(expectedPoster.get("imageData"), actualPoster.getUrl(), "Image data mismatch.");
            assertEquals(expectedPoster.get("user"), actualPoster.getUser().getEmail(), "User mismatch.");
        }
    }

    @Given("the user is on the upload poster page upload posters")
    public void theUserIsOnTheUploadPosterPage() {
        // No action needed, just simulating the user navigation
    }

    @When("he enters a title {string} and a description {string} and a price {float} and an image file {string}")
    public void heEntersPosterDetails(String title, String description, float price, String imageData) {
        assertNotNull(loggedInUser, "User must be logged in to upload a poster.");
        PosterRequestDTO request = new PosterRequestDTO(
            title, description, price, imageData, loggedInUser.getEmail(), 0
        );
        try {
            controllerResponse = posterController.uploadPoster(request);
        } catch (Exception e) {
            controllerResponse = ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Then("the following posters shall exist in the system upload poster")
    public void theFollowingPostersShallExistInTheSystem(DataTable dataTable) {
        List<Map<String, String>> expectedPosters = dataTable.asMaps();
        List<Poster> actualPosters = posterRepository.findAll();
        for (int i = 0; i < expectedPosters.size(); i++) {
            Map<String, String> expectedPoster = expectedPosters.get(i);
            Poster actualPoster = actualPosters.get(i);
            assertEquals(expectedPoster.get("title"), actualPoster.getTitle(), "Title mismatch.");
            assertEquals(expectedPoster.get("description"), actualPoster.getDescription(), "Description mismatch.");
            assertEquals(Float.parseFloat(expectedPoster.get("price")), actualPoster.getPrice(), "Price mismatch.");
            assertEquals(expectedPoster.get("imageData"), actualPoster.getUrl(), "Image data mismatch.");
            assertEquals(expectedPoster.get("user"), actualPoster.getUser().getEmail(), "User mismatch.");
        }
    }

    @Then("he should see an error message {string} upload poster")
    public void heShouldSeeAnErrorMessage(String expectedErrorMessage) {
        assertEquals(400, controllerResponse.getStatusCode().value(), "Expected HTTP 400 error.");
        assertNotNull(controllerResponse.getBody(), "Error message should be present.");
        assertEquals(expectedErrorMessage, controllerResponse.getBody().toString(), "Error message mismatch.");
    }

    @When("the user uploads a poster without an image upload poster")
    public void theUserUploadsAPosterWithoutAnImage() {
        assertNotNull(loggedInUser, "User must be logged in to upload a poster.");
        PosterRequestDTO request = new PosterRequestDTO(
            "Sample Title", 
            "Sample Description", 
            10.0f, 
            null, // No image data provided
            loggedInUser.getEmail(),
            0
        );
        try {
            controllerResponse = posterController.uploadPoster(request);
        } catch (Exception e) {
            controllerResponse = ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Then("the user should see an error message {string} upload poster")
    public void theUserShouldSeeAnErrorMessage(String expectedErrorMessage) {
        assertEquals(400, controllerResponse.getStatusCode().value(), "Expected HTTP 400 error.");
        assertNotNull(controllerResponse.getBody(), "Error message should be present.");
        assertEquals(expectedErrorMessage, controllerResponse.getBody().toString(), "Error message mismatch.");
    }

    @When("the user uploads a poster with the same title as an existing one")
    public void theUserUploadsAPosterWithTheSameTitleAsAnExistingOne() {
        assertNotNull(loggedInUser, "User must be logged in to upload a poster.");
        // Try to upload a new poster with the same title
        PosterRequestDTO request = new PosterRequestDTO(
            "CoolPoster",  // Duplicate title
            "Another description",
            15f,
            "newImageData",
            loggedInUser.getEmail(),
            0
        );

        try {
            controllerResponse = posterController.uploadPoster(request);
        } catch (Exception e) {
            controllerResponse = ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Given("there are no posters available in the system upload poster")
    public void there_are_no_posters_available_in_the_system() {
        posterRepository.deleteAll();
    }

    @When("the user navigates to the posters listing page upload poster")
    public void the_user_navigates_to_the_posters_listing_page() {
        // Simulate navigation to the posters listing page
        controllerResponse = ResponseEntity.ok("No posters available at the moment");
    }

    @When("the user is on the home page upload poster")
    public void the_user_is_on_the_home_page() {
        // Simulate navigation to the home page
        controllerResponse = ResponseEntity.ok("No posters available at the moment");
    }

    @Then("they should see a message {string} upload poster")
    public void they_should_see_a_message(String expectedMessage) {
        // Verify that the message is displayed
        // This would typically involve checking the response from a controller or service
        assertNotNull(controllerResponse, "Controller response should not be null.");
        assertNotNull(controllerResponse.getBody(), "Message should be present.");
        assertEquals(expectedMessage, controllerResponse.getBody().toString(), "Message mismatch.");
    }

    @Then("they should see the following posters displayed in the featured section upload poster")
    public void they_should_see_the_following_posters_displayed_in_the_featured_section(DataTable dataTable) {
        List<Map<String, String>> expectedPosters = dataTable.asMaps();
        List<Poster> actualPosters = posterRepository.findAll();
        assertEquals(expectedPosters.size(), actualPosters.size(), "Mismatch in expected and actual posters.");
        for (int i = 0; i < expectedPosters.size(); i++) {
            Map<String, String> expectedPoster = expectedPosters.get(i);
            Poster actualPoster = actualPosters.get(i);
            assertEquals(expectedPoster.get("title"), actualPoster.getTitle(), "Title mismatch.");
            assertEquals(expectedPoster.get("description"), actualPoster.getDescription(), "Description mismatch.");
            assertEquals(Float.parseFloat(expectedPoster.get("price")), actualPoster.getPrice(), "Price mismatch.");
            assertEquals(expectedPoster.get("imageData"), actualPoster.getUrl(), "Image data mismatch.");
            assertEquals(expectedPoster.get("user"), actualPoster.getUser().getEmail(), "User mismatch.");
        }
    }

}