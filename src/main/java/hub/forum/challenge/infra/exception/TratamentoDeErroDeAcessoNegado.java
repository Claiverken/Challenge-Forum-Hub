package hub.forum.challenge.infra.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TratamentoDeErroDeAcessoNegado implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    // 1. Injeta o ObjectMapper configurado pelo Spring
    public TratamentoDeErroDeAcessoNegado(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        var erro = new ApiErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                "Acesso negado",
                request.getRequestURI()
        );

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        // 2. Usa o objectMapper injetado
        response.getWriter().write(objectMapper.writeValueAsString(erro));
    }
}