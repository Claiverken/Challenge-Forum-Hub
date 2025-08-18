package hub.forum.challenge.domain.answer;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroResposta(
        @NotBlank
        String message
) {
}