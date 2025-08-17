package hub.forum.challenge.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;
import hub.forum.challenge.domain.topico.StatusTopico;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    // Busca todos os Tópicos cujo status seja diferente do status fornecido
    List<Topico> findByStatusNot(StatusTopico status);
}
