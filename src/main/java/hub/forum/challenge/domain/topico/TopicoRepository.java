package hub.forum.challenge.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    // O Spring Data JPA vai entender o nome do método e criar a query:
    // "SELECT * FROM topicos WHERE status = true"
    List<Topico> findAllByStatusTrue();
}
