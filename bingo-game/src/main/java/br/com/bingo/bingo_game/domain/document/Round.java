package br.com.bingo.bingo_game.domain.document;

import br.com.bingo.bingo_game.domain.enums.RoundState;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.List;

@Document("rounds")
public record Round(@Id String id,
                    @Field("bingo_cards") List<BingoCard> bingoCards,
                    @Field("sorted_numbers") List<Integer> sortedNumbers,
                    @Field("winner_ids") List<String> winnerIds,
                    RoundState state,
                    @CreatedDate @Field("created_at") OffsetDateTime createdAt,
                    @LastModifiedDate @Field("modified_at") OffsetDateTime modifiedAt) {

    public static RoundBuilder builder(){
        return new RoundBuilder();
    }

    public RoundBuilder toBuilder(){
        return new RoundBuilder(id, bingoCards, sortedNumbers, winnerIds, state, createdAt,modifiedAt);
    }

    public Mono<Integer> getLastSortedNumbers(){
        return Flux.fromIterable(this.sortedNumbers).last();
    }
}
