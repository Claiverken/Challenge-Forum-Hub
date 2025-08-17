package hub.forum.challenge.domain.answer;

import java.time.LocalDateTime;

public record DadosResposta(
        Long id,
        String message,
        LocalDateTime creationDate,
        String authorName) {

    public DadosResposta(Answer resposta) {
        this(resposta.getId(), resposta.getMessage(), resposta.getCreationDate(), resposta.getAuthor().getName());
    }
}
