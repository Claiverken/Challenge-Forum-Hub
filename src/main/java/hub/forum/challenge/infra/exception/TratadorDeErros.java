package hub.forum.challenge.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    // Erro 404
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> tratarErro404(HttpServletRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var erro = new ApiErrorResponse(status.value(), status.getReasonPhrase(), "Recurso não encontrado", request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    // Erro 400 (Validação) - Mantendo os detalhes!
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> tratarErro400(MethodArgumentNotValidException ex, HttpServletRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var details = ex.getFieldErrors().stream().map(ApiErrorResponse.ValidationErrorDetail::new).toList();
        var erro = new ApiErrorResponse(status.value(), status.getReasonPhrase(), "Erro de validação", request.getRequestURI(), details);
        return ResponseEntity.status(status).body(erro);
    }

    // Erro 400 (JSON Malformado)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> tratarErro400(HttpMessageNotReadableException ex, HttpServletRequest request) {
        var status = HttpStatus.BAD_REQUEST;
        var erro = new ApiErrorResponse(status.value(), status.getReasonPhrase(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    // Erro 401 (Credenciais Inválidas)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> tratarErroBadCredentials(HttpServletRequest request) {
        var status = HttpStatus.UNAUTHORIZED;
        var erro = new ApiErrorResponse(status.value(), status.getReasonPhrase(), "Credenciais inválidas", request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    // Erro 401 (Falha na Autenticação)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> tratarErroAuthentication(HttpServletRequest request) {
        var status = HttpStatus.UNAUTHORIZED;
        var erro = new ApiErrorResponse(status.value(), status.getReasonPhrase(), "Falha na autenticação", request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    // Erro 403 (Acesso Negado)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> tratarErroAcessoNegado(HttpServletRequest request) {
        var status = HttpStatus.FORBIDDEN;
        var erro = new ApiErrorResponse(status.value(), status.getReasonPhrase(), "Acesso negado", request.getRequestURI());
        return ResponseEntity.status(status).body(erro);
    }

    // Erro 500 (Erro Genérico)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> tratarErro500(Exception ex, HttpServletRequest request) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var erro = new ApiErrorResponse(status.value(), status.getReasonPhrase(), "Erro interno do servidor", request.getRequestURI());
        // Em ambiente de desenvolvimento, podes querer logar o erro completo: ex.printStackTrace();
        return ResponseEntity.status(status).body(erro);
    }
}