package net.ictcampus.baemtli.exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.security.SignatureException;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Vertrauenswürdige Fehlerquellen (z.B. Jakarta Beans Validation): Fehlernachrichten sind selbst geschrieben
 * und haben keine vertrauenswürdigen Infos: Kann an API-Aufrufer weitergegeben werden.
 * RuntimeException / Exception: Hier muss eine generische Fehlermeldung geschrieben werden,
 * da ein Stack-Trace vertrauliche Informationen über die Architektur der Applikation
 * preisgeben würde!
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 Bad Request (Validierungsfehler im Controller bei @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        List<ApiError.FieldValidationError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::mapFieldError)
                .collect(toList());

        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                fieldErrors
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 400 Bad Request -> Illegal Argument (ex. Trainee doesn't belong to the team assigned to this chore)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // 404 Not Found (z.B. angegebene userId oder ein Foreign Key wurde nicht gefunden)
    // INFO: Fals ein nicht existierender Pfad (z.B. /users/aninventedpath angefragt wird,
    // gibt Spring ebenfalls ein 404, aber noch bevor es den Controller erreicht.
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFound(EntityNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 405 Method Not Allowed (the requested HTTP Method has not been implemented in this backend application)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return buildError(HttpStatus.METHOD_NOT_ALLOWED, "This HTTP method is not supported.");
    }

    // 409 Conflict (z.B. wenn ein Username schon existiert)
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ApiError> handleEntityExists(EntityExistsException ex) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    // 409 Conflict (z.B. Fremdschlüsselverletzung beim Löschen)
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(org.springframework.dao.DataIntegrityViolationException ex) {
        return buildError(HttpStatus.CONFLICT, "Resource is still in use and cannot be deleted.");
    }

    // AUTH
    // 401 Unauthorized (Falsche Logindaten)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(BadCredentialsException ex) {
        return buildError(HttpStatus.UNAUTHORIZED, "Username or password is incorrect.");
    }

    // 401 Unauthorized (JWT expired)
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiError> handleJwtExpired(ExpiredJwtException ex) {
        return buildError(HttpStatus.UNAUTHORIZED, "JWT has expired.");
    }

    // 401 Unauthorized (JWT invalid)
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ApiError> handleJwtInvalid(SignatureException ex) {
        return buildError(HttpStatus.UNAUTHORIZED, "Invalid JWT signature.");
    }

    // 403 Forbidden
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex) {
        return buildError(HttpStatus.FORBIDDEN, "You don't have permission to access this resource.");
    }

    // RuntimeException
    // 500 Internal Server Error (Fallback, very general)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleGeneralRuntimeException(RuntimeException ex) {
        // Logging
        System.out.println("This RuntimeException generated Code 500: " + ex.getMessage());
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
    }

    // Exception
    // 500 Internal Server Error (Fallback, very general)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception ex) {
        // Logging
        System.out.println("This Exception generated Code 500: " + ex.getMessage());
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
    }

    // Wir wissen nicht, ob 1, 2 oder mehrere Felder failen
    private ApiError.FieldValidationError mapFieldError(FieldError error) {
        return new ApiError.FieldValidationError(error.getField(), error.getDefaultMessage());
    }

    // Logik zum Erstellen einer ResponseEntity<ApiError>, damit nicht jede Methode es selbst machen muss
    // nimmt HttpStatus und eine Message entgegen und gibt eine ResponseEntity zurück.
    private ResponseEntity<ApiError> buildError(HttpStatus status, String message) {
        return new ResponseEntity<>(new ApiError(status.value(), message), status);
    }
}

