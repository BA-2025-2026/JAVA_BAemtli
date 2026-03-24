package net.ictcampus.baemtli.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    // Override to prevent from returning Optionals / Iterables, we want to get back User instances instead
    List<User> findAll();

    // For Auth
    Optional<User> findByUsername(@Param("username") String username);
}
