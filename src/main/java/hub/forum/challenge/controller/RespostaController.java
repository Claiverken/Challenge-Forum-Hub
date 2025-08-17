package hub.forum.challenge.controller;

import hub.forum.challenge.domain.answer.Answer;
import hub.forum.challenge.domain.answer.AnswerRepository;
import hub.forum.challenge.domain.answer.DadosResposta;
import hub.forum.challenge.domain.topico.StatusTopico;
import hub.forum.challenge.domain.topico.TopicoRepository;
import hub.forum.challenge.domain.user.User;
import jakarta.transaction.Transactional;
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
            @RequestBody String message, // Recebe a mensagem como texto simples
            @AuthenticationPrincipal User autor,
            UriComponentsBuilder uriBuilder) {

        var topico = topicoRepository.findById(topicoId)
                .orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado!"));

        var resposta = new Answer(null, message, autor, topico);
        answerRepository.save(resposta);

        // Opcional: Mudar o status do tópico para RESPONDIDO
        if (topico.getStatus() == StatusTopico.NAO_RESPONDIDO) {
            topico.marcarComoRespondido();
        }

        var uri = uriBuilder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosResposta(resposta));
    }
}