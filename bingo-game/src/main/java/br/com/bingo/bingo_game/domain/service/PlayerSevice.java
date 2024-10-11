package br.com.bingo.bingo_game.domain.service;

import br.com.bingo.bingo_game.domain.document.Player;
import br.com.bingo.bingo_game.domain.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
@NoArgsConstructor
public class PlayerSevice {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PlayerSevice.class);

    @Autowired
    private PlayerRepository playerRepository;

    public Mono<Player> save(final Player player){
        return playerRepository.save(player)
                .doFirst(() -> log.info("====== try to save a follow player {}", player));
    }
}
