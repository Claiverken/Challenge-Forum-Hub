package hub.forum.challenge.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // O Spring Data JPA cria a consulta automaticamente a partir do nome do método
    Optional<User> findByName(String name);

    // Método para buscar um usuário pelo email na hora da autenticação
    UserDetails findByEmail(String email);
}
