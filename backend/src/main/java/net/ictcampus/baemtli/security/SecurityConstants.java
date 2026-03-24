package net.ictcampus.baemtli.security;

/**
 * In den SecurityConstants definieren wir statische Werte, welche zum Beispiel zum Token gehören.
 * Dinge wie Ablaufdatum, Header-String (wo erwarten wir das Token später), Token Secret usw.
 * Kurze Randnotiz: Ja das Secret des Tokens im Code zu haben ist enorm schlechter Code.
 * Dies haben wir gemacht, da es sehr einfach geht das Token so zu signieren.
 * Ansonsten müssten wir noch alle einen sicheren Codespace im GitHub erstellen.
 */
public class SecurityConstants {
    public static final String SECRET = "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b";
    public static final long EXPIRATION_TIME = 31556952000L; // 1 Year in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String[] API_DOCUMENTATION_URLS = {
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**"
    };
}
