package net.ictcampus.baemtli.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import net.ictcampus.baemtli.auth.dto.AuthUserDTO;
import net.ictcampus.baemtli.auth.jwt.JwtService;
import net.ictcampus.baemtli.auth.jwt.dto.JwtDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public AuthenticationController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    // Register might be implemented later
    // For now, users can be created manually in the db
//    @Operation(summary = "Create a new User", description = "Registers a new user in the database. Permission: trainee:write:all", security = {@SecurityRequirement(name = "JWT-Token")})
//    @ApiResponses({
//            @ApiResponse(responseCode = "201", description = "User successfully created"),
//            @ApiResponse(responseCode = "401", description = "Invalid request data"),
//            @ApiResponse(responseCode = "403", description = "Forbidden")
//    })
//    @PostMapping(value = "/register", consumes = "application/json")
//    @org.springframework.security.access.prepost.PreAuthorize("hasAuthority('trainee:write:all')")
//    public ResponseEntity<Void> register(@Valid @RequestBody CreateUserDTO createUserDTO) {
//        authenticationService.signup(createUserDTO);
//        return ResponseEntity.status(201).build();
//    }

    @Operation(summary = "Login user", description = "Authenticates a user and returns an access token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully authenticated", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = JwtDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Invalid username or password")
    })
    @PostMapping("/login")
    public ResponseEntity<JwtDTO> authenticate(@Valid @RequestBody AuthUserDTO authUserDto) {
        // Expects UserDetails, we're working with User still... So let's change the model (add Methods)
        String token = jwtService.generateToken(authenticationService.authenticate(authUserDto));
        JwtDTO jwtDTO = new JwtDTO()
                .setAccessToken("Bearer " + token)
                .setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(jwtDTO);
    }
}

