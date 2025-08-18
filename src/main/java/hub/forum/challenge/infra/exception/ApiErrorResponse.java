package hub.forum.challenge.infra.exception;

import org.springframework.validation.FieldError;
import java.time.LocalDateTime;

public record ApiErrorResponse(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        Object details, // Campo flexível para detalhes extras
        String path
) {
    // Construtor para erros gerais
    public ApiErrorResponse(int status, String error, String message, String path) {
        this(LocalDateTime.now(), status, error, message, null, path);
    }

    // Construtor para erros de validação
    public ApiErrorResponse(int status, String error, String message, String path, java.util.List<ValidationErrorDetail> details) {
        this(LocalDateTime.now(), status, error, message, details, path);
    }

    // Record interno para os detalhes da validação
    public record ValidationErrorDetail(String field, String description) {
        public ValidationErrorDetail(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
