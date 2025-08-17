package hub.forum.challenge.controller;

import hub.forum.challenge.domain.user.User;
import hub.forum.challenge.infra.security.DadosAuthentication;
import hub.forum.challenge.infra.security.DadosTokenJWT;
import hub.forum.challenge.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAuthentication dados) {
        // 1. Cria um objeto para representar os dados de login (email e senha)
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.password());

        // 2. Chama o AuthenticationManager para autenticar o usuário
        // (Ele usará nosso AuthenticationService por baixo dos panos)
        var authentication = manager.authenticate(authenticationToken);

        // 3. Se a autenticação for bem-sucedida, gera o token JWT
        var tokenJWT = tokenService.gerarToken((User) authentication.getPrincipal());

        // 4. Retorna o token em um DTO
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}