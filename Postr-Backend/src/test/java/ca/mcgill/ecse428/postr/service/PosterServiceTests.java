package ca.mcgill.ecse428.postr.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

@ExtendWith(MockitoExtension.class)
public class PosterServiceTests {

    @Mock
    private PosterRepository posterRepository;

    @InjectMocks
    private PosterService posterService;

    private User user;
    private Poster poster1;
    private Poster poster2;

    @BeforeEach
    void setUp() {
        user = new User("test@example.com", "password123");
        setUserId(user, 1L); // Setting ID using reflection

        poster1 = new Poster(1L, "http://image1.com", user);
        poster2 = new Poster(2L, "http://image2.com", user);
    }

    @Test
    void testFindAll() {
        when(posterRepository.findAll()).thenReturn(Arrays.asList(poster1, poster2));

        List<Poster> result = posterService.findAll();

        assertEquals(2, result.size());
        assertEquals("http://image1.com", result.get(0).getUrl());
        assertEquals("http://image2.com", result.get(1).getUrl());

        verify(posterRepository, times(1)).findAll();
    }

    @Test
    void testFindPosterById() {
        when(posterRepository.findPosterById(1L)).thenReturn(poster1);

        Poster result = posterService.findPosterById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("http://image1.com", result.getUrl());

        verify(posterRepository, times(1)).findPosterById(1L);
    }

    @Test
    void testFindByUserId() {
        when(posterRepository.findByUserId(1L)).thenReturn(Arrays.asList(poster1, poster2));

        List<Poster> result = posterService.findByUserId(1L);

        assertEquals(2, result.size());
        assertEquals("http://image1.com", result.get(0).getUrl());
        assertEquals("http://image2.com", result.get(1).getUrl());

        verify(posterRepository, times(1)).findByUserId(1L);
    }

    @Test
    @Transactional
    void testSavePoster() {
        when(posterRepository.save(poster1)).thenReturn(poster1);

        Poster savedPoster = posterService.savePoster(poster1);

        assertNotNull(savedPoster);
        assertEquals(1L, savedPoster.getId());
        assertEquals("http://image1.com", savedPoster.getUrl());

        verify(posterRepository, times(1)).save(poster1);
    }

    // Utility function to set user ID via reflection
    private void setUserId(User user, Long id) {
        try {
            Field field = User.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(user, id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set user ID via reflection", e);
        }
    }
}
