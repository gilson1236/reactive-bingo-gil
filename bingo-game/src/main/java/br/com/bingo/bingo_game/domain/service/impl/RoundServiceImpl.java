package br.com.bingo.bingo_game.domain.service.impl;

import br.com.bingo.bingo_game.api.dto.response.RoundResponse;
import br.com.bingo.bingo_game.api.mapper.RoundMapper;
import br.com.bingo.bingo_game.domain.document.BingoCard;
import br.com.bingo.bingo_game.domain.document.Round;
import br.com.bingo.bingo_game.domain.dto.MailMessage;
import br.com.bingo.bingo_game.domain.exceptions.RoundNotInitiatedException;
import br.com.bingo.bingo_game.domain.repository.RoundRepository;
import br.com.bingo.bingo_game.domain.service.MailService;
import br.com.bingo.bingo_game.domain.service.RoundService;
import br.com.bingo.bingo_game.domain.service.query.PlayerQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class RoundServiceImpl implements RoundService {

    private final RoundRepository roundRepository;
    private final PlayerQueryService playerQueryService;
    private final MailService mailService;
    private final RoundMapper roundMapper;
    public RoundServiceImpl(RoundRepository roundRepository, PlayerQueryService playerQueryService, MailService mailService, RoundMapper roundMapper) {
        this.roundRepository = roundRepository;
        this.playerQueryService = playerQueryService;
        this.mailService = mailService;
        this.roundMapper = roundMapper;
    }

    @Override
    public Mono<Round> create() {
        return roundRepository.save(Round.builder().build());
    }

    @Override
    public Mono<Integer> generateNextNumber(String id) {

        return getRoundById(id)
                .flatMap(round -> round.toBuilder().sortNumber())
                .map(Round.builder()::build)
                .flatMap(roundRepository::save)
                .flatMap(round -> round.winnerIds().isEmpty()
                    ? round.getLastSortedNumbers():
                      round.getLastSortedNumbers().onTerminateDetach()
                              .doOnSuccess(lastSortedNumber -> processIfHasWinners(round)));

    }

    private void processIfHasWinners(final Round round) {
        Flux.fromIterable(round.bingoCards())
                .flatMap(bingoCard -> round.winnerIds().contains(bingoCard.player().id())
                        ? notifyWinner(round, bingoCard)
                        : notifyPlayer(round, bingoCard))
                .subscribeOn(Schedulers.parallel())
                .subscribe();
    }

    private Mono<Void> notifyPlayer(Round round, BingoCard bingoCard) {
        return playerQueryService.findById(bingoCard.player().id())
                .map(player -> MailMessage.create(round, player, bingoCard))
                .flatMap(mailService::send);
    }

    private Mono<Void> notifyWinner(Round round, BingoCard bingoCard) {
        return playerQueryService.findById(bingoCard.player().id())
                .map(player -> MailMessage.create(round, player, bingoCard))
                .flatMap(mailService::send);
    }

    private Mono<Round> getRoundById(String id){
        return roundRepository.findById(id);
    }

    @Override
    public Mono<BingoCard> generateBingoCard(String id, String playerId) {
        return roundRepository.existsByIdAndBingoCardsPlayerId(id, playerId)
                .then(Mono.defer(() -> playerQueryService.findById(playerId)))
                .zipWhen(player -> roundRepository.findById(id))
                .flatMap(tuple -> tuple.getT2().toBuilder().addBingoCard(tuple.getT1()))
                .map(Round.builder()::build)
                .flatMap(roundRepository::save)
                .map(round -> round.bingoCards().stream()
                        .filter(bingoCard -> bingoCard.player().id().equals(playerId))
                        .toList()
                        .getFirst());
    }

    @Override
    public Mono<RoundResponse> findRoundById(String id) {
        return findById(id)
                .map(roundMapper::toResponse);
    }

    private Mono<Round> findById(String id){
        return roundRepository.findById(id);
    }

    @Override
    public Mono<Integer> getLastSortedNumber(String id) {
        return this.findById(id)
                .flatMap(Round::getLastSortedNumbers)
                .onErrorResume(NoSuchElementException.class, e -> Mono.error(
                        new RoundNotInitiatedException("Round Not initiated" + id)
                ));
    }

    @Override
    public Flux<RoundResponse> findAll() {
        return roundRepository.findAll()
                .map(roundMapper::toResponse);
    }
}
