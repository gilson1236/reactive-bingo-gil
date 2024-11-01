package br.com.bingo.bingo_game.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record FieldErrorResponse(@JsonProperty("name")
                                 @Schema(description = "Fieldname with error") String name,
                                 @JsonProperty("message")
                                 @Schema(description = "Error description") String message) {

    @Builder
    public FieldErrorResponse {}
}
