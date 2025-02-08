package ca.mcgill.ecse428.postr.dao;

import ca.mcgill.ecse428.postr.model.Poster;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosterRepository extends CrudRepository<Poster, Long> {
    List<Poster> findByUserId(Long userId);

    Poster findPosterById(Long id);
}

