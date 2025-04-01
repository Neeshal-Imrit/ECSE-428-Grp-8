package ca.mcgill.ecse428.postr.dao;

import ca.mcgill.ecse428.postr.model.Poster;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosterRepository extends CrudRepository<Poster, Long> {
    List<Poster> findByUserId(Long userId);

    Poster findPosterById(Long id);

    //find all posters
    List<Poster> findAll();

    Poster findPosterByTitle(String title);

    List<Poster> findByUserEmail(String userEmail);

    @Query("SELECT p FROM Poster p WHERE p.numPurchases > 0 ORDER BY p.numPurchases DESC")
    List<Poster> findAllByOrderByNumPurchasesDesc();

    List<Poster> findByCategory(String category);
    List<Poster> findByPriceBetween(double minPrice, double maxPrice);
}

