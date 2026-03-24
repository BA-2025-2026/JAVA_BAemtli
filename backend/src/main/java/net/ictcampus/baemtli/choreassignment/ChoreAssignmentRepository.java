package net.ictcampus.baemtli.choreassignment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChoreAssignmentRepository extends CrudRepository<ChoreAssignment, Integer> {
    List<ChoreAssignment> findAll();
    List<ChoreAssignment> findByWorkdayDate(LocalDate date);
    List<ChoreAssignment> findByMonthAssignmentId(Integer monthAssignmentId);
    List<ChoreAssignment> findByMonthAssignmentTeamId(Integer teamId);
}
