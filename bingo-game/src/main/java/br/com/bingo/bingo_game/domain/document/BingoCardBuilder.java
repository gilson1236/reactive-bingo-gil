package br.com.bingo.bingo_game.domain.document;

import br.com.bingo.bingo_game.domain.exceptions.RecursionException;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Random;

public class BingoCardBuilder {

    private String id;
    private Player player;
    private List<Integer> numbers;
    private Integer hintCount;
    private OffsetDateTime createdAt;
    private OffsetDateTime modifiedAt;

    private static final int RECURSION_LIMIT = 50;

    public BingoCardBuilder(final String id,
                            final Player player,
                            final List<Integer> numbers,
                            final Integer hintCount,
                            final OffsetDateTime createdAt,
                            final OffsetDateTime modifiedAt) {
        this.id = id;
        this.player = player;
        this.numbers = numbers;
        this.hintCount = hintCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public BingoCardBuilder() {

    }

    public BingoCardBuilder id(final String id){
        this.id = id;
        return this;
    }

    public BingoCardBuilder player(final Player player){
        this.player = player;
        return this;
    }

    public BingoCardBuilder numbers(final List<Integer> numbers){
        this.numbers = numbers;
        return this;
    }

    public BingoCardBuilder hintCount(final Integer hintCount){
        this.hintCount = hintCount;
        return this;
    }

    public BingoCardBuilder createdAt(final OffsetDateTime createdAt){
        this.createdAt = createdAt;
        return this;
    }

    public BingoCardBuilder modifiedAt(final OffsetDateTime modifiedAt){
        this.modifiedAt = modifiedAt;
        return this;
    }

    public Mono<BingoCardBuilder> incrementHintCountIfContains(final Integer sortedNumber){
        return Mono.defer(() -> {
            if(verifyIfNumbersContainsNumber(sortedNumber)){
                ++this.hintCount;
                this.modifiedAt = OffsetDateTime.now();
            }
            return Mono.just(this);
        });
    }

    private boolean verifyIfNumbersContainsNumber(Integer sortedNumber) {
        return numbers.contains(sortedNumber);
    }

    public Mono<BingoCardBuilder> generate(final Player player,
                                           final List<BingoCard> existingBingoCards){

        return mappingNumbersGenerated(generateNumbers(existingBingoCards, new Random(), 0));
    }

    public BingoCard build(){
        return new BingoCard(id, player, numbers, hintCount, createdAt, modifiedAt);
    }

    private Mono<BingoCardBuilder> mappingNumbersGenerated(Mono<List<Integer>> listMono) {
        return listMono.map(numbers -> BingoCard.builder().id(ObjectId.get().toString())
                .player(player)
                .numbers(numbers)
                .hintCount(0)
                .createdAt(OffsetDateTime.now())
                .modifiedAt(OffsetDateTime.now()));
    }

    private Mono<List<Integer>> generateNumbers(final List<BingoCard> existingBingoCards,
                                                final Random random,
                                                int recursionCount) {
        if(testsIfRecursionCountIsBiggerThanLimit(recursionCount)){
            return Mono.error(new RecursionException("Recursion count is at least equal to recursion limit"));
        }

        return generateFlux(existingBingoCards, random, recursionCount);
    }

    private Mono<List<Integer>> generateFlux(List<BingoCard> existingBingoCards,
                                             Random random,
                                             int recursionCount) {
        return Flux.generate((SynchronousSink<Integer> sinc) -> sinc.next(random.nextInt(100)))
                .distinct()
                .take(20)
                .collectSortedList()
                .flatMap(sortedNumbers -> numbersAreNotValid(existingBingoCards, sortedNumbers)
                .flatMap(areNotValid-> areNotValid ?
                        generateNumbers(existingBingoCards, random, recursionCount + 1) :
                        Mono.just(sortedNumbers)));
    }

    private Mono<Boolean> numbersAreNotValid(List<BingoCard> existingBingoCards, List<Integer> sortedNumbers) {
        return Flux.fromIterable(existingBingoCards)
                .flatMap(bingoCard -> countDuplicates(sortedNumbers, bingoCard.numbers()))
                .any(duplicates -> duplicates > 5);
    }

    private Mono<Long> countDuplicates(List<Integer> existingBingoCards, List<Integer> sortedNumbers) {
        return Flux.fromIterable(sortedNumbers)
                .filter(existingBingoCards::contains)
                .count();
    }

    private boolean testsIfRecursionCountIsBiggerThanLimit(int recursionCount) {
        return recursionCount >= RECURSION_LIMIT;
    }
}
