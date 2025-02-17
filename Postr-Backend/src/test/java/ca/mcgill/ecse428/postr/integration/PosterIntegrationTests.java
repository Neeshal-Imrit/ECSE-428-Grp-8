package ca.mcgill.ecse428.postr.integration;

import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.dao.UserRepository;
import ca.mcgill.ecse428.postr.dto.PosterRequestDTO;
import ca.mcgill.ecse428.postr.dto.PosterResponseDTO;
import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PosterIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private PosterRepository posterRepository;

    @Autowired
    private UserRepository userRepository;

    private final String VALID_USEREMAIL = "USER@EMAIL.COM";
    private final String VALID_TITLE = "TITLE";
    private final String VALID_DESCRIPTION = "DESCRIPTION";
    private final float VALID_PRICE = 10.0f;
    private final byte[] VALID_IMAGE_DATA = "HEllo".getBytes();
    private final Long VALID_USERID = 1L;
    private final String VALID_PASSWORD = "PASSWORD";
    private final Long VALID_POSTERID = 1L;


    @BeforeAll
    public void setup() {
        posterRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User( VALID_USEREMAIL, VALID_PASSWORD);
        userRepository.save(user);

        Poster poster = new Poster();
        poster.setUser(user);
        poster.setTitle(VALID_TITLE);
        poster.setDescription(VALID_DESCRIPTION);
        poster.setPrice(VALID_PRICE);
        poster.setImageData(VALID_IMAGE_DATA);
        posterRepository.save(poster);
    }

    @AfterAll
    public void tearDown() {
        posterRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testGetAllPosters() {

        ResponseEntity<List<PosterResponseDTO>> response = client.exchange("/posters", HttpMethod.GET, null, new ParameterizedTypeReference<List<PosterResponseDTO>>() {});
        List<PosterResponseDTO> posters = response.getBody();
        assertEquals(1, posters.size());
        assertEquals(VALID_TITLE, posters.get(0).getTitle());
        assertEquals(VALID_DESCRIPTION, posters.get(0).getDescription());
        assertEquals(VALID_PRICE, posters.get(0).getPrice());
        assertArrayEquals(VALID_IMAGE_DATA, posters.get(0).getImageData());
    }

}
