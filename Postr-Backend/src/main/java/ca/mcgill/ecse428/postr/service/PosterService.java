package ca.mcgill.ecse428.postr.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse428.postr.exception.PostrException;
import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.model.Poster;
import jakarta.transaction.Transactional;

@Service
public class PosterService {
    @Autowired
    private PosterRepository posterRepository;
    
    //find all posters
    public List<Poster> findAll() {
        return posterRepository.findAll();
    }

    //find poster by id
    public Poster findPosterById(Long id) {
        return posterRepository.findPosterById(id);
    }

    //find poster by user id
    public List<Poster> findByUserId(Long userId) {
        return posterRepository.findByUserId(userId);
    }

    //save poster
    @Transactional
    public Poster savePoster(Poster poster) {
        return posterRepository.save(poster);
    }

    @Transactional
    public Poster uploadPoster(String userEmail, byte[] imageData, float price, String description, String title ){
        
        //throw new PostrException(HttpStatus.BAD_REQUEST, "The poster is empty");

        
    }
}
