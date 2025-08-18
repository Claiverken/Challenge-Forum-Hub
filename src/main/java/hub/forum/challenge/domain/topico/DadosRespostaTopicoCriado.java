package hub.forum.challenge.domain.topico;

import java.time.LocalDateTime;

public record DadosRespostaTopicoCriado(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao) {

    public DadosRespostaTopicoCriado(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao());
    }
}
