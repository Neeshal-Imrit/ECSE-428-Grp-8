package ca.mcgill.ecse428.postr.service;

import ca.mcgill.ecse428.postr.dto.LikeDTO;
import ca.mcgill.ecse428.postr.exception.PostrException;
import ca.mcgill.ecse428.postr.model.Like;
import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.model.User;
import ca.mcgill.ecse428.postr.dao.LikeRepository;
import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PosterRepository posterRepository;

    public LikeDTO likePoster(Long userId, Long posterId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Poster poster = posterRepository.findById(posterId).orElseThrow(() -> new RuntimeException("Poster not found"));

        Like like = new Like();
        like.setUser(user);
        like.setPoster(poster);
        like = likeRepository.save(like);

        return convertToDTO(like);
    }

    public void unlikePoster(Long userId, Long posterId) {
        Like like = likeRepository.findByUserIdAndPosterId(userId, posterId).orElseThrow(() -> new PostrException(HttpStatus.BAD_REQUEST, "Like not found"));
        likeRepository.delete(like);
    }

    public boolean hasUserLikedPoster(Long userId, Long posterId) {
        return likeRepository.findByUserIdAndPosterId(userId, posterId).isPresent();
    }

    public long getNumberOfLikesForPoster(Long posterId) {
        return likeRepository.countByPosterId(posterId);
    }

    private LikeDTO convertToDTO(Like like) {
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setId(like.getId());
        likeDTO.setUserId(like.getUser().getId());
        likeDTO.setPosterId(like.getPoster().getId());
        return likeDTO;
    }
}