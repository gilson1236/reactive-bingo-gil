package br.com.bingo.bingo_game.api.controller.impl;

import br.com.bingo.bingo_game.api.controller.RoundController;
import br.com.bingo.bingo_game.api.dto.request.PageableRoundsRequest;
import br.com.bingo.bingo_game.api.dto.response.BingoCardResponse;
import br.com.bingo.bingo_game.api.dto.response.PagedRoundsResponse;
import br.com.bingo.bingo_game.api.dto.response.RoundResponse;
import br.com.bingo.bingo_game.api.dto.response.SortedNumberResponse;
import br.com.bingo.bingo_game.api.mapper.BingoCardMapper;
import br.com.bingo.bingo_game.api.mapper.RoundMapper;
import br.com.bingo.bingo_game.core.validation.MongoId;
import br.com.bingo.bingo_game.domain.service.RoundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/rounds")
@RequiredArgsConstructor
public class RoundControllerImpl implements RoundController {

    private final RoundService roundService;
    private final RoundMapper roundMapper;
    private final BingoCardMapper bingoCardMapper;

    public RoundControllerImpl(RoundService roundService, RoundMapper roundMapper, BingoCardMapper bingoCardMapper) {
        this.roundService = roundService;
        this.roundMapper = roundMapper;
        this.bingoCardMapper = bingoCardMapper;
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RoundResponse> create() {
        return roundService.create()
                .map(roundMapper::toResponse);
    }

    @Override
    @PostMapping(value = "{id}/generate-number", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<SortedNumberResponse> generateNextNumber(
            @PathVariable @Valid @MongoId(message = "{roundController.id}") final String id) {

        return roundService.generateNextNumber(id)
                .map(SortedNumberResponse::new);
    }

    @Override
    @PostMapping(value = "{id}/bingo-card/{playerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BingoCardResponse> generateBingoCard(
            @PathVariable @Valid @MongoId(message = "roundController.id") String id,
            @PathVariable @Valid @MongoId(message = "playerController.id") String playerId) {

        return roundService.generateBingoCard(id, playerId)
                .map(bingoCardMapper::toResponse);
    }

    @Override
    @GetMapping(value = "{id}/current-number", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<SortedNumberResponse> getLastSortedNumber(
            @PathVariable @Valid @MongoId(message = "{roundController.id}") String id) {

        return roundService.getLastSortedNumber(id)
                .map(SortedNumberResponse::new);
    }

    @Override
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RoundResponse> findRoundById(
            @PathVariable @Valid @MongoId(message = "{roundController.id}") String id) {
        return roundService.findRoundById(id);
    }

    @Override
    @GetMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<RoundResponse> findAll() {
        return roundService.findAll();
    }

    @Override
    @GetMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PagedRoundsResponse> findOnDemand(PageableRoundsRequest request) {
        return null;
    }
}
