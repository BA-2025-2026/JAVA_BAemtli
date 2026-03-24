package net.ictcampus.baemtli.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Wir werden später ein Frontend auf dem Port 3000 laufen lassen und dieses will konstant
 * Daten auf unserem Backend auf dem Port 8080. Da diese 2 Ports einander nicht vertrauen,
 * entstehen CORS (Cross Origin Request Sharing) Fehler.
 */
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        // Schutz vor CSRF-Angriffen
                        // Welche URL darf auf das Backend zugreifen? Können auch IP's sein
                        .allowedOrigins("http://localhost:3000") // ggf. hier noch eigene IP + Teamkollege hinzufügen: "http://172.16.2.108:3000"                        // Mit welcher HTTP Function darf zugegriffen werden?
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        // Welche Header werden akzeptiert? Bspw. Token kommen in Authorization. * ist eine Wildcard
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
