package ca.mcgill.ecse428.postr.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PosterRepositoryTests {

    @Autowired
    private PosterRepository posterRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        posterRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testPosterPersistence() {
        // Create and save User
        User user = new User("testuser@gmail.com", "securepassword");
        user = userRepository.save(user);

        // Create and save Poster
        String url = "https://example.com/poster.jpg";
        Poster poster = new Poster(null, url, user);
        poster = posterRepository.save(poster);

        // Retrieve from database
        Poster posterFromDB = posterRepository.findPosterById(poster.getId());

        // Assertions
        assertNotNull(posterFromDB);
        assertEquals(poster.getId(), posterFromDB.getId());
        assertEquals(url, posterFromDB.getUrl());
        assertEquals(user.getId(), posterFromDB.getUser().getId());
    }
}
