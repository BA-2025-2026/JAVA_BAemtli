package net.ictcampus.baemtli.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.ictcampus.baemtli.team.Team;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "`user`")
@Getter @Setter
@NoArgsConstructor
@Accessors(chain = true)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_User")
    private Integer id;

    @Column(name = "Username", unique = true, nullable = false, length = 50)
    @NotBlank(message = "Username is required")
    private String username;

    @Column(name = "PasswordHash", nullable = false, length = 255)
    @Schema(hidden = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 255, message = "Password must be between 6 and 255 characters")
    private String password;

    @Column(name = "Role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "AuthSource", nullable = false, length = 10)
    private String authSource = "LOCAL";

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "Team_ID", nullable = false) // TODO: Coaches don't have a team
    private Team team;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<org.springframework.security.core.authority.SimpleGrantedAuthority> authorities = new java.util.ArrayList<>(
                role.getPermissions().stream()
                        .map(org.springframework.security.core.authority.SimpleGrantedAuthority::new)
                        .toList()
        );
        authorities.add(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + role.name()));
        return authorities;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
