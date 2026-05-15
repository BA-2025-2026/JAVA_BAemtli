package net.ictcampus.baemtli.security;

import net.ictcampus.baemtli.auth.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Endpoints sichern
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // ermöglicht uns, bis auf die Methode spezifisch festzulegen, wer was darf.
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider){
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Cors konfigurieren, damit unsere Cors-Config benutzt wird.
        http
                .cors(Customizer.withDefaults())
                // csrf disablen -> eigentlich schlecht aber mit csrf wird unser Backend zu komplex
                .csrf(AbstractHttpConfigurer::disable)
                // RESTful (state-less)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // Access Swagger Documentation
                        .requestMatchers(HttpMethod.GET, SecurityConstants.API_DOCUMENTATION_URLS).permitAll()

                        // Unauthenticated Users may log in
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()

                        // --- CHORE CATEGORIES ---
                        .requestMatchers(HttpMethod.GET, "/chorecategories/**").hasAuthority(Permission.CHORE_CATEGORY_READ_ALL)
                        .requestMatchers(HttpMethod.POST, "/chorecategories/**").hasAuthority(Permission.CHORE_CATEGORY_WRITE_ALL)
                        .requestMatchers(HttpMethod.PATCH, "/chorecategories/**").hasAuthority(Permission.CHORE_CATEGORY_WRITE_ALL)
                        .requestMatchers(HttpMethod.DELETE, "/chorecategories/**").hasAuthority(Permission.CHORE_CATEGORY_WRITE_ALL)

                        // --- TEAMS ---
                        .requestMatchers(HttpMethod.GET, "/teams/**").permitAll()
                        // .requestMatchers(HttpMethod.GET, "/teams/**").hasAuthority(Permission.TEAM_READ_ALL)
                        .requestMatchers(HttpMethod.POST, "/teams/**").permitAll()
                        //.requestMatchers(HttpMethod.POST, "/teams/**").hasAuthority(Permission.TEAM_WRITE_ALL)
                        .requestMatchers(HttpMethod.PATCH, "/teams/**").permitAll()
                        // .requestMatchers(HttpMethod.PATCH, "/teams/**").hasAuthority(Permission.TEAM_WRITE_ALL)
                        .requestMatchers(HttpMethod.DELETE, "/teams/**").permitAll()
                        // .requestMatchers(HttpMethod.DELETE, "/teams/**").hasAuthority(Permission.TEAM_WRITE_ALL)

                        // --- TRAINEES ---
                        // Hier brauchen wir hasAnyAuthority, da sowohl Coaches (:all) als auch Lernende/Teamverantwortliche (:team) Lesezugriff haben
                        .requestMatchers(HttpMethod.GET, "/trainees/**").hasAnyAuthority(Permission.TRAINEE_READ_ALL, Permission.TRAINEE_READ_TEAM)
                        .requestMatchers(HttpMethod.POST, "/trainees/**").hasAuthority(Permission.TRAINEE_WRITE_ALL)
                        .requestMatchers(HttpMethod.PATCH, "/trainees/**").hasAuthority(Permission.TRAINEE_WRITE_ALL)
                        .requestMatchers(HttpMethod.DELETE, "/trainees/**").hasAuthority(Permission.TRAINEE_WRITE_ALL)

                        // --- MONATSZUTEILUNGEN ---
                        .requestMatchers(HttpMethod.GET, "/monthassignments/**").hasAuthority(Permission.MONTH_ASSIGNMENT_READ_ALL)
                        .requestMatchers(HttpMethod.POST, "/monthassignments/**").hasAuthority(Permission.MONTH_ASSIGNMENT_WRITE_ALL)
                        .requestMatchers(HttpMethod.PATCH, "/monthassignments/**").hasAuthority(Permission.MONTH_ASSIGNMENT_WRITE_ALL)
                        .requestMatchers(HttpMethod.DELETE, "/monthassignments/**").hasAuthority(Permission.MONTH_ASSIGNMENT_WRITE_ALL)

                        // --- TAGESEINTEILUNGEN (DAY ASSIGNMENTS) ---
                        // Annahme basierend auf der Matrix, auch wenn die Endpoints noch nicht voll definiert sind
                        .requestMatchers(HttpMethod.GET, "/dayassignments/**").hasAnyAuthority(Permission.DAY_ASSIGNMENT_READ_ALL, Permission.DAY_ASSIGNMENT_READ_TEAM)
                        .requestMatchers(HttpMethod.POST, "/dayassignments/**").hasAuthority(Permission.DAY_ASSIGNMENT_WRITE_TEAM)
                        .requestMatchers(HttpMethod.PATCH, "/dayassignments/**").hasAuthority(Permission.DAY_ASSIGNMENT_WRITE_TEAM)
                        .requestMatchers(HttpMethod.DELETE, "/dayassignments/**").hasAuthority(Permission.DAY_ASSIGNMENT_WRITE_TEAM)

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                // Prüfung: Gibts im Header ein Authorization-String?
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
