package ca.mcgill.ecse428.postr.dao;

import ca.mcgill.ecse428.postr.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndPosterId(Long userId, Long posterId);
    long countByPosterId(Long posterId);
}