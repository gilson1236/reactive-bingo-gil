package br.com.bingo.bingo_game.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public record PlayerResponse (
        @JsonProperty("id")
        String id,
        @JsonProperty("name")
        String name,
        @JsonProperty("email")
        String email
) {
    @Builder(toBuilder = true)
    public PlayerResponse { }
}
