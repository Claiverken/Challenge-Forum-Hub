package hub.forum.challenge.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    // Busca todos os Tópicos cujo status seja diferente do status fornecido
    Page<Topico> findByStatusNot(StatusTopico status, Pageable pageable);
}
