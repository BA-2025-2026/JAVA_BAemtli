package net.ictcampus.baemtli.trainee;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TraineeRepository extends CrudRepository<Trainee, Integer> {
    List<Trainee> findAll();

    List<Trainee> findByTeam_Id(Integer teamId);

    Optional<Trainee> findByFirstNameAndLastName(String firstName, String lastName);
}
