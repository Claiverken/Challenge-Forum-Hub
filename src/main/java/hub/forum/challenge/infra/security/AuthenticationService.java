package hub.forum.challenge.infra.security;

import hub.forum.challenge.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Anotação que indica ao Spring que esta é uma classe de serviço
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Este método é chamado pelo Spring Security ao autenticar um usuário.
     * @param username O email do usuário que está tentando fazer login.
     * @return um objeto UserDetails com os dados do usuário.
     * @throws UsernameNotFoundException se o usuário não for encontrado no banco de dados.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // A lógica é simples: buscar o usuário no banco de dados pelo email.
        UserDetails user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }
        return user;
    }
}
