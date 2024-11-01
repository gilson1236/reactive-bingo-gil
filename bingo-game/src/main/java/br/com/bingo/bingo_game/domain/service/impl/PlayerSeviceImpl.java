package br.com.bingo.bingo_game.domain.service.impl;

import br.com.bingo.bingo_game.domain.document.Player;
import br.com.bingo.bingo_game.domain.exceptions.EmailAlreadyUsedException;
import br.com.bingo.bingo_game.domain.repository.PlayerRepository;

import br.com.bingo.bingo_game.domain.service.PlayerService;
import br.com.bingo.bingo_game.domain.service.query.PlayerQueryService;
import br.com.bingo.bingo_game.utils.UtilLogger;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
@Service
public class PlayerSeviceImpl implements PlayerService {


    private final PlayerRepository playerRepository;

    private final PlayerQueryService playerQueryService;

    public PlayerSeviceImpl(PlayerRepository playerRepository, PlayerQueryService playerQueryService) {
        this.playerRepository = playerRepository;
        this.playerQueryService = playerQueryService;
    }

    public Mono<Player> save(final Player player){
        return savingAfterVerifying(player);
    }

    private Mono<Player> savingAfterVerifying(final Player player) {
        return verifyIfEmailExists(player).doFirst(() ->
                UtilLogger.logInfo("==Try to save a player"))
                .filter(Objects::isNull)
                .switchIfEmpty(Mono.defer(() ->
                        Mono.error(new EmailAlreadyUsedException("Email already used!"))))
                .onErrorResume(ChangeSetPersister.NotFoundException.class,
                        e -> playerRepository.save(player));
    }

    private Mono<Player> verifyIfEmailExists(final Player player) {
        return playerQueryService.findByEmail(player.email());
    }

    public Mono<Player> update(final String id, final Player document){
        return updateAfterVerifying(document);
    }

    private Mono<Player> updateAfterVerifying(final Player document){
        return verifyIfEmailExists(document)
                .then(Mono.defer(() -> playerQueryService.findById(document.id())
                        .map(player -> new Player(player.id(), document.name(), document.email(),
                                document.createdAt(), document.modifiedAt()))
                        .flatMap(playerRepository::save)
                        .doFirst(() -> UtilLogger.logInfo("===Try to update a player with id " + document.id()))));

    }

    public Mono<Void> delete(final String id){
        return playerQueryService.findById(id)
                .flatMap(playerRepository::delete)
                .doFirst(() -> UtilLogger.logInfo("===Try to delete a player with id + " + id));
    }


    public Mono<Player> findById(String id) {
        return playerQueryService.findById(id);
    }
}
