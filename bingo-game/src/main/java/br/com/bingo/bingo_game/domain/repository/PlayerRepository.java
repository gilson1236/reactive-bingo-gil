package br.com.bingo.bingo_game.domain.repository;

import br.com.bingo.bingo_game.domain.document.Player;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PlayerRepository extends ReactiveMongoRepository<Player, String> {

    Mono<Player> findByEmail(String email);
}
