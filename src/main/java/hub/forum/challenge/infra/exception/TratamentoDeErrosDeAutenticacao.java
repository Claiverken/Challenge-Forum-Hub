package hub.forum.challenge.infra.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TratamentoDeErrosDeAutenticacao implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    // 1. Injeta o ObjectMapper configurado pelo Spring
    public TratamentoDeErrosDeAutenticacao(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        var mensagem = "Autenticação necessária. Para aceder a este recurso, é preciso fornecer um token JWT válido no cabeçalho Authorization.";
        var erro = new ApiErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                mensagem,
                request.getRequestURI()
        );

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        // 2. Usa o objectMapper injetado
        response.getWriter().write(objectMapper.writeValueAsString(erro));
    }
}