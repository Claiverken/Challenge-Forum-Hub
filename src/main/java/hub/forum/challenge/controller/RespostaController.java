package hub.forum.challenge.controller;

import hub.forum.challenge.domain.answer.Answer;
import hub.forum.challenge.domain.answer.AnswerRepository;
import hub.forum.challenge.domain.answer.DadosCadastroResposta;
import hub.forum.challenge.domain.answer.DadosResposta;
import hub.forum.challenge.domain.topico.TopicoRepository;
import hub.forum.challenge.domain.user.User;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos/{topicoId}/respostas")
public class RespostaController {

    private final AnswerRepository answerRepository;
    private final TopicoRepository topicoRepository;

    public RespostaController(AnswerRepository answerRepository, TopicoRepository topicoRepository) {
        this.answerRepository = answerRepository;
        this.topicoRepository = topicoRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosResposta> criarResposta(
            @PathVariable Long topicoId,
            @RequestBody @Valid DadosCadastroResposta dados,
            @AuthenticationPrincipal User autor,
            UriComponentsBuilder uriBuilder) {

        var topico = topicoRepository.getReferenceById(topicoId);

        var resposta = new Answer(dados, autor, topico);
        answerRepository.save(resposta);

        topico.marcarComoRespondido();

        var uri = uriBuilder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosResposta(resposta));
    }
}