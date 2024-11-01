package br.com.bingo.bingo_game.domain.service;

import br.com.bingo.bingo_game.api.dto.response.RoundResponse;
import br.com.bingo.bingo_game.domain.document.BingoCard;
import br.com.bingo.bingo_game.domain.document.Round;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoundService {

    Mono<Round> create();

    Mono<Integer> generateNextNumber(final String id);

    Mono<BingoCard> generateBingoCard(final String id, final String playerId);

    Mono<RoundResponse> findRoundById(final String id);

    Mono<Integer> getLastSortedNumber(final String id);

    Flux<RoundResponse> findAll();
}
