package ca.mcgill.ecse428.postr.service;

import ca.mcgill.ecse428.postr.dto.LikeDTO;
import ca.mcgill.ecse428.postr.exception.PostrException;
import ca.mcgill.ecse428.postr.model.Like;
import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.model.User;
import ca.mcgill.ecse428.postr.dao.LikeRepository;
import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.dao.UserRepository;

import java.util.ArrayList;
import java.util.List;

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
        Like like = likeRepository.findByUser_IdAndPoster_Id(userId, posterId).orElseThrow(() -> new PostrException(HttpStatus.BAD_REQUEST, "Like not found"));
        likeRepository.delete(like);
    }

    public boolean hasUserLikedPoster(Long userId, Long posterId) {
        return likeRepository.findByUser_IdAndPoster_Id(userId, posterId).isPresent();
    }

    public long getNumberOfLikesForPoster(Long posterId) {
        return likeRepository.countByPoster_Id(posterId);
    }

    private LikeDTO convertToDTO(Like like) {
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setId(like.getId());
        likeDTO.setUserId(like.getUser().getId());
        likeDTO.setPosterId(like.getPoster().getId());
        return likeDTO;
    }

    public List<LikeDTO> getAllLikes() {
        List<Like> likes = likeRepository.findAll();
        List<LikeDTO> likeDTOs = new ArrayList<>();
        for (Like like : likes) {
            likeDTOs.add(convertToDTO(like));
        }
        return likeDTOs;
    }

    
    public List<LikeDTO> getMostLikedPosters() {
        List<Long> posterIds = likeRepository.findDistinctPosterIds();
        List<LikeDTO> mostLikedPosters = new ArrayList<>();

        for (Long posterId : posterIds) {
            long likeCount = getNumberOfLikesForPoster(posterId);
            LikeDTO likeDTO = new LikeDTO();
            likeDTO.setPosterId(posterId);
            likeDTO.setNumLikes(likeCount); 
            likeDTO.setPosterTitle(posterRepository.findPosterById(posterId).getTitle()); // Assuming you have a method to get the poster title
            mostLikedPosters.add(likeDTO);
        }

        mostLikedPosters.sort((a, b) -> Long.compare(b.getNumLikes(), a.getNumLikes()));
        return mostLikedPosters;

    }

  

}