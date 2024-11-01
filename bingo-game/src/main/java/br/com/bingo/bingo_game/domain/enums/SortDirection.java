package br.com.bingo.bingo_game.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortDirection {

    ASC("ASC"),
    DESC("DESC");

    SortDirection(String direction) {
    }
}
