package hub.forum.challenge.controller;

import hub.forum.challenge.domain.curso.Curso;
import hub.forum.challenge.domain.curso.CursoRepository;
import hub.forum.challenge.domain.topico.*;
import hub.forum.challenge.domain.user.Role;
import hub.forum.challenge.domain.user.User;
import hub.forum.challenge.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import hub.forum.challenge.domain.user.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    // Renomeado para 'topicoRepository' para clareza
    @Autowired
    private TopicoRepository topicoRepository;

    // Injete o PasswordEncoder
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Injetar o novo CursoRepository
    @Autowired
    private CursoRepository cursoRepository;

    // 1. ADICIONADO: Injeção do UserRepository que estava faltando
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuilder) {
        var autor = userRepository.findByName(dados.autor()).orElseGet(() -> {
            // AJUSTE: Criptografando a senha antes de salvar
            var senhaCriptografada = passwordEncoder.encode("123456"); // Usamos "123456" como senha padrão
            var novoUsuario = new User(dados.autor(), dados.autor().toLowerCase() + "@forum.hub", senhaCriptografada, Role.ROLE_USER);
            return userRepository.save(novoUsuario);
        });

        // 2. Adicionar lógica para encontrar ou criar o curso
        var curso = cursoRepository.findByNome(dados.curso()).orElseGet(() -> {
            var novoCurso = new Curso(dados.curso(), dados.categoria());
            return cursoRepository.save(novoCurso);
        });

        // 3. Usar o objeto 'curso' no construtor do Tópico
        var topico = new Topico(dados.titulo(), dados.mensagem(), autor, curso);
        topicoRepository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
    }

    @GetMapping
    @Transactional
    public ResponseEntity<List<DadosListagemTopico>> listar() {
        // Agora usamos o novo método para buscar todos os tópicos que não estão FECHADO
        var topicos = topicoRepository.findByStatusNot(StatusTopico.FECHADO)
                .stream()
                .map(DadosListagemTopico::new)
                .toList();
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity detalhar(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody DadosAtualizacaoTopico dados, @AuthenticationPrincipal User usuarioLogado) {
        var topico = topicoRepository.getReferenceById(id);

        // Lógica de permissão: só pode atualizar se for o autor OU um admin
        if (!topico.getAutor().equals(usuarioLogado) && !usuarioLogado.getRole().equals(Role.ROLE_ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: você só pode editar os seus próprios tópicos.");
        }

        topico.atualizarInformacoes(dados.titulo(), dados.mensagem());
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id, @AuthenticationPrincipal User usuarioLogado) {
        var topico = topicoRepository.getReferenceById(id);

        // Lógica de permissão: só pode excluir se for o autor OU um admin
        if (!topico.getAutor().equals(usuarioLogado) && !usuarioLogado.getRole().equals(Role.ROLE_ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado: você só pode excluir os seus próprios tópicos.");
        }

        topico.fechar(); // Exclusão lógica
        return ResponseEntity.noContent().build();
    }
}