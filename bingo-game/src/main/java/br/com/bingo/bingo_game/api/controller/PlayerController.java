package br.com.bingo.bingo_game.api.controller;

import br.com.bingo.bingo_game.api.controller.request.PlayerRequest;
import br.com.bingo.bingo_game.api.controller.response.PlayerResponse;
import br.com.bingo.bingo_game.api.mapper.PlayerMapper;
import br.com.bingo.bingo_game.domain.service.PlayerSevice;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping("/players")
@AllArgsConstructor
public class PlayerController {

    private PlayerSevice playerSevice;
    private PlayerMapper playerMapper;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PlayerController.class);

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Mono<PlayerResponse> save(@Valid @RequestBody final PlayerRequest request){
        System.out.println(playerMapper.getClass());
        return playerSevice.save(playerMapper.toDocument(request))
                .doFirst(() -> log.info("====== Saving a player with follow data {}", request))
                .map(playerMapper::toResponse);
    }
}
