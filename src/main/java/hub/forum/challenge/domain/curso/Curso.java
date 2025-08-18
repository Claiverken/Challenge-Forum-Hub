package hub.forum.challenge.domain.curso;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hub.forum.challenge.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "cursos")
@Entity(name = "Curso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String categoria;

    @OneToMany(mappedBy = "curso")
    @JsonIgnore // Evita loops infinitos na serialização JSON
    private List<Topico> topicos = new ArrayList<>();

    public Curso(String nome, String categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }
}
