package net.ictcampus.baemtli.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Wir wollen einen User nicht zwingen, username, E-Mail und passwort anzugeben, wenn er sich anmelden möchte.
 * Dies soll er einfach mit Usernamen und passwort können.
 */
@Getter
@Setter
@Accessors(chain = true)
public class AuthUserDTO {

    @NotBlank(message = "Username is required")
    private String username;

    @Size(min = 6, message = "Password must be at least 6 characters long.")
    @Size(max = 255, message = "Password must be at most 255 characters long.")
    private String password;
}
