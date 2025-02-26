package ca.mcgill.ecse428.postr.features;

import ca.mcgill.ecse428.postr.controller.PosterController;
import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.dto.PosterRequestDTO;
import ca.mcgill.ecse428.postr.dto.PosterResponseDTO;
import ca.mcgill.ecse428.postr.model.Poster;
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
public class UploadPosterStepDefinitions {

    @Autowired
    private PosterRepository posterRepository;

    @Autowired
    private PosterController posterController;

    private ResponseEntity<?> controllerResponse;

    private void clearDatabase() {
        posterRepository.deleteAll();
    }

    @Given("the following posters exist in the system")
    public void theFollowingPostersExistInTheSystem(DataTable dataTable) {
        clearDatabase();
        List<Map<String, String>> rows = dataTable.asMaps();
        for (var row : rows) {
            Poster poster = new Poster();
            poster.setTitle(row.get("title"));
            poster.setDescription(row.get("description"));
            poster.setPrice(Float.parseFloat(row.get("price")));
            poster.setImageData(row.get("imageData"));
            posterRepository.save(poster);
        }
    }

    @Given("the user is on the upload poster page")
    public void theUserIsOnTheUploadPosterPage() {
        // No action needed, just simulating the user navigation
    }

    @When("he enters a title {string} and a description {string} and a price {float} and an image file {string}")
    public void heEntersPosterDetails(String title, String description, float price, String imageData) {
        PosterRequestDTO request = new PosterRequestDTO(title,description,price,imageData,);
        request.setTitle(title);
        request.setDescription(description);
        request.setPrice(price);
        request.setImageData(imageData);
        controllerResponse = posterController.uploadPoster(request);
    }

    @Then("the following posters shall exist in the system")
    public void theFollowingPostersShallExistInTheSystem(DataTable dataTable) {
        List<Map<String, String>> expectedPosters = dataTable.asMaps();
        List<Poster> actualPosters = posterRepository.findAll();
        assertEquals(expectedPosters.size(), actualPosters.size());
    }

    @Then("he should see an error message {string}")
    public void heShouldSeeAnErrorMessage(String expectedErrorMessage) {
        assertEquals(400, controllerResponse.getStatusCode().value());
        assertNotNull(controllerResponse.getBody());
        assertEquals(expectedErrorMessage, controllerResponse.getBody().toString());
    }
}