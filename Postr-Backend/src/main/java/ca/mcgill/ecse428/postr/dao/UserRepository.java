package ca.mcgill.ecse428.postr.dao;

import ca.mcgill.ecse428.postr.model.User;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);

    @EntityGraph(attributePaths = {"posters"})
    User findUserByEmail(String email);
}
