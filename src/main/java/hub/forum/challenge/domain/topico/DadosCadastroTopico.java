package hub.forum.challenge.domain.topico;

import jakarta.validation.constraints.NotBlank;

// Usa records para criar uma classe imutável e concisa para transferência de dados.
// @NotBlank é uma anotação de validação que garante que o campo não seja nulo nem vazio.
public record DadosCadastroTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        @NotBlank
        String autor,
        @NotBlank
        String curso,
        @NotBlank
        String categoria) {
}
