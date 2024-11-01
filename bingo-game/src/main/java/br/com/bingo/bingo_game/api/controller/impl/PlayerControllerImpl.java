package br.com.bingo.bingo_game.api.controller.impl;

import br.com.bingo.bingo_game.api.controller.PlayerController;
import br.com.bingo.bingo_game.api.dto.request.PlayerRequest;
import br.com.bingo.bingo_game.api.dto.response.PlayerResponse;
import br.com.bingo.bingo_game.api.mapper.PlayerMapper;
import br.com.bingo.bingo_game.core.validation.MongoId;
import br.com.bingo.bingo_game.domain.service.impl.PlayerSeviceImpl;
import br.com.bingo.bingo_game.utils.UtilLogger;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping("/players")
public class PlayerControllerImpl implements PlayerController {

    private final PlayerSeviceImpl playerSevice;
    private final PlayerMapper playerMapper;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PlayerControllerImpl.class);

    public PlayerControllerImpl(PlayerSeviceImpl playerSevice, PlayerMapper playerMapper) {
        this.playerSevice = playerSevice;
        this.playerMapper = playerMapper;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PlayerResponse> save(@Valid @RequestBody final PlayerRequest request){
        System.out.println(playerMapper.getClass());
        return playerSevice.save(playerMapper.toDocument(request))
                .doFirst(() -> log.info("====== Saving a player with follow data {}", request))
                .map(playerMapper::toResponse);
    }

    @Override
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Mono<PlayerResponse> findById(
            @PathVariable @Valid @MongoId(message = "{playerController.id}") String id) {
        return playerSevice.findById(id).doFirst(() ->
                UtilLogger.logInfo("=== Finding a player with follow id {}" + id))
                .map(playerMapper::toResponse);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Mono<PlayerResponse> update(
            @PathVariable @Valid @MongoId(message = "{playerController.id}") final String id,
            @RequestBody @Valid final PlayerRequest request) {
        return playerSevice.update(id, playerMapper.toDocument(request))
                .doFirst(() -> UtilLogger.logInfo("=== Updating a player"))
                .map(playerMapper::toResponse);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(
            @PathVariable @Valid @MongoId(message = "{playerController.id}") String id) {
        return playerSevice.delete(id).doFirst(() ->
                UtilLogger.logInfo("=== Deleting a player with id " + id));
    }
}
