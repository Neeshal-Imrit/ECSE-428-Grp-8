package ca.mcgill.ecse428.postr.features;

import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.model.User;
import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.dao.UserRepository;
import ca.mcgill.ecse428.postr.service.PosterService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShowBestSellingLeaderboardStepDefinitions {
    @Autowired
    private PosterRepository posterRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PosterService posterService;
    
    private String currentPage;
    private List<Poster> leaderboardPosters;
    private Map<String, Poster> createdPosters = new HashMap<>();
    
    @Before
    public void setup() {
        // Clean up data before each scenario
        cleanup();
    }
    
    @After
    public void cleanup() {
        // Clean up all created entities after each scenario
        try {
            createdPosters.values().forEach(poster -> posterRepository.delete(poster));
            createdPosters.clear();
        } catch (Exception e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }
    
    @Given("the following posters have sales data in the system")
    public void the_following_posters_have_sales_data_in_the_system(DataTable dataTable) {
        // Clear existing data first to avoid accumulation across scenarios
        cleanup();
        
        List<Map<String, String>> postersData = dataTable.asMaps();

        // Create mock user
        User buyUser = new User();
        buyUser.setEmail("buy@example.com");
        buyUser.setPassword("password");
        buyUser = userRepository.save(buyUser);
                
        // Create posters and add sales based on the table data
        for (Map<String, String> posterData : postersData) {
            String title = posterData.get("title");
            String salesCountStr = posterData.get("salesCount");
            
            // Create a poster without setting the user yet
            Poster poster = new Poster();
            poster.setTitle(title);
            poster = posterRepository.save(poster);
            
            // Store the poster for later reference
            createdPosters.put(title, poster);
            
            // Add poster if specified
            if (salesCountStr != null && !salesCountStr.isEmpty()) {
                int salesCount = Integer.parseInt(salesCountStr);
                // Add sales
                for (int i = 0; i < salesCount; i++) {
                    poster.addPurchase();
                    posterRepository.save(poster); // Save poster with sales count
                }
            }
        }
    }    

    @Given("the user is on the Leaderboard page")
    public void the_user_is_on_the_page() {
        navigateToPage("Leaderboard");
    }
    
    @Given("there are no sales data in the system")
    public void there_are_no_sales_data_in_the_system() {
        // Delete all existing posters
        posterRepository.deleteAll();
    }
    
    private void navigateToPage(String pageName) {
        currentPage = pageName;
        
        // Skip API call and directly use the service
        if (pageName.equals("Popular Posters") || pageName.equals("Leaderboard")) {
            try {
                leaderboardPosters = posterService.getMostPurchasedPosters();
                
                // Debug info
                System.out.println("Retrieved " + leaderboardPosters.size() + " posters from service");
                for (Poster poster : leaderboardPosters) {
                    String title = poster != null ? poster.getTitle() : "unknown";
                    System.out.println("  - " + title + " with " + poster.getNumPurchases() + " purchases");
                }
            } catch (Exception e) {
                System.err.println("Error retrieving leaderboard: " + e.getMessage());
                e.printStackTrace();
                // In case of error, set to empty list rather than null
                leaderboardPosters = new ArrayList<>();
            }
        }
    }
    
    @Then("they should see the posters sorted by salesCount in descending order")
    public void they_should_see_the_posters_sorted_by_salesCount_in_descending_order(DataTable dataTable) {
        List<Map<String, String>> expectedData = dataTable.asMaps();
        
        // Verify we have the correct number of posters
        assertEquals(expectedData.size(), leaderboardPosters.size(), 
                "Number of posters in leaderboard does not match expected");
        
        // Verify the order and like counts
        for (int i = 0; i < expectedData.size(); i++) {
            Map<String, String> expectedPoster = expectedData.get(i);
            Poster actualPoster = leaderboardPosters.get(i);
            
            // Get the expected values
            String expectedTitle = expectedPoster.get("title");
            int expectedSalesCount = Integer.parseInt(expectedPoster.get("salesCount"));
            
            // Get the actual values - need to lookup title from the poster repository
            Poster actualPosterEntity = posterRepository.findById(actualPoster.getId()).orElse(null);
            assertNotNull(actualPosterEntity, "Poster not found in repository: " + actualPoster.getId());
            
            String actualTitle = actualPosterEntity.getTitle();
            int actualSalesCount = actualPosterEntity.getNumPurchases();
            
            // Verify the title and like count match
            assertEquals(expectedTitle, actualTitle, "Poster title does not match");
            assertEquals(expectedSalesCount, actualSalesCount, "Like count does not match");
        }
    }   

    @Then("they should see a message {string} leaderboard")
    public void they_should_see_a_message(String message) {
        if (message.equals("No leaderboard data available")) {
            assertTrue(leaderboardPosters == null || leaderboardPosters.isEmpty(),
                    "Expected no posters in leaderboard");
        }
    }
}
