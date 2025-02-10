package ca.mcgill.ecse428.postr.controller;

import ca.mcgill.ecse428.postr.dto.PosterDTO;
import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.service.PosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PosterController {

    @Autowired
    private PosterService posterService;

    /**
     * GET /posters
     * Retrieve all posters.
     */
    @GetMapping("/posters")
    public List<PosterDTO> getAllPosters() {
        List<Poster> posters = posterService.findAll();
        return posters.stream()
                      .map(PosterDTO::new)
                      .collect(Collectors.toList());
    }

    /**
     * GET /posters/{id}
     * Retrieve a poster by its ID.
     */
    @GetMapping("/posters/id/{id}")
    public ResponseEntity<PosterDTO> getPosterById(@PathVariable Long id) {
        Poster poster = posterService.findPosterById(id);
        if (poster == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new PosterDTO(poster));
    }

    /**
     * GET /posters/user/{userId}
     * Retrieve all posters associated with a given user ID.
     */
    @GetMapping("/posters/userid/{userId}")
    public List<PosterDTO> getPostersByUserId(@PathVariable Long userId) {
        List<Poster> posters = posterService.findByUserId(userId);
        return posters.stream()
                      .map(PosterDTO::new)
                      .collect(Collectors.toList());
    }

}
