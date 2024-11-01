package br.com.bingo.bingo_game.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

public record PagedRoundsResponse(@JsonProperty("currentPage")
                                  @Schema(description = "Returned page")
                                  Long currentPage,

                                  @JsonProperty("totalPages")
                                  @Schema(description = "Total pages")
                                  Long totalPages,

                                  @JsonProperty("totalItens")
                                  @Schema(description = "Total paginated register")
                                  Long totalItens,

                                  @JsonProperty("content")
                                  @Schema(description = "Rounds page data")
                                  List<RoundResponse> content) {

    @Builder
    public PagedRoundsResponse{}
}
