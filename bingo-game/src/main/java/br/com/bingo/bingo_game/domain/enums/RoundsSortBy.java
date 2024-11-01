package br.com.bingo.bingo_game.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoundsSortBy {

    STATE("state"),
    CREATE_DATE("created_at");

    RoundsSortBy(String field) {
    }
}
