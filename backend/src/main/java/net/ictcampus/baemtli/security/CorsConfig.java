package net.ictcampus.baemtli.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Wir werden später ein Frontend auf dem Port 3000 laufen lassen und dieses will konstant
 * Daten auf unserem Backend auf dem Port 8080. Da diese 2 Ports einander nicht vertrauen,
 * entstehen CORS (Cross Origin Request Sharing) Fehler.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // Load global API prefix from application.properties
    @Value("${baemtli.api.prefix}")
    private String apiPrefix;

    // CORS Configuration for K8s deployment
    @Value("${baemtli.cors.allowed-origins:http://localhost:3000}")
    private String allowedOrigins;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // Fügt /api/v1 vor alle Klassen, die mit @RestController annotiert sind
        // dies sorgt für eine saubere Trennung zwischen Frontend und Backend auf URL-Ebene
        configurer.addPathPrefix(apiPrefix,
                c -> c.isAnnotationPresent(RestController.class));
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping(apiPrefix + "/**")
                        // Schutz vor CSRF-Angriffen
                        // Welche URL darf auf das Backend zugreifen?
                        .allowedOrigins(allowedOrigins.split(","))
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        // Welche Header werden akzeptiert? Bspw. Token kommen in Authorization. * ist eine Wildcard
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
