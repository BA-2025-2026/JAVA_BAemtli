package net.ictcampus.baemtli.workday;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.ictcampus.baemtli.workday.dto.WorkdayDTO;
import net.ictcampus.baemtli.workday.dto.WorkdayInitializationResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "WorkdayController", description = "Management of BA workdays")
@RestController
@RequestMapping("/workdays")
public class WorkdayController {

    private final WorkdayService workdayService;

    public WorkdayController(WorkdayService workdayService) {
        this.workdayService = workdayService;
    }

    @Operation(summary = "Get all workdays", description = "Fetches all days defined as BA workdays. Permission: team:read:all")
    @GetMapping
    public ResponseEntity<List<WorkdayDTO>> getAllWorkdays() {
        return ResponseEntity.ok(workdayService.getAllWorkdays());

    }

    @Operation(summary = "Add a workday", description = "Defines a specific date as a BA workday. Permission: team:write:all")
    @PostMapping("/{date}")
    public ResponseEntity<WorkdayDTO> addWorkday(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workdayService.addWorkday(date));
    }

    @Operation(summary = "Remove a workday", description = "Removes a date from the BA workdays. Permission: team:write:all")
    @DeleteMapping("/{date}")
    public ResponseEntity<Void> deleteWorkday(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        workdayService.deleteWorkday(date);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Initialize the current school year",
            description = "Creates the default Wednesday-to-Friday workdays for the current school year if none exist yet."
    )
    @ApiResponse(responseCode = "200", description = "Returns whether the school year was initialized or was already present")
    @PostMapping("/initialize-current-school-year")
    public ResponseEntity<WorkdayInitializationResponse> initializeCurrentSchoolYear() {
        return ResponseEntity.ok(workdayService.initializeCurrentSchoolYear());
    }
}
