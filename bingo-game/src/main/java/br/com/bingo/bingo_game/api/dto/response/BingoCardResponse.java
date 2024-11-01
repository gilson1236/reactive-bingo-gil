package br.com.bingo.bingo_game.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

public record BingoCardResponse(
        @JsonProperty("playerId") String playerId,
        @JsonProperty("numbers") List<Integer> numbers,
        @JsonProperty("hintCount") Integer hintCount) {

    @Builder
    public BingoCardResponse {}
}
