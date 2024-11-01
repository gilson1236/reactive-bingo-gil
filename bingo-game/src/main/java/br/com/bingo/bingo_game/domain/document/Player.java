package br.com.bingo.bingo_game.domain.document;

import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.OffsetDateTime;

@Document(collection = "players")
public record Player (@Id
                      String id,
                      String name,
                      String email,
                      @CreatedDate
                      @Field("created_at")
                      OffsetDateTime createdAt,
                      @LastModifiedDate
                      @Field("modified_at")
                      OffsetDateTime modifiedAt) {

    @Builder(toBuilder = true)
    public Player{}
}
