package net.ictcampus.baemtli.security;

import net.ictcampus.baemtli.auth.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
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

    // Load global API prefix from application.properties
    @Value("${baemtli.api.prefix}")
    private String apiPrefix;

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


                        // Swagger Documentation
                        // Load resources
                        .requestMatchers(HttpMethod.GET, apiPrefix + "/v3/api-docs/**").permitAll()
                        // Load UI
                        .requestMatchers(HttpMethod.GET, "/swagger-ui.html", "/swagger-ui/**").permitAll()

                        // Access Swagger Documentation
                        .requestMatchers(HttpMethod.GET, SecurityConstants.API_DOCUMENTATION_URLS).permitAll()

                        // Unauthenticated Users may log in
                        .requestMatchers(HttpMethod.POST, apiPrefix + "/auth/login").permitAll()

                        // --- CHORE CATEGORIES ---
                        .requestMatchers(HttpMethod.GET, apiPrefix + "/chorecategories/**").hasAuthority(Permission.CHORE_CATEGORY_READ_ALL)
                        .requestMatchers(HttpMethod.POST, apiPrefix + "/chorecategories/**").hasAuthority(Permission.CHORE_CATEGORY_WRITE_ALL)
                        .requestMatchers(HttpMethod.PATCH, apiPrefix + "/chorecategories/**").hasAuthority(Permission.CHORE_CATEGORY_WRITE_ALL)
                        .requestMatchers(HttpMethod.DELETE, apiPrefix + "/chorecategories/**").hasAuthority(Permission.CHORE_CATEGORY_WRITE_ALL)

                        // --- TEAMS ---
                        // TESTING
                        .requestMatchers(HttpMethod.GET, apiPrefix + "/teams/**").permitAll()
                        .requestMatchers(HttpMethod.POST, apiPrefix + "/teams/**").permitAll()
//                      .requestMatchers(HttpMethod.GET, apiPrefix + "/teams/**").hasAuthority(Permission.TEAM_READ_ALL)
                        .requestMatchers(HttpMethod.POST, apiPrefix + "/teams/**").hasAuthority(Permission.TEAM_WRITE_ALL)
                        .requestMatchers(HttpMethod.PATCH, apiPrefix + "/teams/**").hasAuthority(Permission.TEAM_WRITE_ALL)
                        .requestMatchers(HttpMethod.DELETE, apiPrefix + "/teams/**").hasAuthority(Permission.TEAM_WRITE_ALL)

                        // --- TRAINEES ---
                        // Hier brauchen wir hasAnyAuthority, da sowohl Coaches (:all) als auch Lernende/Teamverantwortliche (:team) Lesezugriff haben
                        .requestMatchers(HttpMethod.GET, apiPrefix + "/trainees/**").hasAnyAuthority(Permission.TRAINEE_READ_ALL, Permission.TRAINEE_READ_TEAM)
                        .requestMatchers(HttpMethod.POST, apiPrefix + "/trainees/**").hasAuthority(Permission.TRAINEE_WRITE_ALL)
                        .requestMatchers(HttpMethod.PATCH, apiPrefix + "/trainees/**").hasAuthority(Permission.TRAINEE_WRITE_ALL)
                        .requestMatchers(HttpMethod.DELETE, apiPrefix + "/trainees/**").hasAuthority(Permission.TRAINEE_WRITE_ALL)

                        // --- MONATSZUTEILUNGEN ---
                        .requestMatchers(HttpMethod.GET, apiPrefix + "/monthassignments/**").hasAuthority(Permission.MONTH_ASSIGNMENT_READ_ALL)
                        .requestMatchers(HttpMethod.POST, apiPrefix + "/monthassignments/**").hasAuthority(Permission.MONTH_ASSIGNMENT_WRITE_ALL)
                        .requestMatchers(HttpMethod.PATCH, apiPrefix + "/monthassignments/**").hasAuthority(Permission.MONTH_ASSIGNMENT_WRITE_ALL)
                        .requestMatchers(HttpMethod.DELETE, apiPrefix + "/monthassignments/**").hasAuthority(Permission.MONTH_ASSIGNMENT_WRITE_ALL)

                        // --- TAGESEINTEILUNGEN (DAY ASSIGNMENTS) ---
                        // Annahme basierend auf der Matrix, auch wenn die Endpoints noch nicht voll definiert sind
                        .requestMatchers(HttpMethod.GET, apiPrefix + "/dayassignments/**").hasAnyAuthority(Permission.DAY_ASSIGNMENT_READ_ALL, Permission.DAY_ASSIGNMENT_READ_TEAM)
                        .requestMatchers(HttpMethod.POST, apiPrefix + "/dayassignments/**").hasAuthority(Permission.DAY_ASSIGNMENT_WRITE_TEAM)
                        .requestMatchers(HttpMethod.PATCH, apiPrefix + "/dayassignments/**").hasAuthority(Permission.DAY_ASSIGNMENT_WRITE_TEAM)
                        .requestMatchers(HttpMethod.DELETE, apiPrefix + "/dayassignments/**").hasAuthority(Permission.DAY_ASSIGNMENT_WRITE_TEAM)

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                // Prüfung: Gibts im Header ein Authorization-String?
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
