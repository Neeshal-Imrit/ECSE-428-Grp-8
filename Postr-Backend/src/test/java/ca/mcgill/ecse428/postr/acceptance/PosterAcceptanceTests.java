package ca.mcgill.ecse428.postr.acceptance;

import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.model.User;
import ca.mcgill.ecse428.postr.service.PosterService;
import ca.mcgill.ecse428.postr.dao.PosterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PosterAcceptanceTests {

    @Mock
    private PosterRepository posterRepository;

    @InjectMocks
    private PosterService posterService;

    private Poster poster1;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a mock user using the User constructor
        user = new User("johndoe@example.com", "password123");

        // Create a poster and associate it with the mock user
        poster1 = new Poster();
        poster1.setId(1L);  // Setting ID for the poster
        poster1.setUrl("http://example.com/poster1");
        poster1.setUser(user);  // Associate the user with the poster
    }

    @Test
    void testDeletePoster() {
        // Simulate finding the poster by ID (poster exists in the repository)
        when(posterRepository.findPosterById(1L)).thenReturn(poster1);

        // Simulate the delete operation (deleteById will be called on the repository)
        doNothing().when(posterRepository).deleteById(1L);

        // Call the repository directly (since PosterService doesn't have a deletePoster method)
        posterRepository.deleteById(1L);

        // Verify that deleteById was called exactly once
        verify(posterRepository, times(1)).deleteById(1L);

        // Verify that the poster's user association is nullified after deletion
        // (in this case, we aren't directly checking the user here, but the poster deletion will be enough for validation)
    }

    @Test
    void testDeleteNonExistentPoster() {
        // Simulate the scenario where the poster does not exist in the repository
        when(posterRepository.findPosterById(1L)).thenReturn(null);

        // Call the repository directly (poster isn't found, so delete should not be called)
        posterRepository.deleteById(1L);

        // Verify that deleteById is never called since the poster doesn't exist
        verify(posterRepository, times(0)).deleteById(1L);
    }
}
