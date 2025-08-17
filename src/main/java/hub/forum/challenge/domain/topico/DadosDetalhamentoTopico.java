package hub.forum.challenge.domain.topico;

import java.time.LocalDateTime;

// DTO para exibir todos os dados de um tópico ao ser consultado individualmente.
public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        Boolean status,
        String autor,
        String curso) {

    // Construtor que recebe a entidade e a converte para o DTO.
    public DadosDetalhamentoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getStatus(), topico.getAutor().getName(), topico.getCurso().getNome());
    }
}
