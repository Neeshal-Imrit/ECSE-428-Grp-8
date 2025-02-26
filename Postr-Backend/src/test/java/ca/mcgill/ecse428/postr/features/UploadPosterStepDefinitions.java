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
            try {
                poster.setImageData(Base64.getDecoder().decode(row.get("imageData")));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid base64 image data for poster: " + row.get("title"), e);
            }
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

    @When("the user uploads a poster without an image")
    public void theUserUploadsAPosterWithoutAnImage() {
        assertNotNull(loggedInUser, "User must be logged in to upload a poster.");
        PosterRequestDTO request = new PosterRequestDTO(
            "Sample Title", 
            "Sample Description", 
            10.0f, 
            null, // No image data provided
            loggedInUser.getEmail()
        );
        try {
            controllerResponse = posterController.uploadPoster(request);
        } catch (Exception e) {
            controllerResponse = ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Then("the user should see an error message {string}")
    public void theUserShouldSeeAnErrorMessage(String expectedErrorMessage) {
        assertEquals(400, controllerResponse.getStatusCode().value(), "Expected HTTP 400 error.");
        assertNotNull(controllerResponse.getBody(), "Error message should be present.");
        assertEquals(expectedErrorMessage, controllerResponse.getBody().toString(), "Error message mismatch.");
    }


    @When("the user uploads a poster with the same title as an existing one")
    public void theUserUploadsAPosterWithTheSameTitleAsAnExistingOne() {
        assertNotNull(loggedInUser, "User must be logged in to upload a poster.");
        // First, create and save a poster with a unique title
        Poster existingPoster = new Poster();
        existingPoster.setTitle("Existing Title");
        existingPoster.setDescription("Existing description");
        existingPoster.setPrice(10.0f);
        existingPoster.setImageData(Base64.getDecoder().decode("iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC"));
        existingPoster.setUser(loggedInUser);
        posterRepository.save(existingPoster);

        // Try to upload a new poster with the same title
        PosterRequestDTO request = new PosterRequestDTO(
            "Existing Title",  // Duplicate title
            "Another description",
            15.0f,
            Base64.getDecoder().decode("newImageData"),
            loggedInUser.getEmail()
        );

        try {
            controllerResponse = posterController.uploadPoster(request);
        } catch (Exception e) {
            controllerResponse = ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Given("there are no posters available in the system")
    public void there_are_no_posters_available_in_the_system() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("the user is on the home page")
    public void the_user_is_on_the_home_page() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("they should see a message {string}")
    public void they_should_see_a_message(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("they should see the following posters displayed in the featured section")
    public void they_should_see_the_following_posters_displayed_in_the_featured_section(io.cucumber.datatable.DataTable dataTable) {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
        // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
        // Double, Byte, Short, Long, BigInteger or BigDecimal.
        //
        // For other transformations you can register a DataTableType.
        throw new io.cucumber.java.PendingException();
    }



}