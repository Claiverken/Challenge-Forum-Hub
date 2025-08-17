package hub.forum.challenge.domain.topico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import hub.forum.challenge.domain.answer.DadosResposta;
import hub.forum.challenge.domain.topico.StatusTopico;

// DTO para exibir todos os dados de um tópico ao ser consultado individualmente.
public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        StatusTopico status,
        String autor,
        String curso,
        String categoriaCurso,
        List<DadosResposta> respostas
) {
    public DadosDetalhamentoTopico(Topico topico) {
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),
                topico.getAutor().getName(),
                topico.getCurso().getNome(),
                topico.getCurso().getCategoria(),
                // Transforma a lista de entidades Answer em uma lista de DTOs DadosResposta
                topico.getRespostas().stream()
                        .map(DadosResposta::new)
                        .collect(Collectors.toList())
        );
    }
}