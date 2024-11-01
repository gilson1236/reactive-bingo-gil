package br.com.bingo.bingo_game.domain.document;

import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.List;

public record BingoCard (String id,
                         Player player,
                         List<Integer> numbers,
                         Integer hintCount,
                         OffsetDateTime createdAt,
                         OffsetDateTime modifiedAt) {

    public static BingoCardBuilder builder(){
        return new BingoCardBuilder();
    }

    public BingoCardBuilder toBuilder(){
        return new BingoCardBuilder(id, player,numbers, hintCount, createdAt, modifiedAt);
    }

    public Mono<Boolean> isCompleted(){
        return Mono.just(verifyIsHintMoreAndEqualThan20());
    }

    private Boolean verifyIsHintMoreAndEqualThan20() {
        return hintCount >= 20;
    }
}
