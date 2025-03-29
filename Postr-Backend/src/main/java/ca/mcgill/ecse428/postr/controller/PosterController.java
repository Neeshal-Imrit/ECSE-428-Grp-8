package ca.mcgill.ecse428.postr.controller;

import ca.mcgill.ecse428.postr.dto.PosterRequestDTO;
import ca.mcgill.ecse428.postr.dto.PosterResponseDTO;
import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.service.PosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ca.mcgill.ecse428.postr.dto.ErrorDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class PosterController {

    @Autowired
    private PosterService posterService;

    /**
     * GET /posters
     * Retrieve all posters.
     */
    @GetMapping("/posters")
    public List<PosterResponseDTO> getAllPosters() {
        List<Poster> posters = posterService.findAll();
        return posters.stream()
                      .map(PosterResponseDTO::new)
                      .collect(Collectors.toList());
    }

    /**
     * GET /posters/{id}
     * Retrieve a poster by its ID.
     */
    @GetMapping("/posters/id/{id}")
    public ResponseEntity<PosterResponseDTO> getPosterById(@PathVariable Long id) {
        Poster poster = posterService.findPosterById(id);
        if (poster == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new PosterResponseDTO(poster));
    }

    /**
     * GET /posters/user/{userId}
     * Retrieve all posters associated with a given user ID.
     */
    @GetMapping("/posters/userid/{userId}")
    public List<PosterResponseDTO> getPostersByUserId(@PathVariable Long userId) {
        List<Poster> posters = posterService.findByUserId(userId);
        return posters.stream()
                      .map(PosterResponseDTO::new)
                      .collect(Collectors.toList());
    }

    /**
     * GET /posters/user/{userEmail}
     * Retrieve all posters associated with a given user email.
     */
    @GetMapping("/posters/useremail/{userEmail}")
    public List<PosterResponseDTO> getPostersByUserEmail(@PathVariable String userEmail) {
        List<Poster> posters = posterService.findByUserEmail(userEmail);
        return posters.stream()
                      .map(PosterResponseDTO::new)
                      .collect(Collectors.toList());
    }

    /**
     * GET /posters/title/{title}
     * Retrieve a poster by its title.
     */
    @GetMapping("/posters/title/{title}")
    public ResponseEntity<PosterResponseDTO> getPosterByTitle(@PathVariable String title) {
        Poster poster = posterService.findByTitle(title);
        if (poster == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new PosterResponseDTO(poster));
    }

    /**
     * POST /posters
     * Upload a new poster.
     */
    @PostMapping("/posters")
    public ResponseEntity<PosterResponseDTO> uploadPoster(@RequestBody PosterRequestDTO posterRequestDTO) {
        Poster poster = posterService.uploadPoster(posterRequestDTO.getUserEmail(),
                                                    posterRequestDTO.getUrl(),
                                                    posterRequestDTO.getPrice(),
                                                    posterRequestDTO.getDescription(),
                                                    posterRequestDTO.getTitle());
        return ResponseEntity.ok(new PosterResponseDTO(poster));
    }

    /**
     * DELETE /posters/id/{id}
     * Delete a poster.
     */
    @DeleteMapping("/posters/id/{id}")
    public ResponseEntity<String> deletePoster(@PathVariable Long id) {
        posterService.deletePoster(id);
        return ResponseEntity.ok("Poster deleted successfully");
    }    


     /*** 
     * PUT /posters/buy
     * Buy a poster.
     */
    @PutMapping("/posters/buy/{posterId}")
    public ResponseEntity<?> buyPoster(@PathVariable Long posterId) {
        try {
            Poster poster = posterService.buyPoster(posterId);
            return new ResponseEntity<>(new PosterResponseDTO(poster), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * PUT /posters/update/{id}
     * Update an existing poster.
     */
    @PutMapping("/posters/update/{id}")
    public ResponseEntity<?> updatePoster(@PathVariable Long id, @RequestBody PosterRequestDTO posterRequestDTO) {
        try {
            Poster poster = posterService.updatePoster(id, posterRequestDTO);
            return new ResponseEntity<>(new PosterResponseDTO(poster), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * GET /posters/purchaseLeaderboard
     * Retrieve the posters from most purchased to least purchased.
     */
    @GetMapping("/posters/purchaseLeaderboard")
    public List<PosterResponseDTO> getPurchaseLeaderboard() {
        List<Poster> posters = posterService.getMostPurchasedPosters();
        return posters.stream()
                      .map(PosterResponseDTO::new)
                      .collect(Collectors.toList());
    }
}
