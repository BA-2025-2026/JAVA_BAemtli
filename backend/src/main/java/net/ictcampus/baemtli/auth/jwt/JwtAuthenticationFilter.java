package net.ictcampus.baemtli.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.ictcampus.baemtli.security.SecurityConstants;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

 /**
  * Spring Security Filter Chain (setzt Security Context)
 *  <pre>{@code
  *  --- Servlet Container ---
 *  HTTP Request
 *  ↓
 *  CorsFilter
 *  ↓
 *  CsrfFilter
 *  ↓
 *  JwtAuthenticationFilter  ← dieses File
 *  ↓
 *  UsernamePasswordAuthenticationFilter
 *  ↓
 *  AuthorizationFilter
 *  ↓ (Dispatcher Servlet)
 *  Controller
 *  }</pre>
 * Checks every request for an http header containing authorization information
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Zentraler Exception-Resolver, um Security/Parsing-Fehler sauber zu serialisieren.
     */
    private final HandlerExceptionResolver handlerExceptionResolver;
    /**
     * Service zum Parsen/Validieren des JWT (Claims, Ablauf, Signatur).
     */
    private final JwtService jwtService;
    /**
     * Standard Spring-Service, um Benutzerinformationen anhand des Usernames zu laden.
     */
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(HandlerExceptionResolver handlerExceptionResolver, JwtService jwtService, UserDetailsService userDetailsService) {
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Validierung vom Token
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Erwarteter Headername und Prefix sind zentral in SecurityConstants definiert,
        // z. B. HEADER_STRING="Authorization", TOKEN_PREFIX="Bearer ".
        final String authHeader = request.getHeader(SecurityConstants.HEADER_STRING);

        // Abbruch, wenn kein authorization-Header oder anderer Auth-Typ:
        // Öffentliche Endpoints können dennoch aufgerufen werden.
        if (authHeader == null || !authHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Token aus dem Header schneiden: "Bearer " hat 7 Zeichen; hier bewusst anhand des Prefix.
            final String jwt = authHeader.substring(SecurityConstants.TOKEN_PREFIX.length());
            // Username (typischerweise 'sub' Claim) aus dem Token extrahieren. sub = subject
            final String username = jwtService.extractUsername(jwt);

            // Prüfen, ob bereits eine Authentication im Context vorhanden ist (z. B. durch vorgelagerten Filter).
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Nur wenn wir einen Benutzer aus dem Token erhalten und noch niemand authentifiziert ist,
            // laden wir die UserDetails und validieren das Token
            if (username != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // Wenn Token abgelaufen ist, gibt nichts zurück, auch wenn ohne JWT möglich wäre
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Authentication-Objekt bauen
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // Security Context (wer ist aktuell eingeloggt?) setzen
                    // Dieser enthält: UserDetails, username, roles
                    // Gilt nur für den aktuellen Request-Thread
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            System.out.println("JWTAuth will call next filter.");
            // Filter-Chain funktioniert wie eine Zwiebel, äussere Schicht ruft eine weiter innen auf
            // und pausiert so lange, bis diese (und deren inneren Schichten!) fertig sind
            // Führt die nächsten Filter aus und springt dann schrittweise zurück
            filterChain.doFilter(request, response);

            System.out.println("JWTAuth Filter ended.");
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }

}
