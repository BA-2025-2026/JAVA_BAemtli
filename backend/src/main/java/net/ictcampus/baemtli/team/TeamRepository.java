package net.ictcampus.baemtli.team;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends CrudRepository<Team, Integer> {
    List<Team> findAll();

    Optional<Team> findByName(String name);
}
