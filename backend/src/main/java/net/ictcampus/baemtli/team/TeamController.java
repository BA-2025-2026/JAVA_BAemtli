package net.ictcampus.baemtli.team;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.ictcampus.baemtli.team.dto.CreateTeamDTO;
import net.ictcampus.baemtli.team.dto.TeamDTO;
import net.ictcampus.baemtli.team.dto.UpdateTeamDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Teams", description = "At ICT-Campus trainees are organized in teams.")
@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @Operation(summary = "Retrieve all teams", description = "Fetches a list of all existing teams. Permission: team:read:all", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of teams", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TeamDTO.class)))})
    })
    @GetMapping
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @Operation(summary = "Retrieve a team by ID", description = "Fetches a single team by its unique ID. Permission: team:read:all", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Team found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TeamDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Team not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Integer id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @Operation(summary = "Create a new team", description = "Creates a new team. Permission: team:write:all", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successfully created team"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "409", description = "Team name already exists")
    })
    @PostMapping
    public ResponseEntity<TeamDTO> createTeam(@Valid @RequestBody CreateTeamDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teamService.createTeam(dto));
    }

    @Operation(summary = "Update an existing team", description = "Updates team details. Permission: team:write:all", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated team"),
            @ApiResponse(responseCode = "404", description = "Team not found"),
            @ApiResponse(responseCode = "409", description = "Team name already exists")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Integer id, @Valid @RequestBody UpdateTeamDTO dto) {
        return ResponseEntity.ok(teamService.updateTeam(id, dto));
    }

    @Operation(summary = "Delete a team", description = "Deletes team with provided id. Permission: team:write:all", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully deleted team"),
            @ApiResponse(responseCode = "404", description = "Team not found"),
            @ApiResponse(responseCode = "409", description = "Team is still in use")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Integer id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}
