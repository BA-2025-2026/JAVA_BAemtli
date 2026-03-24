package net.ictcampus.baemtli.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.ictcampus.baemtli.auth.jwt.JwtService;
import net.ictcampus.baemtli.auth.jwt.dto.JwtDTO;
import net.ictcampus.baemtli.user.dto.UpdateUserDTO;
import net.ictcampus.baemtli.user.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "User accounts of the Ämtliplantool app. Coaches have an account too.")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    // POST (createUser) is handled by AuthenticationService.signup()

    //  GET
    @Operation(summary = "Retrieve all users", description = "Fetches a list of all registered users. Permission: trainee:read:all", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))})
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @Operation(summary = "Retrieve a user by ID", description = "Fetches a single user by their unique ID. Permission: trainee:read:all or self", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    @PreAuthorize("@authz.canAccessUser(authentication, #id)")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUser(id));
    }


    // PUT
    @Operation(summary = "Update an existing User", description = "Updates user details based on provided information", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated user", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = JwtDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "Failed to update user")
    })
    @PutMapping(value = "/{id}", consumes = "application/json")
    @PreAuthorize("@authz.canAccessUser(authentication, #id)") // User darf nur sich selbst updaten,
    public ResponseEntity<JwtDTO> update(@PathVariable Integer id, @Valid @RequestBody UpdateUserDTO user) { // @Valid: Fail Fast Prinzip
        User updatedUser = userService.updateUser(id, user);

        String token = jwtService.generateToken(updatedUser);

        JwtDTO jwtDTO = new JwtDTO()
                .setAccessToken("Bearer " + token)
                .setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(jwtDTO);
    }


    // DELETE
    @Operation(summary = "Delete a User", description = "Deletes user with provided id", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully deleted user"),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("@authz.canAccessUser(authentication, #id)") // User darf nur sich selbst löschen
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
