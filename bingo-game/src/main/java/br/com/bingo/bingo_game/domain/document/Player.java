package br.com.bingo.bingo_game.domain.document;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.OffsetDateTime;

@Document(collection = "players")
public class Player {

    @Id
    private String id;
    private String name;
    private String email;
    private BingoCard bingoCard;
    @CreatedDate
    @Field("created_at")
    private OffsetDateTime createdAt;
    @LastModifiedDate
    @Field("modified_at")
    private OffsetDateTime modifiedAt;
}
