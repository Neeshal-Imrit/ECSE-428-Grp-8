package ca.mcgill.ecse428.postr.acceptance;
import ca.mcgill.ecse428.postr.dto.PosterDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PosterAcceptanceTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetAllPosters() {
        PosterDTO poster1 = new PosterDTO(null, "https://upload.wikimedia.org/wikipedia/commons/thumb/7/71/I_Want_You_for_U.S._Army_by_James_Montgomery_Flagg.jpg/1200px-I_Want_You_for_U.S._Army_by_James_Montgomery_Flagg.jpg", 1L);
        PosterDTO poster2 = new PosterDTO(null, "https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/89058/93685/Joker-2019-Final-Style-steps-Poster-buy-original-movie-posters-at-starstills__62518.1669120603.jpg?c=2", 1L);
        restTemplate.postForEntity("/posters", poster1, PosterDTO.class);
        restTemplate.postForEntity("/posters", poster2, PosterDTO.class);

        ResponseEntity<PosterDTO[]> response = restTemplate.getForEntity("/posters", PosterDTO[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PosterDTO[] posters = response.getBody();
        assertThat(posters).isNotNull();
        assertThat(posters.length).isGreaterThanOrEqualTo(2);
    }

 
    @Test
    public void testGetPostersByUserId() {
        PosterDTO posterForUser2 = new PosterDTO(null, "https://cdn11.bigcommerce.com/s-ydriczk/images/stencil/1500x1500/products/89058/93685/Joker-2019-Final-Style-steps-Poster-buy-original-movie-posters-at-starstills__62518.1669120603.jpg?c=2", 2L);
        restTemplate.postForEntity("/posters", posterForUser2, PosterDTO.class);

        ResponseEntity<PosterDTO[]> response = restTemplate.getForEntity("/posters/user/2", PosterDTO[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        PosterDTO[] posters = response.getBody();
        assertThat(posters).isNotNull();
        assertThat(posters.length).isGreaterThanOrEqualTo(1);
    }
}
