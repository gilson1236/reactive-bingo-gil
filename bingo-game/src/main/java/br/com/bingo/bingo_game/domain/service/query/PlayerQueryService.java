package br.com.bingo.bingo_game.domain.service.query;

import br.com.bingo.bingo_game.domain.document.Player;
import reactor.core.publisher.Mono;

public interface PlayerQueryService {

    public Mono<Player> findById(final String id);

    public Mono<Player> findByEmail(final String email);

}
