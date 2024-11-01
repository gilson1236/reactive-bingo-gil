package br.com.bingo.bingo_game.api.controller;

import br.com.bingo.bingo_game.api.dto.request.PlayerRequest;
import br.com.bingo.bingo_game.api.dto.response.PlayerResponse;
import br.com.bingo.bingo_game.api.dto.response.ProblemResponse;
import br.com.bingo.bingo_game.core.validation.MongoId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Players", description = "Endpoints to manage players")
public interface PlayerController {
    @Operation(summary = "create a new player")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Returns created player"),
            @ApiResponse(responseCode = "400", description = "Bad request / Invalid request",
                    content = @Content(schema = @Schema(implementation = ProblemResponse.class)))
    })
    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Mono<PlayerResponse> save(@Valid @RequestBody final PlayerRequest request);

    @Operation(summary = "finds player by identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns player appropriately"),
            @ApiResponse(responseCode = "400", description = "Bad request / Invalid request",
                    content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(schema = @Schema(implementation = ProblemResponse.class)))
    })
    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    Mono<PlayerResponse> findById(
            @Parameter(description = "Player identifier") @PathVariable
            @Valid @MongoId(message = "{playerController.id}") String id);

    @Operation(summary = "Update player data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns updated player"),
            @ApiResponse(responseCode = "400", description = "Bad request / Invalid request",
                    content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(schema = @Schema(implementation = ProblemResponse.class)))
    })
    @PutMapping(value = "{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    Mono<PlayerResponse> update(
            @Parameter(description = "Player identifier") @PathVariable
            @Valid @MongoId(message = "{playerController.id}") String id,
            @RequestBody @Valid PlayerRequest request);

    @Operation(summary = "Remove player by identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "No content "),
            @ApiResponse(responseCode = "400", description = "Bad request / Invalid request",
                    content = @Content(schema = @Schema(implementation = ProblemResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found ",
                    content = @Content(schema = @Schema(implementation = ProblemResponse.class)))
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Mono<Void> delete(
            @Parameter(description = "Player identifier") @PathVariable
            @Valid @MongoId(message = "{playerController.id}") String id);
    }
