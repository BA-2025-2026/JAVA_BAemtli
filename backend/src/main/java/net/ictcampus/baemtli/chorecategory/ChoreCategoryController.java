package net.ictcampus.baemtli.chorecategory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.ictcampus.baemtli.chorecategory.dto.ChoreCategoryDTO;
import net.ictcampus.baemtli.chorecategory.dto.CreateChoreCategoryDTO;
import net.ictcampus.baemtli.chorecategory.dto.UpdateChoreCategoryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ChoreCategoryController", description = "Processes /chorecategories endpoint requests")
@RestController
@RequestMapping("/chorecategories")
public class ChoreCategoryController {

    private final ChoreCategoryService choreCategoryService;

    public ChoreCategoryController(ChoreCategoryService choreCategoryService) {
        this.choreCategoryService = choreCategoryService;
    }

    @Operation(summary = "Retrieve all chore categories", description = "Fetches a list of all existing chore categories. Permission: chore-category:read:all", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of chore categories", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ChoreCategoryDTO.class)))})
    })
    @GetMapping
    public ResponseEntity<List<ChoreCategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(choreCategoryService.getAllCategories());
    }

    @Operation(summary = "Retrieve a chore category by ID", description = "Fetches a single chore category by its unique ID. Permission: chore-category:read:all", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Chore category found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ChoreCategoryDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Chore category not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ChoreCategoryDTO> getCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(choreCategoryService.getCategoryById(id));
    }

    @Operation(summary = "Create a new chore category", description = "Creates a new chore category. Permission: chore-category:write:all", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successfully created chore category"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "409", description = "Name already exists")
    })
    @PostMapping
    public ResponseEntity<ChoreCategoryDTO> createCategory(@Valid @RequestBody CreateChoreCategoryDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(choreCategoryService.createCategory(dto));
    }

    @Operation(summary = "Update an existing chore category", description = "Updates chore category details. Permission: chore-category:write:all", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated chore category"),
            @ApiResponse(responseCode = "404", description = "Chore category not found"),
            @ApiResponse(responseCode = "409", description = "Name already exists")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ChoreCategoryDTO> updateCategory(@PathVariable Integer id, @Valid @RequestBody UpdateChoreCategoryDTO dto) {
        return ResponseEntity.ok(choreCategoryService.updateCategory(id, dto));
    }

    @Operation(summary = "Delete a chore category", description = "Deletes chore category with provided id. Permission: chore-category:write:all", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully deleted chore category"),
            @ApiResponse(responseCode = "404", description = "Chore category not found"),
            @ApiResponse(responseCode = "409", description = "Chore category is still in use")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        choreCategoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
