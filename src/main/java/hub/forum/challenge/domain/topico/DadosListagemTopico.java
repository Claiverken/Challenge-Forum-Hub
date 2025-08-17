package hub.forum.challenge.domain.topico;

import java.time.LocalDateTime;

// DTO para exibir apenas os dados essenciais na listagem de tópicos.
public record DadosListagemTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao) {

    // Construtor adicional que recebe uma entidade Topico e extrai os dados necessários.
    public DadosListagemTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao());
    }
}
