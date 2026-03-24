package net.ictcampus.baemtli.workday;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import net.ictcampus.baemtli.workday.dto.WorkdayDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkdayService {

    private final WorkdayRepository workdayRepository;

    public WorkdayService(WorkdayRepository workdayRepository) {
        this.workdayRepository = workdayRepository;
    }

    public List<WorkdayDTO> getAllWorkdays() {
        return workdayRepository.findAllByOrderByDateAsc().stream()
                .map(w -> new WorkdayDTO(w.getDate()))
                .toList();
    }

    public WorkdayDTO addWorkday(LocalDate date) {
        if (workdayRepository.existsById(date)) {
            throw new EntityExistsException("Workday already exists for this date");
        }
        Workday workday = new Workday(date);
        return new WorkdayDTO(workdayRepository.save(workday).getDate());
    }

    public void deleteWorkday(LocalDate date) {
        if (!workdayRepository.existsById(date)) {
            throw new EntityNotFoundException("Workday not found for this date");
        }
        workdayRepository.deleteById(date);
    }

    public void generateDefaultWorkdays(int year) {
        LocalDate start = LocalDate.of(year, 8, 1); // BA Jahr startet im August
        LocalDate end = LocalDate.of(year + 1, 7, 31);

        while (start.isBefore(end) || start.isEqual(end)) {
            switch (start.getDayOfWeek()) {
                case WEDNESDAY, THURSDAY, FRIDAY -> {
                    if (!workdayRepository.existsById(start)) {
                        workdayRepository.save(new Workday(start));
                    }
                }
            }
            start = start.plusDays(1);
        }
    }
}
