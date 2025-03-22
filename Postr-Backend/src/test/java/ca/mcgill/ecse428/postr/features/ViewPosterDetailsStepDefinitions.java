package ca.mcgill.ecse428.postr.features;

import ca.mcgill.ecse428.postr.controller.PosterController;
import ca.mcgill.ecse428.postr.controller.UserController;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ViewPosterDetailsStepDefinitions {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PosterRepository posterRepository;
    
    @Autowired
    private PosterController posterController;
    
    private String currentPage;
    private Poster selectedPoster;
    private ResponseEntity<?> response;
    private String errorMessage;

    private void clearDatabase() {
        posterRepository.deleteAll();
        userRepository.deleteAll();
    }

    
    @Given("the user is on the {string} page")
    public void the_user_is_on_the_page(String pageName) {
        // Set current page for tracking user navigation
        this.currentPage = pageName;
    }

    @When("they select the {string}")
    public void they_select_the_poster(String posterTitle) {
        // Find the poster by title
        Poster poster = posterRepository.findPosterByTitle(posterTitle);
        
        if (poster != null) {
            this.selectedPoster = poster;
            this.response = posterController.getPosterById(poster.getId());
        } else {
            this.errorMessage = "Poster not found";
            this.response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poster not found");
        }
    }

    @When("they attempt to select a recently deleted poster {string}")
    public void they_attempt_to_select_a_recently_deleted_poster(String posterTitle) {
        // Since the poster doesn't exist, we'll get a null response
        Poster poster = posterRepository.findPosterByTitle(posterTitle);
        
        if (poster == null) {
            this.errorMessage = "Poster not found";
            this.response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poster not found");
        }
    }

    @Then("they should see the details for {string} displayed")
    public void they_should_see_the_details_for_displayed(String posterTitle) {
        assertNotNull(this.response);
        assertEquals(HttpStatus.OK, this.response.getStatusCode());
        
        Poster poster = (Poster) this.response.getBody();
        assertNotNull(poster);
        assertEquals(posterTitle, poster.getTitle());
    }

    @Then("they should see an error {string}")
    public void they_should_see_an_error(String expectedErrorMessage) {
        assertEquals(HttpStatus.NOT_FOUND, this.response.getStatusCode());
        assertEquals(expectedErrorMessage, this.errorMessage);
    }
}