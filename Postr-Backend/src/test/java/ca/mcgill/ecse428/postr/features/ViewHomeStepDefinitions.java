package ca.mcgill.ecse428.postr.features;

import ca.mcgill.ecse428.postr.controller.PosterController;
import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.dao.UserRepository;
import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.dto.PosterResponseDTO;
import ca.mcgill.ecse428.postr.model.User;
import ca.mcgill.ecse428.postr.service.PosterService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ViewHomeStepDefinitions {

    @Autowired
    private PosterRepository posterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PosterController posterController;

    @Autowired
    private PosterService posterService;

    private ResponseEntity<?> controllerResponse;
    private List<PosterResponseDTO> posters;

    private void clearDatabase() {
        posterRepository.deleteAll();
    }

    @Given("the following users exist in the system")
    public void theFollowingUsersExistInTheSystem(DataTable dataTable) {
        // No action needed since this scenario only involves viewing posters.
    }

    @Given("the user is logged in as {string}")
    public void theUserIsLoggedInAs(String email) {
        System.out.println("User logged in: " + email);
    }


    @Given("the following posters exist in the system")
    public void theFollowingPostersExistInTheSystem(DataTable dataTable) {
        clearDatabase();
        User aUser = new User();
        aUser.setEmail("jeff@ap.com");
        aUser.setPassword("llol");
        aUser.posters = new ArrayList<>();
        userRepository.save(aUser);
        List<Map<String, String>> rows = dataTable.asMaps();
        for (var row : rows) {
            Poster poster = new Poster();
            poster.setTitle(row.get("title"));
            poster.setDescription(row.get("description"));
            poster.setPrice(Float.parseFloat(row.get("price")));
            poster.setImageData(row.get("imageData").getBytes());
            poster.setUser(aUser);
            posterRepository.save(poster);
        }

    }

    @Given("there are no posters available in the system")
    public void thereAreNoPostersAvailableInTheSystem() {
        clearDatabase();
    }

    @Given("the user is on the home page view")
    public void theUserFetchesThePostersFromTheAPI() {

        controllerResponse = ResponseEntity.ok(posterController.getAllPosters());
        if (controllerResponse.getStatusCode() == HttpStatus.OK) {
            posters = (List<PosterResponseDTO>) controllerResponse.getBody();
        } else {
            posters = List.of();
        }

    }

    @When("the user is on the home page")
    public void userOnTheHomePage() {
        //do nothing
    }

    @Then("they should see the following posters displayed in the featured section")
    public void theyShouldSeeTheFollowingPostersDisplayedInTheFeaturedSection(DataTable dataTable) {

        List<Map<String, String>> expectedPosters = dataTable.asMaps();

        for (Map<String, String> expectedPoster : expectedPosters) {
            boolean found = posters.stream().anyMatch(p ->
                    p.getTitle().equals(expectedPoster.get("title").trim()) &&
                            p.getDescription().equals(expectedPoster.get("description").trim()) &&
                            p.getPrice() == Float.parseFloat(expectedPoster.get("price").trim())
                            //&& p.getImageData() == (expectedPoster.get("imageData").getBytes())
            );
            assertTrue(found, "Expected poster not found: " + expectedPoster.get("title"));
        }
    }

    @Then("they should see a message {string}")
    public void theyShouldSeeAMessage(String expectedMessage) {
        if (posters == null) {
            assertEquals("No posters available at the moment", expectedMessage);
        } else {
            fail("Expected no posters, but some were found.");
        }
    }
}
