package hub.forum.challenge.domain.topico;

// DTO para a atualização. Os campos são opcionais, o usuário pode querer atualizar só o título ou só a mensagem.
public record DadosAtualizacaoTopico(
        String titulo,
        String mensagem) {
}