package br.com.bingo.bingo_game.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record SortedNumberResponse(@JsonProperty("number")
                                   @Schema(description = "Sorted number") Integer number) {

    @Builder
    public SortedNumberResponse {}
}
