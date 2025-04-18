package ca.mcgill.ecse428.postr.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse428.postr.exception.PostrException;
import ca.mcgill.ecse428.postr.dao.PosterRepository;
import ca.mcgill.ecse428.postr.dao.UserRepository;
import ca.mcgill.ecse428.postr.dto.PosterRequestDTO;
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
    public Poster uploadPoster(String userEmail, String imageData, float price, String description, String title ){
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
        poster.setUrl(imageData);
        poster.setPrice(price);
        poster.setTitle(title);
        poster.setUser(user);

        return posterRepository.save(poster);
    }

    @Transactional
    public Poster buyPoster(Long id){
        Poster boughtPoster = posterRepository.findPosterById(id);
        if(boughtPoster == null){
            throw new PostrException(HttpStatus.BAD_REQUEST, "The poster does not exist");
        }
        boughtPoster.addPurchase();
        return boughtPoster;
    }

    @Transactional
    public void deletePoster(Long id) {
        Poster poster = findPosterById(id);
        if (poster == null) {
            throw new PostrException(HttpStatus.NOT_FOUND, "Poster not found");
        }
        posterRepository.delete(poster);
    }

    @Transactional
    public Poster updatePoster(Long id, PosterRequestDTO posterRequestDTO) {
        Poster poster = posterRepository.findPosterById(id);
        if (poster == null) {
            throw new PostrException(HttpStatus.NOT_FOUND, "Poster not found");
        }

        if(posterRequestDTO.getTitle() == null || posterRequestDTO.getTitle().isEmpty()){
            throw new PostrException(HttpStatus.BAD_REQUEST, "The poster must have a title");
        }
        if(posterRequestDTO.getDescription() == null || posterRequestDTO.getDescription().isEmpty()){
            throw new PostrException(HttpStatus.BAD_REQUEST, "The description cannot be empty");
        }
        if(posterRequestDTO.getUrl() == null || posterRequestDTO.getUrl().isEmpty()){
            throw new PostrException(HttpStatus.BAD_REQUEST, "There is no image for the poster");
        }
        if(posterRequestDTO.getPrice() < 0){
            throw new PostrException(HttpStatus.BAD_REQUEST, "The poster's price cannot be negative");
        }

        Poster existingPoster = posterRepository.findPosterByTitle(posterRequestDTO.getTitle());
        if(existingPoster != null && !existingPoster.getId().equals(id)) {
            throw new PostrException(HttpStatus.BAD_REQUEST, "A poster with the same title already exists");
        }

        poster.setTitle(posterRequestDTO.getTitle());
        poster.setDescription(posterRequestDTO.getDescription());
        poster.setUrl(posterRequestDTO.getUrl());
        poster.setPrice(posterRequestDTO.getPrice());

        return posterRepository.save(poster);
    }

    @Transactional
    public List<Poster> getMostPurchasedPosters() {
        return posterRepository.findAllByOrderByNumPurchasesDesc();
    }

    @Transactional
    public List<Poster> getByCategory(String category) {
        return posterRepository.findByCategory(category);
    }

    @Transactional
    public List<Poster> getByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0) {
            throw new PostrException(HttpStatus.BAD_REQUEST, "The poster's price cannot be negative");
        }
        List<Poster> posters = posterRepository.findByPriceBetween(minPrice, maxPrice);
        if (posters.isEmpty()) {
            throw new PostrException(HttpStatus.NOT_FOUND, "No posters match the selected criteria");
        }
        return posters;
    }
}
