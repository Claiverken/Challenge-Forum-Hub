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
import org.springframework.security.access.AccessDeniedException;
import hub.forum.challenge.domain.user.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;

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
    public ResponseEntity cadastrar(
            @RequestBody @Valid DadosCadastroTopico dados,
            @AuthenticationPrincipal User autor, // 1. O autor vem do utilizador logado
            UriComponentsBuilder uriBuilder) {

        // 2. Lógica para o curso com categoria opcional (usando o padrão "Geral")
        var curso = cursoRepository.findByNome(dados.curso()).orElseGet(() -> {
            // Agora podemos passar dados.categoria() diretamente, mesmo que seja null
            return cursoRepository.save(new Curso(dados.curso(), dados.categoria()));
        });

        // 3. O 'autor' já é o utilizador correto, injetado pelo Spring Security.
        var topico = new Topico(dados.titulo(), dados.mensagem(), autor, curso);
        topicoRepository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosRespostaTopicoCriado(topico));
    }

    @GetMapping
    @Transactional
    public ResponseEntity<Page<DadosListagemTopico>> listar(
            @PageableDefault(size = 10, sort = {"dataCriacao"}, direction = Sort.Direction.DESC) Pageable pageable) {

        var page = topicoRepository.findByStatusNot(StatusTopico.FECHADO, pageable)
                .map(DadosListagemTopico::new);

        return ResponseEntity.ok(page);
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
            throw new AccessDeniedException("Acesso negado: utilizador não tem permissão para alterar este tópico.");
        }

        topico.atualizarInformacoes(dados.titulo(), dados.mensagem());
        return ResponseEntity.ok(new DadosRespostaTopicoCriado(topico));
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id, @AuthenticationPrincipal User usuarioLogado) {
        var topico = topicoRepository.getReferenceById(id);

        // Lógica de permissão: só pode excluir se for o autor OU um admin
        if (!topico.getAutor().equals(usuarioLogado) && !usuarioLogado.getRole().equals(Role.ROLE_ADMIN)) {
            throw new AccessDeniedException("Acesso negado: utilizador não tem permissão para excluir este tópico.");
        }

        topico.fechar(); // Exclusão lógica
        return ResponseEntity.noContent().build();
    }
}