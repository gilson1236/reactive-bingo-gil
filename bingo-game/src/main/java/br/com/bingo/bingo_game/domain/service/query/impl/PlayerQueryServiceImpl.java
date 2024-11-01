package br.com.bingo.bingo_game.domain.service.query.impl;

import br.com.bingo.bingo_game.domain.document.Player;
import br.com.bingo.bingo_game.domain.exceptions.PlayerNotFoundException;
import br.com.bingo.bingo_game.domain.repository.PlayerRepository;
import br.com.bingo.bingo_game.domain.service.query.PlayerQueryService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import br.com.bingo.bingo_game.utils.UtilLogger;

import java.util.Objects;

@Service
public class PlayerQueryServiceImpl implements PlayerQueryService {

    private final PlayerRepository playerRepository;

    public PlayerQueryServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Mono<Player> findById(final String id){
        return treatingReturn(playerRepository.findById(id), id);
    }

    @Override
    public Mono<Player> findByEmail(String email) {
        return findEmail(email).doFirst(() ->
                UtilLogger.logInfo("== try to find player with an email " + email))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new PlayerNotFoundException("Player not found"))));
    }

    private Mono<Player> findEmail(String email) {
        return playerRepository.findByEmail(email);
    }

    private Mono<Player> treatingReturn(Mono<Player> playerById, String id) {
        return playerById.doFirst(() -> UtilLogger.logInfo("== try to find player with id "+ id))
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new PlayerNotFoundException("Player not Found"))));
    }
}
