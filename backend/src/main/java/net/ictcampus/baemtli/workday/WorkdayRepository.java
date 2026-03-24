package net.ictcampus.baemtli.workday;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkdayRepository extends CrudRepository<Workday, LocalDate> {
    List<Workday> findAllByOrderByDateAsc();
}
