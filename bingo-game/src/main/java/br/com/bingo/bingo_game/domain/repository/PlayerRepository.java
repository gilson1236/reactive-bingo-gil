package br.com.bingo.bingo_game.domain.repository;

import br.com.bingo.bingo_game.domain.document.Player;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends ReactiveMongoRepository<Player, String> {
}
