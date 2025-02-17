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

    public byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
    @Test
public void testPosterPersistence() {

    
    // Create and save User
    User user = new User("testuser@gmail.com", "securepassword");
    user = userRepository.save(user);

    // Create and save Poster
    String url = "https://example.com/poster.jpg";
    Poster poster = new Poster();
    poster.setUrl(url);
    poster.setTitle("Test Poster Title"); // Set title to avoid null constraint
    poster.setDescription("Test Description");
    poster.setPrice(0);
    poster.setImageData(hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d"));
    poster.setUser(user);

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
