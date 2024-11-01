package br.com.bingo.bingo_game.domain.document;

import br.com.bingo.bingo_game.domain.enums.RoundState;
import br.com.bingo.bingo_game.domain.exceptions.RoundAlreadyCreatedException;
import br.com.bingo.bingo_game.domain.exceptions.RoundAlreadyFinishedException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RoundBuilder {

    private String id;
    private List<BingoCard> bingoCards;
    private List<Integer> sortedNumbers;
    private List<String> winnersIds;
    private RoundState state;
    private OffsetDateTime createdAt;
    private OffsetDateTime modifiedAt;

    public RoundBuilder(){}

    public RoundBuilder(String id,
                        List<BingoCard> bingoCards,
                        List<Integer> sortedNumbers,
                        List<String> winnersIds,
                        RoundState state,
                        OffsetDateTime createdAt,
                        OffsetDateTime modifiedAt){
        this.id = id;
        this.bingoCards = bingoCards;
        this.sortedNumbers = sortedNumbers;
        this.winnersIds = winnersIds;
        this.state = state;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;

    }

    public RoundBuilder id(final String id){
        this.id = id;
        return this;
    }

    public RoundBuilder bingoCards(final List<BingoCard> bingoCards) {
        this.bingoCards = bingoCards;
        return this;
    }

    public RoundBuilder bingoCard(final BingoCard bingoCards) {
        this.bingoCards.add(bingoCards);
        return this;
    }

    public RoundBuilder sortedNumbers(final List<Integer> sortedNumbers) {
        this.sortedNumbers = sortedNumbers;
        return this;
    }

    public RoundBuilder sortedNumber(final Integer sortedNumber) {
        this.sortedNumbers.add(sortedNumber);
        return this;
    }

    public RoundBuilder winnersIds(final List<String> winnersIds) {
        this.winnersIds = winnersIds;
        return this;
    }

    public RoundBuilder state(final RoundState state) {
        this.state = state;
        return this;
    }

    public RoundBuilder createdAt(final OffsetDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public RoundBuilder updatedAt(final OffsetDateTime updatedAt) {
        this.modifiedAt = updatedAt;
        return this;
    }

    public Round build(){
        return new Round(id, bingoCards, sortedNumbers, winnersIds, state, createdAt, modifiedAt);
    }

    public Mono<RoundBuilder> create(){
        return mappingToCreate(Mono.just(this));
    }

    private Mono<RoundBuilder> mappingToCreate(Mono<RoundBuilder> just) {
        return just.map(roundBuilder -> roundBuilder
                .state(RoundState.CREATED)
                .sortedNumbers(new ArrayList<>())
                .bingoCards(new ArrayList<>())
                .winnersIds(new ArrayList<>()));
    }

    public Mono<RoundBuilder> addBingoCard(final Player player){
        return Mono.defer(() -> {
            if(verifyStateIsCreated(state)){
                return Mono.error(new RoundAlreadyCreatedException("Round Alread Created"));
            }
            return addingBingoCard(player);
        });
    }

    private Mono<RoundBuilder> addingBingoCard(Player player) {
        return Mono.defer(() ->
           BingoCard.builder().generate(player, this.bingoCards)
                   .map(BingoCardBuilder::build)
                   .map(bingoCard -> this.bingoCard(bingoCard).updatedAt(OffsetDateTime.now())));


        /*BingoCard.builder().generate(player, this.bingoCards)
                .map()
                .map(bingoCard -> this.bingoCard(bingoCard).updatedAt(OffsetDateTime.now()));*/
    }

    private Boolean verifyStateIsCreated(RoundState state) {
        return state != RoundState.CREATED;
    }

    public Mono<RoundBuilder> sortNumber(){
        return Mono.defer(() -> {
            if(verifyStateIsFinished(state))
                return Mono.error(new RoundAlreadyFinishedException("Round Already Finished!"));
            if(verifyStateIsCreated(state)){
                this.state = RoundState.INITIALIZED;
            }

            var random = new Random();
            return sortingUniqueNumber(random);
        });
    }

    private Mono<RoundBuilder> sortingUniqueNumber(Random random) {
        return sortUniqueNumber(random)
                .flatMap(this::processSortedBuilder)
                .flatMap(RoundBuilder::checkAndPopulateWinners);
    }

    private Mono<Integer> sortUniqueNumber(Random random) {
        return Mono.create(sink -> {
            int sortedNumber;
            do{
                sortedNumber = random.nextInt(100);
            } while (sortedNumbers.contains(sortedNumber));
            sink.success(sortedNumber);
        });
    }

    private Mono<RoundBuilder> processSortedBuilder(final Integer sortedNumber){
        return Mono.just(this.sortedNumber(sortedNumber).updatedAt(OffsetDateTime.now()))
                .flatMap(roundBuilder -> updateBingoCards(sortedNumber));
    }

    private Mono<RoundBuilder> updateBingoCards(Integer sortedNumber) {
        return Flux.fromIterable(this.bingoCards)
                .flatMap(bingoCard -> bingoCard.toBuilder().incrementHintCountIfContains(sortedNumber))
                .map(BingoCardBuilder::build)
                .collectList()
                .map(this::bingoCards);
    }

    private Mono<RoundBuilder> checkAndPopulateWinners(){
        return populateWinners(checkWinners(this.bingoCards));
    }

    private Mono<RoundBuilder> populateWinners(Flux<BingoCard> bingoCardFlux) {
        return bingoCardFlux.map(bingoCard -> bingoCard.player().id())
                .collectList()
                .map(this::winnersIds)
                .flatMap(roundBuilder -> roundBuilder.winnersIds.isEmpty()
                        ? Mono.just(roundBuilder) : roundBuilder.finish());
    }

    private Flux<BingoCard> checkWinners(List<BingoCard> bingoCards){
        return Flux.fromIterable(bingoCards)
                .flatMap(bingoCard -> bingoCard.isCompleted()
                        .filter(Boolean.TRUE::equals)
                        .map(completed -> bingoCard));
    }

    private Boolean verifyStateIsFinished(RoundState state) {
        return state == RoundState.FINISHED;
    }

    private Mono<RoundBuilder> finish(){
        return Mono.just(this.state(RoundState.FINISHED).updatedAt(OffsetDateTime.now()));
    }

    public Round build(RoundBuilder roundBuilder) {
        return new Round(roundBuilder.id, roundBuilder.bingoCards, roundBuilder.sortedNumbers,
                roundBuilder.winnersIds, roundBuilder.state, roundBuilder.createdAt,
                roundBuilder.modifiedAt);
    }
}
