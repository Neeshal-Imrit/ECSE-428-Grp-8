package ca.mcgill.ecse428.postr.dao;

import ca.mcgill.ecse428.postr.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserById(Long id);
}
