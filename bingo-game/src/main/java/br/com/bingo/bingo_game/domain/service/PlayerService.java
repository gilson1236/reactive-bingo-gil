package br.com.bingo.bingo_game.domain.service;

import br.com.bingo.bingo_game.domain.document.Player;
import reactor.core.publisher.Mono;

public interface PlayerService {
    Mono<Player> save(final Player player);

    Mono<Player> update(final String id, final Player player);

    Mono<Void> delete(String id);

    Mono<Player> findById(String id);
}
