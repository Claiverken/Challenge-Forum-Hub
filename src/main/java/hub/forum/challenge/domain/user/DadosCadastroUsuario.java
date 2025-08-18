package hub.forum.challenge.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DadosCadastroUsuario(
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String password
) {
}