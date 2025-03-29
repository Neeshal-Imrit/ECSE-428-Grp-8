package ca.mcgill.ecse428.postr.dao;

import ca.mcgill.ecse428.postr.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUser_IdAndPoster_Id(Long userId, Long posterId);
    long countByPoster_Id(Long posterId);
    
    @Query("SELECT DISTINCT l.poster.id FROM Like l")
    List<Long> findDistinctPosterIds();
    
}