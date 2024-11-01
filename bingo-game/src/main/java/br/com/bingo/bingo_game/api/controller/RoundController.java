package br.com.bingo.bingo_game.api.controller;

import br.com.bingo.bingo_game.api.dto.request.PageableRoundsRequest;
import br.com.bingo.bingo_game.api.dto.response.*;
import br.com.bingo.bingo_game.core.validation.MongoId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Rounds", description = "Endpoints to manage rounds")
public interface RoundController {

    @Operation(summary = "Create new round")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "returns created round")})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Mono<RoundResponse> create();

    @Operation(summary = "Generates next sorted number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns new sorted number"),
            @ApiResponse(responseCode = "400", description = "Bad request / Invalid request",
                    content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
            @ApiResponse(responseCode = "404", description = "NotFound",
                    content = @Content(schema = @Schema(implementation = ProblemResponse.class)))
    })
    @PostMapping(value = "{id}/generate-number", produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<SortedNumberResponse> generateNextNumber(
            @Parameter(description = "Round identification")
            @PathVariable @Valid @MongoId(message = "{roundController.id}") String id);

    @Operation(summary = "Generates new bingo card to player")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns the new bingo card generated"),
            @ApiResponse(responseCode = "400", description = "Bad request / Invalid request",
                content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                content = @Content(schema = @Schema(implementation = ProblemResponse.class)))
    })
    @PostMapping(value = "{id}/bingo-card/{playerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<BingoCardResponse> generateBingoCard(
            @Parameter(description = "Round identifier") @PathVariable
            @Valid @MongoId(message = "{roundController.id}") String id,
            @Parameter(description = "Player identifier") @PathVariable
            @Valid @MongoId(message = "{playerController.id}") String playerId);

    @Operation(summary = "Gets the last sobrted number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns the last sorted number"),
            @ApiResponse(responseCode = "400", description = "Bad request / Invalid request",
                    content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(schema = @Schema(implementation = ProblemResponse.class)))
    })
    @GetMapping(value = "{id}/current-number", produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<SortedNumberResponse> getLastSortedNumber(
            @Parameter(description = "Round identifier")
            @PathVariable @Valid @MongoId(message = "{roundController.id}") String id
    );

    @Operation(summary = "Gets round by identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns round"),
            @ApiResponse(responseCode = "400", description = "Bad request / Invalid request",
                    content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(schema = @Schema(implementation = ProblemResponse.class)))
    })
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<RoundResponse> findRoundById(
            @Parameter(description = "Round identifier")
            @PathVariable @Valid @MongoId(message = "{roundController.id}") String id
    );

    @Operation(summary = "Gets all rounds")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns all rounds")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    Flux<RoundResponse> findAll();

    @Operation(summary = "Gets paginated rounds")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns rounds in according to request information")
    })
    @GetMapping(value = "search", produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<PagedRoundsResponse> findOnDemand(@ParameterObject @Valid PageableRoundsRequest request);
}
