package br.com.bingo.bingo_game.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.List;

public record ProblemResponse(@JsonProperty("status")
                              @Schema(description = "Returned HTTP status") Integer status,
                              @JsonProperty("description")
                              @Schema(description = "Error description") String description,
                              @JsonProperty("timestamp")
                              @Schema(description = "Moment at the error happens") OffsetDateTime timestamp,
                              @JsonProperty("fields")
                              @Schema(description = "If the request has invalid parameters will be informed here")
                              List<FieldErrorResponse> fields) {
}
