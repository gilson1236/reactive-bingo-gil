package br.com.bingo.bingo_game.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.List;

public record RoundResponse(@JsonProperty("id") String id,
                            @JsonProperty("bingoCards") List<BingoCardResponse> bingoCards,
                            @JsonProperty("sortedNumbers") List<Integer> sortedNumbers,
                            @JsonProperty("winnersIds") List<String> winnersIds,
                            @JsonProperty("state") String state,
                            @JsonProperty("createdAt") OffsetDateTime createdAt) {

    @Builder
    public RoundResponse {}
}
