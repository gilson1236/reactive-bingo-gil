package br.com.bingo.bingo_game.domain.service;

import br.com.bingo.bingo_game.domain.dto.MailMessage;
import reactor.core.publisher.Mono;

public interface MailService {
    public Mono<Void> send(final MailMessage mailMessage);
}
