package br.com.bingo.bingo_game.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public record PlayerRequest(
    @JsonProperty("name")
    String name,
    @JsonProperty("email")
    String email
) {
    @Builder(toBuilder = true)
    public PlayerRequest { }
}
