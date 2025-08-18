package hub.forum.challenge.domain.answer;

import hub.forum.challenge.domain.topico.Topico;
import hub.forum.challenge.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Answer")
@Table(name = "answers")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    @Column(name = "creation_date")
    private LocalDateTime creationDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;


    public Answer(DadosCadastroResposta dados, User autor, Topico topico) {
        this.message = dados.message();
        this.author = autor;
        this.topico = topico;
        this.creationDate = LocalDateTime.now();
    }
}
