package net.ictcampus.baemtli.exception;

import lombok.*;

import java.util.List;

/**
 * Diese Klasse erhält alle Fehler, die während einem Request anfallen
 * und kann dann in der Response ausgegeben werden.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    private int status;
    private String message;
    private List<FieldValidationError> fieldErrors;

    @Data
    @Getter @Setter // TODO: Nicht notwendig?
    @AllArgsConstructor
    public static class FieldValidationError {
        // Bei Verletzung einer Validationannotation kommt das Feld mit.
        private String field;
        private String error;
    }

    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
    }
}

