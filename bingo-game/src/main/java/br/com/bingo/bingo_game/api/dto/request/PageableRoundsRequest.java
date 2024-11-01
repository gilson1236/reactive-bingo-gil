package br.com.bingo.bingo_game.api.dto.request;

import br.com.bingo.bingo_game.domain.enums.RoundsSortBy;
import br.com.bingo.bingo_game.domain.enums.SortDirection;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import org.apache.commons.lang3.ObjectUtils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public record PageableRoundsRequest(@JsonProperty("sentence")
                                    @Schema(description = "Text to filter by status(case insensitive)")
                                    String sentence,

                                    @JsonProperty("startDate")
                                    @PastOrPresent
                                    @Schema(description = "Data from", defaultValue = "-999999999-01-01T00:00:00+18:00")
                                    OffsetDateTime startDate,

                                    @JsonProperty("endDate")
                                    @PastOrPresent
                                    @Schema(description = "Data to", defaultValue = "Data and hour of the request moment")
                                    OffsetDateTime endDate,

                                    @JsonProperty("page")
                                    @PositiveOrZero
                                    @Schema(description = "Page requested", defaultValue = "1")
                                    Long page,

                                    @JsonProperty("limit")
                                    @Min(1)
                                    @Max(50)
                                    @Schema(description = "Page size", defaultValue = "20")
                                    Integer limit,

                                    @JsonProperty("sortBy")
                                    @Schema(description = "Field for sorting", enumAsRef = true, defaultValue = "CREATE_DATE")
                                    RoundsSortBy sortBy,

                                    @JsonProperty("sortDirection")
                                    @Schema(description = "Sorting direction", enumAsRef = true, defaultValue = "DESC")
                                    SortDirection sortDirection
) {

    @Builder
    public PageableRoundsRequest {
        sentence = ObjectUtils.defaultIfNull(sentence, "");
        startDate = ObjectUtils.defaultIfNull(startDate, OffsetDateTime.of(1900, 1,1,0,0,0,0, ZoneOffset.ofHours(-3)));
        endDate = ObjectUtils.defaultIfNull(endDate, OffsetDateTime.now());
        page = ObjectUtils.defaultIfNull(page, 1L);
        limit = ObjectUtils.defaultIfNull(limit, 20);
        sortBy = ObjectUtils.defaultIfNull(sortBy, RoundsSortBy.CREATE_DATE);
        sortDirection = ObjectUtils.defaultIfNull(sortDirection, SortDirection.DESC);
    }
}
