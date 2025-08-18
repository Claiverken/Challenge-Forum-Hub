package hub.forum.challenge.controller;

import hub.forum.challenge.domain.user.DadosCadastroUsuario;
import hub.forum.challenge.domain.user.Role;
import hub.forum.challenge.domain.user.User;
import hub.forum.challenge.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder) {
        // Verifica se o email já existe
        if (userRepository.findByEmail(dados.email()) != null) {
            // Lança a exceção em vez de retornar uma resposta manual
            throw new DataIntegrityViolationException("Email já registado.");
        }

        // Criptografa a senha antes de guardar
        var senhaCriptografada = passwordEncoder.encode(dados.password());
        var usuario = new User(dados.name(), dados.email(), senhaCriptografada, Role.ROLE_USER);
        userRepository.save(usuario);

        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

        // Retorna os dados do utilizador criado (sem a senha)
        return ResponseEntity.created(uri).body(new DadosDetalhamentoUsuario(usuario));
    }

    // DTO para a resposta, para não expor a senha
    private record DadosDetalhamentoUsuario(Long id, String name, String email) {
        public DadosDetalhamentoUsuario(User user) {
            this(user.getId(), user.getName(), user.getEmail());
        }
    }
}