package br.com.bingo.bingo_game.domain.document;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.OffsetDateTime;
import java.util.Map;

public class BingoCard {

    private Map<Integer, Boolean> numbers;
    @CreatedDate
    @Field("created_at")
    private OffsetDateTime createdAt;
    @LastModifiedDate
    @Field("modified_at")
    private OffsetDateTime modifiedAt;
}
