package ca.mcgill.ecse428.postr.features;

import ca.mcgill.ecse428.postr.controller.PosterController;
import ca.mcgill.ecse428.postr.dto.ErrorDTO;
import ca.mcgill.ecse428.postr.dto.PosterRequestDTO;
import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.model.User;
import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.dao.UserRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpdatePosterStepDefinitions {

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

    @Given("the following users exist in the system update poster")
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

    @Given("the following posters exist in the system update poster")
    public void theFollowingPostersExistInTheSystemUpdatePoster(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            Poster poster = new Poster();
            poster.setTitle(row.get("title"));
            poster.setDescription(row.get("description"));
            poster.setPrice(Float.parseFloat(row.get("price")));
            poster.setUrl(row.get("imageData"));
            String ownerEmail = row.get("user");
            assertNotNull(ownerEmail, "Poster owner email is missing.");
            User owner = userRepository.findUserByEmail(ownerEmail);
            assertNotNull(owner, "Owner user must exist in the database.");
            poster.setUser(owner);
            posterRepository.save(poster);
        }
    }

    @Given("the artist is logged in as {string} update poster")
    public void theArtistIsLoggedInAsUpdatePoster(String email) {
        loggedInUser = userRepository.findUserByEmail(email);
        if (loggedInUser == null) {
            User user = new User();
            user.setEmail(email);
            user.setPassword("dummy");
            userRepository.save(user);
            loggedInUser = user;
        }
    }
    
    @Given("^the\\s+user\\s+is\\s+logged\\s+in\\s+as\\s+\"([^\"]+)\"\\s+update\\s+poster$")
    public void theUserIsLoggedInAsUpdatePoster(String email) {
        loggedInUser = userRepository.findUserByEmail(email);
        if (loggedInUser == null) {
            User user = new User();
            user.setEmail(email);
            user.setPassword("dummy");
            userRepository.save(user);
            loggedInUser = user;
        }
    }


    @Given("^the\\s+user\\s+is\\s+on\\s+the\\s+update\\s+poster\\s+page$")
    public void theUserIsOnTheUpdatePosterPage() {
        if (loggedInUser == null) {
            loggedInUser = userRepository.findUserByEmail("jeff@ap.com");
        }
        assertNotNull(loggedInUser, "Logged in user must not be null.");
        Poster poster = findPosterByUser(loggedInUser);
        assertNotNull(poster, "Logged in user must have a poster to update.");
    }

    @When("they update the {string} description to {string} update poster")
    public void theyUpdateTheDescriptionToUpdatePoster(String posterTitle, String newDescription) {
        Poster poster = posterRepository.findPosterByTitle(posterTitle);
        assertNotNull(poster, "Poster with title " + posterTitle + " must exist.");
        PosterRequestDTO request = new PosterRequestDTO(
                poster.getTitle(),
                newDescription,
                poster.getPrice(),
                poster.getUrl(),
                poster.getUser().getEmail(),
                poster.getNumPurchases()
        );
        controllerResponse = posterController.updatePoster(poster.getId(), request);
    }

    @When("he enters a title {string} and a description {string} and a price {float} and an image file {string} update poster")
    @When("he  enters a title {string} and a description {string} and a price {float} and an image file {string}")
    @When("he enters a  title {string} and a description {string} and a price {float} and an image file {string}")
    public void heEntersUpdateDetails(String title, String description, float price, String imageFile) {
        Poster poster = findPosterByUser(loggedInUser);
        if (poster == null) {
            poster = posterRepository.findPosterByTitle("CoolPoster");
        }
        assertNotNull(poster, "Poster must exist for the logged in user.");
        PosterRequestDTO request = new PosterRequestDTO(
                title,
                description,
                price,
                imageFile,
                poster.getUser().getEmail(),
                poster.getNumPurchases()
        );
        controllerResponse = posterController.updatePoster(poster.getId(), request);
    }

    @When("the user updates a poster without an image")
    public void theUserUpdatesAPosterWithoutAnImage() {
        Poster poster = findPosterByUser(loggedInUser);
        if (poster == null) {
            poster = posterRepository.findPosterByTitle("CoolPoster");
        }
        assertNotNull(poster, "Poster must exist for the logged in user.");
        PosterRequestDTO request = new PosterRequestDTO(
                poster.getTitle(),
                poster.getDescription(),
                poster.getPrice(),
                "",
                poster.getUser().getEmail(),
                poster.getNumPurchases()
        );
        controllerResponse = posterController.updatePoster(poster.getId(), request);
    }

    @When("the user updates a poster with the same title as an existing one")
    public void theUserUpdatesAPosterWithTheSameTitleAsAnExistingOne() {
        Poster poster = findPosterByUser(loggedInUser);
        if (poster == null) {
            poster = posterRepository.findPosterByTitle("CoolPoster");
        }
        assertNotNull(poster, "Poster must exist for the logged in user.");
        PosterRequestDTO request = new PosterRequestDTO(
                "VeryCoolPoster",
                poster.getDescription(),
                poster.getPrice(),
                poster.getUrl(),
                poster.getUser().getEmail(),
                poster.getNumPurchases()
        );
        controllerResponse = posterController.updatePoster(poster.getId(), request);
    }

    @Then("^(?:he|the user)\\s+shall\\s+see\\s+an\\s+error\\s+message\\s+\"([^\"]+)\"$")
    public void theErrorMessage(String expectedMessage) {
        assertNotNull(controllerResponse, "Response should not be null.");
        Object body = controllerResponse.getBody();
        assertNotNull(body, "Response body should not be null.");
        if (body instanceof ErrorDTO) {
            ErrorDTO error = (ErrorDTO) body;
            assertEquals(expectedMessage, error.getError());
        } else {
            fail("Expected an error message but got a successful response.");
        }
    }

    @Then("^the\\s+following\\s+posters\\s+shall\\s+exist\\s+in\\s+the\\s+system\\s+update\\s+poster$")
    public void theFollowingPostersShallExistInTheSystemUpdatePoster(DataTable dataTable) {
        List<Map<String, String>> expectedRows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : expectedRows) {
            String expectedTitle = row.get("title");
            Poster poster = posterRepository.findPosterByTitle(expectedTitle);
            assertNotNull(poster, "Poster with title " + expectedTitle + " not found.");
            assertEquals(row.get("description"), poster.getDescription());
            float expectedPrice = Float.parseFloat(row.get("price"));
            assertEquals(expectedPrice, poster.getPrice(), 0.01);
            assertEquals(row.get("imageData"), poster.getUrl());
            assertEquals(row.get("user"), poster.getUser().getEmail());
        }
    }
    
    private Poster findPosterByUser(User user) {
        List<Poster> posters = posterRepository.findByUserEmail(user.getEmail());
        return (posters == null || posters.isEmpty()) ? null : posters.get(0);
    }
}
