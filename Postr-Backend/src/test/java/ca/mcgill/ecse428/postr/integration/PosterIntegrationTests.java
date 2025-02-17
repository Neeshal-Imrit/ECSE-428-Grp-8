package ca.mcgill.ecse428.postr.integration;

import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.model.User;
import ca.mcgill.ecse428.postr.service.PosterService;
import ca.mcgill.ecse428.postr.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional  // Ensures database changes are rolled back after each test
public class PosterIntegrationTests {

    @Autowired
    private PosterService posterService;

    @Autowired
    private UserService userService;

    private User testUser;
    private Poster testPoster;

    @BeforeEach
    public void setup() {
        // Create and save a test user before each test
        testUser = new User("test@example.com", "password123");
        userService.saveUser(testUser);  // Assuming you have a UserService to handle user operations

        // Create a poster associated with the test user
        testPoster = new Poster(1L, "http://test-url.com", testUser);
    }

    @Test
    public void testSavePoster() {
        // Save the poster
        Poster savedPoster = posterService.savePoster(testPoster);

        // Verify the poster is saved with the correct attributes
        assertNotNull(savedPoster.getId());
        assertEquals("http://test-url.com", savedPoster.getUrl());
        assertEquals(testUser.getId(), savedPoster.getUser().getId());
    }

    @Test
    public void testFindPosterById() {
        // Save the poster first
        posterService.savePoster(testPoster);

        // Now find the poster by its ID
        Poster foundPoster = posterService.findPosterById(testPoster.getId());

        // Verify the found poster has the correct properties
        assertNotNull(foundPoster);
        assertEquals(testPoster.getId(), foundPoster.getId());
        assertEquals(testPoster.getUrl(), foundPoster.getUrl());
    }

    @Test
    public void testFindPosterByUserId() {
        // Save the poster first
        posterService.savePoster(testPoster);

        // Find all posters for the test user
        List<Poster> posters = posterService.findByUserId(testUser.getId());

        // Verify the user has one poster
        assertEquals(1, posters.size());
        assertEquals(testPoster.getId(), posters.get(0).getId());
    }

    @Test
    public void testDeletePoster() {
        // Save the poster first
        Poster savedPoster = posterService.savePoster(testPoster);

        // Delete the poster by its ID
        posterService.deletePoster(savedPoster.getId());

        // Try to find the deleted poster (should return null)
        Poster deletedPoster = posterService.findPosterById(savedPoster.getId());
        assertNull(deletedPoster);
    }

    @Test
    public void testDeleteNonExistentPoster() {
        // Try deleting a non-existent poster (ID 999L for example)
        posterService.deletePoster(999L);

        // Nothing should happen, so no exceptions should be thrown
        assertDoesNotThrow(() -> posterService.deletePoster(999L));
    }
}
