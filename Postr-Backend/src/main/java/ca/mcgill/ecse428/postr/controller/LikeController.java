package ca.mcgill.ecse428.postr.controller;

import ca.mcgill.ecse428.postr.dto.LikeDTO;

import ca.mcgill.ecse428.postr.service.LikeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/user/{userId}/poster/{posterId}")
    public ResponseEntity<LikeDTO> likePoster(@PathVariable Long userId, @PathVariable Long posterId) {
        LikeDTO like = likeService.likePoster(userId, posterId);
        return ResponseEntity.ok(like);
    }

    @DeleteMapping("/user/{userId}/poster/{posterId}")
    public ResponseEntity<Void> unlikePoster(@PathVariable Long userId, @PathVariable Long posterId) {
        likeService.unlikePoster(userId, posterId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}/poster/{posterId}/status")
    public ResponseEntity<Boolean> hasUserLikedPoster(@PathVariable Long userId, @PathVariable Long posterId) {
        boolean hasLiked = likeService.hasUserLikedPoster(userId, posterId);
        return ResponseEntity.ok(hasLiked);
    }

    @GetMapping("/poster/{posterId}/count")
    public ResponseEntity<Long> getNumberOfLikesForPoster(@PathVariable Long posterId) {
        long likeCount = likeService.getNumberOfLikesForPoster(posterId);
        return ResponseEntity.ok(likeCount);
    }

     // Get an array of most liked posters in descending order
    @GetMapping("/most-liked")
    public ResponseEntity<List<LikeDTO>> getMostLikedPosters() {
        List<LikeDTO> mostLikedPosters = likeService.getMostLikedPosters();
        return ResponseEntity.ok(mostLikedPosters);
    }
    
}