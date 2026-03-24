package net.ictcampus.baemtli.monthassignment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.ictcampus.baemtli.monthassignment.dto.CreateMonthAssignmentDTO;
import net.ictcampus.baemtli.monthassignment.dto.MonthAssignmentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "MonthAssignmentController", description = "Management of team chore assignments per month")
@RestController
@RequestMapping("/monthassignments")
public class MonthAssignmentController {

    private final MonthAssignmentService monthAssignmentService;

    public MonthAssignmentController(MonthAssignmentService monthAssignmentService) {
        this.monthAssignmentService = monthAssignmentService;
    }

    @Operation(summary = "Get all monthly assignments", description = "Fetches all assignments of teams to chore categories per month. Permission: month-assignment:read:all")
    @GetMapping
    public ResponseEntity<List<MonthAssignmentDTO>> getAllAssignments() {
        return ResponseEntity.ok(monthAssignmentService.getAllAssignments());
    }

    @Operation(summary = "Create a monthly assignment", description = "Creates a new assignment. Permission: month-assignment:write:all")
    @PostMapping
    public ResponseEntity<MonthAssignmentDTO> createAssignment(@Valid @RequestBody CreateMonthAssignmentDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(monthAssignmentService.createAssignment(dto));
    }

    @Operation(summary = "Update a monthly assignment", description = "Updates an existing assignment. Permission: month-assignment:write:all")
    @PatchMapping("/{id}")
    public ResponseEntity<MonthAssignmentDTO> updateAssignment(@PathVariable Integer id, @Valid @RequestBody net.ictcampus.baemtli.monthassignment.dto.UpdateMonthAssignmentDTO dto) {
        return ResponseEntity.ok(monthAssignmentService.updateAssignment(id, dto));
    }

    @Operation(summary = "Delete a monthly assignment", description = "Deletes an assignment. Permission: month-assignment:write:all")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Integer id) {
        monthAssignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }
}
