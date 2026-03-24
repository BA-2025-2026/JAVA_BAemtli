package net.ictcampus.baemtli.trainee;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.ictcampus.baemtli.security.AuthorizationService;
import net.ictcampus.baemtli.trainee.dto.CreateTraineeDTO;
import net.ictcampus.baemtli.trainee.dto.TraineeDTO;
import net.ictcampus.baemtli.trainee.dto.UpdateTraineeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Trainees", description = "Lernende of ICT-Campus are called Trainees")
@RestController
@RequestMapping("/trainees")
public class TraineeController {

    private final TraineeService traineeService;
    private final AuthorizationService authz;

    public TraineeController(TraineeService traineeService, AuthorizationService authz) {
        this.traineeService = traineeService;
        this.authz = authz;
    }

    @Operation(summary = "Retrieve trainees", description = "Fetches a list of trainees, filtered by team if not a coach. Permission: trainee:read:all or trainee:read:team", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of trainees", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TraineeDTO.class)))})
    })
    @GetMapping
    public ResponseEntity<List<TraineeDTO>> getTrainees(Authentication auth, @RequestParam(required = false) Integer teamId) {
        return ResponseEntity.ok(traineeService.getTrainees(auth, teamId));
    }

    @Operation(summary = "Retrieve a trainee by ID", description = "Fetches a single trainee by their unique ID. Permission: trainee:read:all or trainee:read:team (own team only)", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trainee found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TraineeDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Trainee not found")
    })
    @GetMapping("/{id}")
    @PreAuthorize("@authz.canAccessTrainee(authentication, #id)")
    public ResponseEntity<TraineeDTO> getTrainee(@PathVariable Integer id) {
        return ResponseEntity.ok(traineeService.getTraineeById(id));
    }

    @Operation(summary = "Create a new trainee", description = "Creates a new trainee. Permission: trainee:write:all", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successfully created trainee"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    public ResponseEntity<TraineeDTO> createTrainee(@Valid @RequestBody CreateTraineeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(traineeService.createTrainee(dto));
    }

    @Operation(summary = "Update an existing trainee", description = "Updates trainee details. Permission: trainee:write:all", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated trainee"),
            @ApiResponse(responseCode = "404", description = "Trainee not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<TraineeDTO> updateTrainee(@PathVariable Integer id, @Valid @RequestBody UpdateTraineeDTO dto) {
        return ResponseEntity.ok(traineeService.updateTrainee(id, dto));
    }

    @Operation(summary = "Delete a trainee", description = "Deletes trainee with provided id. Permission: trainee:write:all", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully deleted trainee"),
            @ApiResponse(responseCode = "404", description = "Trainee not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainee(@PathVariable Integer id) {
        traineeService.deleteTrainee(id);
        return ResponseEntity.noContent().build();
    }
}
