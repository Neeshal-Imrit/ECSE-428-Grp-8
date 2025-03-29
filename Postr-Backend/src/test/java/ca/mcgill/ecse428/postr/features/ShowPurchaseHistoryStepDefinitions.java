package ca.mcgill.ecse428.postr.features;

import ca.mcgill.ecse428.postr.controller.PosterController;
import ca.mcgill.ecse428.postr.controller.UserController;
import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.dao.UserRepository;
import ca.mcgill.ecse428.postr.dto.ErrorDTO;
import ca.mcgill.ecse428.postr.dto.PosterListDTO;
import ca.mcgill.ecse428.postr.dto.PosterResponseDTO;
import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.model.User;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShowPurchaseHistoryStepDefinitions {

    @Autowired
    private PosterRepository posterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    private User currentUser;
    private ResponseEntity<?> response;

    private void clearDatabase() {
        posterRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Given("the following users exist in the system purchase history")
    public void theFollowingUsersExistInTheSystem(DataTable dataTable) {
        clearDatabase();
        List<Map<String, String>> rows = dataTable.asMaps();
        for (Map<String, String> row : rows) {
            User user = new User();
            user.setEmail(row.get("email"));
            user.setPassword(row.get("password"));
            userRepository.save(user);
        }
    }

    @Given("the following purchases exists purchase history")
    public void theFollowingPurchasesExistInTheSystem(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (Map<String, String> row : rows) {
            Poster poster = new Poster();
            poster.setTitle(row.get("poster"));
            poster.setDescription("");
            poster.setPrice(Float.parseFloat(row.get("price")));
            poster.setUrl(null);
            String ownerEmail = row.get("email");
            if (ownerEmail != null && !ownerEmail.isEmpty()) {
                User owner = userRepository.findUserByEmail(ownerEmail);
                assertNotNull(owner);
                poster.setUser(owner);
            }
            posterRepository.save(poster);
        }
    }

    @Given("the  user is logged in as {string} purchase history")
    public void the_user_is_logged_in_as_purchase_history(String email) {
        currentUser = userRepository.findUserByEmail(email);
        assertNotNull(currentUser);
    }

    @Given("the user is logged in as {string} purchase history")
    public void the_user_is_logged_in_as_purchase_history_single(String email) {
        currentUser = userRepository.findUserByEmail(email);
        assertNotNull(currentUser);
    }

    @When("they  view their purchase history")
    public void they_view_their_purchase_history() {
        response = userController.getPurchasedPosters(currentUser.getId());
    }
    
    @When("they view their purchase history")
    public void they_view_their_purchase_history_single() {
        response = userController.getPurchasedPosters(currentUser.getId());
    }

    @Then("they should see the following entries")
    public void they_should_see_the_following_entries(DataTable dataTable) {
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());

        PosterListDTO posterListDTO = (PosterListDTO) response.getBody();
        List<PosterResponseDTO> purchasedPosters = posterListDTO.getPosters();

        Map<String, PosterResponseDTO> actualMap = purchasedPosters.stream()
                .collect(Collectors.toMap(PosterResponseDTO::getTitle, p -> p));

        List<Map<String, String>> expectedRows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> expected : expectedRows) {
            String expectedTitle = expected.get("poster");
            assertTrue(actualMap.containsKey(expectedTitle));
            PosterResponseDTO actual = actualMap.get(expectedTitle);
            String actualPrice = String.format("%.2f", actual.getPrice());
            assertEquals(expected.get("price"), actualPrice);
        }
        assertEquals(expectedRows.size(), purchasedPosters.size());
    }

    @Then("they  should see a message {string}")
    public void they_should_see_a_message(String expectedMessage) {
        assertNotNull(response);
        if (response.getStatusCode().value() == 200) {
            PosterListDTO posterListDTO = (PosterListDTO) response.getBody();
            if (posterListDTO.getPosters().isEmpty()) {
                assertEquals(expectedMessage, "You have not made any purchases");
            }
        } else {
            ErrorDTO errorDTO = (ErrorDTO) response.getBody();
            assertEquals(expectedMessage, errorDTO.getError());
        }
    }
}
