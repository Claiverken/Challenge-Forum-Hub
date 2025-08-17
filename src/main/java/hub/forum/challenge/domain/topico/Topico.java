package hub.forum.challenge.domain.topico;

import hub.forum.challenge.domain.curso.Curso;
import hub.forum.challenge.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao = LocalDateTime.now();
    private Boolean status = true; // true para ativo, false para fechado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Garante que a coluna no banco não pode ser nula
    private User autor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    // Construtor para criação de um novo tópico (sem o id)
    public Topico(String titulo, String mensagem, User autor, Curso curso) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.autor = autor;
        this.curso = curso;
    }

    public void atualizarInformacoes(String titulo, String mensagem) {
        if (titulo != null) {
            this.titulo = titulo;
        }
        if (mensagem != null) {
            this.mensagem = mensagem;
        }
    }

    public void fechar() {
        this.status = false;
    }
}
