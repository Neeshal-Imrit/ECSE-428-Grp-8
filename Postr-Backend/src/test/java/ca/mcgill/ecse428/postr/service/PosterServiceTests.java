package ca.mcgill.ecse428.postr.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.dao.UserRepository;
import ca.mcgill.ecse428.postr.exception.PostrException;
import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.model.User;
import org.springframework.http.HttpStatus;

@SpringBootTest  // Ensures Spring Boot test context
@ExtendWith(MockitoExtension.class) // Enables Mockito extension
class PosterServiceTest {

    @Mock
    private PosterRepository posterRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PosterService posterService;

    private User mockUser;
    private Poster mockPoster;

    @BeforeEach
    void setUp() {
        mockUser = new User("test@example.com", "password");
        mockPoster = new Poster();
        mockPoster.setTitle("Test Poster");
        mockPoster.setDescription("Test Description");
        mockPoster.setPrice(10.0f);
        mockPoster.setImageData(new byte[]{1, 2, 3});
        mockPoster.setUser(mockUser);
    }

    @Test
    void testFindAll() {
        when(posterRepository.findAll()).thenReturn(List.of(mockPoster));

        List<Poster> posters = posterService.findAll();

        assertEquals(1, posters.size());
        assertEquals("Test Poster", posters.get(0).getTitle());
    }

    @Test
    void testFindPosterById() {
        when(posterRepository.findPosterById(1L)).thenReturn(mockPoster);
 
        
        Poster poster = posterService.findPosterById(1L);
        
        assertNotNull(poster);
        assertEquals("Test Poster", poster.getTitle());
    }

    @Test
    void testFindByUserId() {
        when(posterRepository.findByUserId(1L)).thenReturn(List.of(mockPoster));

        List<Poster> posters = posterService.findByUserId(1L);

        assertFalse(posters.isEmpty());
    }

    @Test
    void testFindByUserEmail() {
        when(posterRepository.findByUserEmail("test@example.com")).thenReturn(List.of(mockPoster));

        List<Poster> posters = posterService.findByUserEmail("test@example.com");

        assertEquals(1, posters.size());
    }

    @Test
    void testFindByTitle() {
        when(posterRepository.findPosterByTitle("Test Poster")).thenReturn(mockPoster);

        Poster poster = posterService.findByTitle("Test Poster");

        assertEquals("Test Poster", poster.getTitle());
    }

    @Test
    void testUploadPoster_Success() {
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(mockUser);
        when(posterRepository.findPosterByTitle("New Poster")).thenReturn(null);
        when(posterRepository.save(any(Poster.class))).thenReturn(mockPoster);

        Poster poster = posterService.uploadPoster("test@example.com", new byte[]{1, 2, 3}, 15.0f, "Nice poster", "New Poster");

        assertNotNull(poster);
        assertEquals("New Poster", poster.getTitle());
    }

    @Test
    void testUploadPoster_FailsWhenUserNotFound() {
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(null);

        PostrException ex = assertThrows(PostrException.class, () -> {
            posterService.uploadPoster("test@example.com", new byte[]{1, 2, 3}, 15.0f, "Nice poster", "New Poster");
        });

        assertEquals("The user's email cannot be found", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    void testUploadPoster_FailsWhenTitleExists() {
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(mockUser);
        when(posterRepository.findPosterByTitle("Test Poster")).thenReturn(mockPoster);

        PostrException ex = assertThrows(PostrException.class, () -> {
            posterService.uploadPoster("test@example.com", new byte[]{1, 2, 3}, 15.0f, "Nice poster", "Test Poster");
        });

        assertEquals("A poster with the same title already exists", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    @Test
    void testUploadPoster_FailsWhenPriceNegative() {
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(mockUser);

        PostrException ex = assertThrows(PostrException.class, () -> {
            posterService.uploadPoster("test@example.com", new byte[]{1, 2, 3}, -5.0f, "Nice poster", "New Poster");
        });

        assertEquals("The poster's price cannot be negative", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }
}
