package br.com.bingo.bingo_game.domain.repository;

import br.com.bingo.bingo_game.domain.document.Round;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface RoundRepository extends ReactiveMongoRepository<Round, String> {
    Flux<Round> findIsActive(boolean isActive);

    Mono<Boolean> existsByIdAndBingoCardsPlayerId(String id, String playerId);

}
