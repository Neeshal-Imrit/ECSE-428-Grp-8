package ca.mcgill.ecse428.postr.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse428.postr.exception.PostrException;
import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.dao.UserRepository;
import ca.mcgill.ecse428.postr.model.Poster;
import ca.mcgill.ecse428.postr.model.User;
import jakarta.transaction.Transactional;

@Service
public class PosterService {
    @Autowired
    private PosterRepository posterRepository;
    @Autowired
    private UserRepository userRepository;
    
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

    public List<Poster> findByUserEmail(String userEmail) {
        return posterRepository.findByUserEmail(userEmail);
    }
    public Poster findByTitle(String title) {
        return posterRepository.findPosterByTitle(title);
    }

    //save poster
    @Transactional
    public Poster savePoster(Poster poster) {
        return posterRepository.save(poster);
    }

    @Transactional
    public Poster uploadPoster(String userEmail, byte[] imageData, float price, String description, String title ){
        if(userEmail == null || userEmail.isEmpty()){
            throw new PostrException(HttpStatus.BAD_REQUEST, "The poster uploader has no email");
        }
        User user = userRepository.findUserByEmail(userEmail);
        if(user == null){
            throw new PostrException(HttpStatus.BAD_REQUEST, "The user's email cannot be found");
        }

        if(imageData == null){
            throw new PostrException(HttpStatus.BAD_REQUEST, "There is no image for the poster");
        }

        if(price < 0){
            throw new PostrException(HttpStatus.BAD_REQUEST, "The poster's price cannot be negative");
        }

        if(description == null || description.isEmpty()){
            throw new PostrException(HttpStatus.BAD_REQUEST, "The description cannot be empty");
        }

        if(title == null || title.isEmpty()){
            throw new PostrException(HttpStatus.BAD_REQUEST, "The poster must have a title");
        }
        Poster aPoster = posterRepository.findPosterByTitle(title);

        if(aPoster != null){
            throw new PostrException(HttpStatus.BAD_REQUEST, "A poster with the same title already exists");

        }

        Poster poster = new Poster();
        poster.setDescription(description);
        poster.setImageData(imageData);
        poster.setPrice(price);
        poster.setTitle(title);
        poster.setUser(user);

        posterRepository.save(poster);
        return poster;
    }
}
