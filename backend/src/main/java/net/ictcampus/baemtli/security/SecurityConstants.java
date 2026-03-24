package net.ictcampus.baemtli.security;

/**
 * In den SecurityConstants definieren wir statische Werte, welche zum Beispiel zum Token gehören.
 * Dinge wie Ablaufdatum, Header-String (wo erwarten wir das Token später), Token Secret usw.
 */
public class SecurityConstants {

    // JWT Secret is set and loaded from application.properties
    public static final long EXPIRATION_TIME = 31556952000L; // 1 Year in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String[] API_DOCUMENTATION_URLS = {
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**"
    };
}
