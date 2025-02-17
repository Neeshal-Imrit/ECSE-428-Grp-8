package ca.mcgill.ecse428.postr.integration;

import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.dao.UserRepository;
import ca.mcgill.ecse428.postr.dto.ErrorDTO;
import ca.mcgill.ecse428.postr.dto.PosterRequestDTO;
import ca.mcgill.ecse428.postr.dto.PosterResponseDTO;
import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

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
//    private final Long VALID_USERID = 1L;
    private final String VALID_PASSWORD = "PASSWORD";
//    private final Long VALID_POSTERID = 1L;

    private final String NEW_TITLE = "NEW TITLE";
    private final String NEW_DESCRIPTION = "NEW DESCRIPTION";
    private final float NEW_PRICE = 20.0f;
    private final byte[] NEW_IMAGE_DATA = "NEW IMAGE".getBytes();

    private Long savedUserID;
    private Long savedPosterID;
    @BeforeAll
    public void setup() {
        posterRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User( VALID_USEREMAIL, VALID_PASSWORD);
        userRepository.save(user);
        savedUserID = user.getId();

        Poster poster = new Poster();
        poster.setUser(user);
        poster.setTitle(VALID_TITLE);
        poster.setDescription(VALID_DESCRIPTION);
        poster.setPrice(VALID_PRICE);
        poster.setImageData(VALID_IMAGE_DATA);
        posterRepository.save(poster);
        savedPosterID = poster.getId();
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

    @Test
    @Order(2)
    public void testGetPosterByTitle() {
        ResponseEntity<PosterResponseDTO> response = client.getForEntity("/posters/title/" + VALID_TITLE, PosterResponseDTO.class);
        PosterResponseDTO poster = response.getBody();
        assertEquals(VALID_TITLE, poster.getTitle());
        assertEquals(VALID_DESCRIPTION, poster.getDescription());
        assertEquals(VALID_PRICE, poster.getPrice());
        assertArrayEquals(VALID_IMAGE_DATA, poster.getImageData());
    }

    @Test
    @Order(3)
    public void testGetPosterByNonExistentTitle() {
        ResponseEntity<PosterResponseDTO> response = client.getForEntity("/posters/title/INVALID", PosterResponseDTO.class);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @Order(4)
    public void testGetPosterById() {
        ResponseEntity<PosterResponseDTO> response = client.getForEntity("/posters/id/" + savedPosterID, PosterResponseDTO.class);
        PosterResponseDTO poster = response.getBody();
        assertEquals(VALID_TITLE, poster.getTitle());
        assertEquals(VALID_DESCRIPTION, poster.getDescription());
        assertEquals(VALID_PRICE, poster.getPrice());
        assertArrayEquals(VALID_IMAGE_DATA, poster.getImageData());
    }

    @Test
    @Order(5)
    public void testGetPosterByNonExistentId() {
        ResponseEntity<PosterResponseDTO> response = client.getForEntity("/posters/id/89786", PosterResponseDTO.class);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @Order(6)
    public void testGetPostersByUserId() {
        ResponseEntity<List<PosterResponseDTO>> response = client.exchange("/posters/userid/" + savedUserID, HttpMethod.GET, null, new ParameterizedTypeReference<List<PosterResponseDTO>>() {});
        List<PosterResponseDTO> posters = response.getBody();
        assertEquals(1, posters.size());
        assertEquals(VALID_TITLE, posters.get(0).getTitle());
        assertEquals(VALID_DESCRIPTION, posters.get(0).getDescription());
        assertEquals(VALID_PRICE, posters.get(0).getPrice());
        assertArrayEquals(VALID_IMAGE_DATA, posters.get(0).getImageData());
    }

    @Test
    @Order(7)
    public void testGetPostersByNonExistentUserId() {
        ResponseEntity<List<PosterResponseDTO>> response = client.exchange("/posters/userid/89786", HttpMethod.GET, null, new ParameterizedTypeReference<List<PosterResponseDTO>>() {});
        List<PosterResponseDTO> posters = response.getBody();
        assertEquals(0, posters.size());
    }

    @Test
    @Order(8)
    public void testGetPostersByUserEmail() {
        ResponseEntity<List<PosterResponseDTO>> response = client.exchange("/posters/useremail/" + VALID_USEREMAIL, HttpMethod.GET, null, new ParameterizedTypeReference<List<PosterResponseDTO>>() {});
        List<PosterResponseDTO> posters = response.getBody();
        assertEquals(1, posters.size());
        assertEquals(VALID_TITLE, posters.get(0).getTitle());
        assertEquals(VALID_DESCRIPTION, posters.get(0).getDescription());
        assertEquals(VALID_PRICE, posters.get(0).getPrice());
        assertArrayEquals(VALID_IMAGE_DATA, posters.get(0).getImageData());
    }

    @Test
    @Order(9)
    public void testGetPostersByNonExistentUserEmail() {
        ResponseEntity<List<PosterResponseDTO>> response = client.exchange("/posters/useremail/INVALID", HttpMethod.GET, null, new ParameterizedTypeReference<List<PosterResponseDTO>>() {});
        List<PosterResponseDTO> posters = response.getBody();
        assertEquals(0, posters.size());
    }

    @Test
    @Order(10)
    public void testUploadPoster() {
        PosterRequestDTO posterRequestDTO = new PosterRequestDTO( NEW_TITLE, NEW_DESCRIPTION, NEW_PRICE, NEW_IMAGE_DATA, VALID_USEREMAIL);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<PosterRequestDTO> request = new HttpEntity<>(posterRequestDTO, headers);

        ResponseEntity<PosterResponseDTO> response = client.postForEntity("/posters", request, PosterResponseDTO.class);
        PosterResponseDTO poster = response.getBody();
        assertEquals("NEW TITLE", poster.getTitle());
        assertEquals("NEW DESCRIPTION", poster.getDescription());
        assertEquals(20.0f, poster.getPrice());
        assertArrayEquals("NEW IMAGE".getBytes(), poster.getImageData());
    }

    @Test
    @Order(11)
    public void testUploadPosterWithNonExistentUser() {
        PosterRequestDTO posterRequestDTO = new PosterRequestDTO(NEW_TITLE, NEW_DESCRIPTION, NEW_PRICE, NEW_IMAGE_DATA, "Fake user");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<PosterRequestDTO> request = new HttpEntity<>(posterRequestDTO, headers);

        ResponseEntity<ErrorDTO> response = client.postForEntity("/posters", request, ErrorDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The user's email cannot be found", response.getBody().getErrors().getFirst());
    }

    @Test
    @Order(12)
    public void testUploadPosterWithNegativePrice() {
        PosterRequestDTO posterRequestDTO = new PosterRequestDTO(NEW_TITLE, NEW_DESCRIPTION, -1.0f, NEW_IMAGE_DATA, VALID_USEREMAIL);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<PosterRequestDTO> request = new HttpEntity<>(posterRequestDTO, headers);

        ResponseEntity<ErrorDTO> response = client.postForEntity("/posters", request, ErrorDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The poster's price cannot be negative", response.getBody().getErrors().getFirst());
    }

    @Test
    @Order(13)
    public void testUploadPosterWithEmptyDescription() {
        PosterRequestDTO posterRequestDTO = new PosterRequestDTO(NEW_TITLE, "", NEW_PRICE, NEW_IMAGE_DATA, VALID_USEREMAIL);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<PosterRequestDTO> request = new HttpEntity<>(posterRequestDTO, headers);

        ResponseEntity<ErrorDTO> response = client.postForEntity("/posters", request, ErrorDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The description cannot be empty", response.getBody().getErrors().getFirst());
    }

    @Test
    @Order(14)
    public void testUploadPosterWithEmptyTitle() {
        PosterRequestDTO posterRequestDTO = new PosterRequestDTO("", NEW_DESCRIPTION, NEW_PRICE, NEW_IMAGE_DATA, VALID_USEREMAIL);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<PosterRequestDTO> request = new HttpEntity<>(posterRequestDTO, headers);

        ResponseEntity<ErrorDTO> response = client.postForEntity("/posters", request, ErrorDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The poster must have a title", response.getBody().getErrors().getFirst());
    }

    @Test
    @Order(15)
    public void testUploadPosterWithExistingTitle() {
        PosterRequestDTO posterRequestDTO = new PosterRequestDTO(VALID_TITLE, NEW_DESCRIPTION, NEW_PRICE, NEW_IMAGE_DATA, VALID_USEREMAIL);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<PosterRequestDTO> request = new HttpEntity<>(posterRequestDTO, headers);

        ResponseEntity<ErrorDTO> response = client.postForEntity("/posters", request, ErrorDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("A poster with the same title already exists", response.getBody().getErrors().getFirst());
    }

    @Test
    @Order(16)
    public void testUploadPosterWithNoEmail() {
        PosterRequestDTO posterRequestDTO = new PosterRequestDTO(NEW_TITLE, NEW_DESCRIPTION, NEW_PRICE, NEW_IMAGE_DATA, "");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<PosterRequestDTO> request = new HttpEntity<>(posterRequestDTO, headers);

        ResponseEntity<ErrorDTO> response = client.postForEntity("/posters", request, ErrorDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The poster uploader has no email", response.getBody().getErrors().getFirst());
    }

    @Test
    @Order(17)
    public void testUploadPosterWithNoImage() {
        PosterRequestDTO posterRequestDTO = new PosterRequestDTO(NEW_TITLE, NEW_DESCRIPTION, NEW_PRICE, null, VALID_USEREMAIL);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<PosterRequestDTO> request = new HttpEntity<>(posterRequestDTO, headers);

        ResponseEntity<ErrorDTO> response = client.postForEntity("/posters", request, ErrorDTO.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("There is no image for the poster", response.getBody().getErrors().getFirst());
    }
}
