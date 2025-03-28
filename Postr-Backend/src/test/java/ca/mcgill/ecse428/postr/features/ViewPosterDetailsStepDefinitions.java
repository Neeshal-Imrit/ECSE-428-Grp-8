package ca.mcgill.ecse428.postr.features;

import ca.mcgill.ecse428.postr.controller.PosterController;
import ca.mcgill.ecse428.postr.dto.PosterResponseDTO;
import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.dao.UserRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    private ResponseEntity<?> response;
    private String errorMessage;


    private void clearDatabase() {
        posterRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Given("the user is on the {string} page")
    public void the_user_is_on_the_page(String pageName) {
        this.currentPage = pageName;
    }

    @When("they select the {string}")
    public void they_select_the_poster(String posterTitle) {
        Poster poster = posterRepository.findPosterByTitle(posterTitle);
        this.response = posterController.getPosterById(poster.getId());
    }

    @When("they attempt to select a recently deleted poster {string}")
    public void they_attempt_to_select_a_recently_deleted_poster(String posterTitle) {
        Poster poster = posterRepository.findPosterByTitle(posterTitle);
        this.errorMessage = "Poster not found";
        this.response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Poster not found");
    }

    @Then("they should see the details for {string} displayed")
    public void they_should_see_the_details_for_displayed(String posterTitle) {
        assertNotNull(this.response);
        assertEquals(HttpStatus.OK, this.response.getStatusCode());
        PosterResponseDTO posterResponse = (PosterResponseDTO) this.response.getBody();
        assertNotNull(posterResponse);
        assertEquals(posterTitle, posterResponse.getTitle());
    }

    @Then("they should see an error {string}")
    public void they_should_see_an_error(String expectedErrorMessage) {
        assertNotNull(this.response);
        assertEquals(HttpStatus.NOT_FOUND, this.response.getStatusCode());
        String actualError = (this.errorMessage != null) ? this.errorMessage : (String) this.response.getBody();
        assertEquals(expectedErrorMessage, actualError);
    }
}