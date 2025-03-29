package ca.mcgill.ecse428.postr.controller;

import ca.mcgill.ecse428.postr.dto.LikeDTO;
import ca.mcgill.ecse428.postr.exception.PostrException;
import ca.mcgill.ecse428.postr.service.LikeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/user/{userId}/poster/{posterId}")
    public ResponseEntity<?> likePoster(@PathVariable Long userId, @PathVariable Long posterId) {
        try {
            LikeDTO like = likeService.likePoster(userId, posterId);
            return ResponseEntity.ok(like);
        } catch (PostrException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @DeleteMapping("/user/{userId}/poster/{posterId}")
    public ResponseEntity<Void> unlikePoster(@PathVariable Long userId, @PathVariable Long posterId) {
        try {
            likeService.unlikePoster(userId, posterId);
            return ResponseEntity.noContent().build();
        } catch (PostrException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/user/{userId}/poster/{posterId}/status")
    public ResponseEntity<Boolean> hasUserLikedPoster(@PathVariable Long userId, @PathVariable Long posterId) {
        try {
            boolean hasLiked = likeService.hasUserLikedPoster(userId, posterId);
            return ResponseEntity.ok(hasLiked);
        } catch (PostrException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/poster/{posterId}/count")
    public ResponseEntity<Long> getNumberOfLikesForPoster(@PathVariable Long posterId) {
        try {
            long likeCount = likeService.getNumberOfLikesForPoster(posterId);
            return ResponseEntity.ok(likeCount);
        } catch (PostrException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Get an array of most liked posters in descending order
    @GetMapping("/most-liked")
    public ResponseEntity<List<LikeDTO>> getMostLikedPosters() {
        try {
            List<LikeDTO> mostLikedPosters = likeService.getMostLikedPosters();
            return ResponseEntity.ok(mostLikedPosters);
        } catch (PostrException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
}