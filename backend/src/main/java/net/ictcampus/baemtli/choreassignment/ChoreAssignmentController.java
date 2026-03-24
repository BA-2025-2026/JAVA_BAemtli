package net.ictcampus.baemtli.choreassignment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.ictcampus.baemtli.choreassignment.dto.ChoreAssignmentDTO;
import net.ictcampus.baemtli.choreassignment.dto.CreateChoreAssignmentDTO;
import net.ictcampus.baemtli.security.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Chore Assignments", description = "To actually assign a chore to a trainee on a specific day")
@RestController
@RequestMapping("/choreassignments")
public class ChoreAssignmentController {

    private final ChoreAssignmentService choreAssignmentService;
    private final AuthorizationService authz;

    public ChoreAssignmentController(ChoreAssignmentService choreAssignmentService, AuthorizationService authz) {
        this.choreAssignmentService = choreAssignmentService;
        this.authz = authz;
    }

    @Operation(summary = "Get all daily assignments", description = "Fetches all assignments of trainees to chores for specific days. Permission: day-assignment:read:all or day-assignment:read:team", security = {@SecurityRequirement(name = "JWT-Token")})
    @GetMapping
    public ResponseEntity<List<ChoreAssignmentDTO>> getAllAssignments(Authentication auth) {
        return ResponseEntity.ok(choreAssignmentService.getAssignments(auth));
    }

    @Operation(summary = "Create a daily assignment", description = "Assigns a trainee to a chore on a specific day. Permission: day-assignment:write:team")
    @PostMapping
    @PreAuthorize("@authz.isInMonthAssignmentTeam(authentication, #dto.monthAssignmentId)")
    public ResponseEntity<ChoreAssignmentDTO> createAssignment(@Valid @RequestBody CreateChoreAssignmentDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(choreAssignmentService.createAssignment(dto));
    }

    @Operation(summary = "Delete a daily assignment", description = "Removes a daily assignment. Permission: day-assignment:write:team")
    @DeleteMapping("/{id}")
    @PreAuthorize("@authz.canAccessChoreAssignment(authentication, #id)")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Integer id) {
        choreAssignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }
}
