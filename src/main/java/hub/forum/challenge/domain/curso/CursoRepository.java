package hub.forum.challenge.domain.curso;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    // Método para buscar um curso pelo nome
    Optional<Curso> findByNome(String nome);
}
