package net.ictcampus.baemtli.monthassignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonthAssignmentRepository extends JpaRepository<MonthAssignment, Integer> {
    List<MonthAssignment> findAll();
    Optional<MonthAssignment> findByTeamIdAndChoreCategoryIdAndMonthId(Integer teamId, Integer choreCategoryId, Integer monthId);
}
