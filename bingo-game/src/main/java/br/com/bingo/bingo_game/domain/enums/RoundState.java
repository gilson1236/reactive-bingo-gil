package br.com.bingo.bingo_game.domain.enums;

public enum RoundState {

    CREATED("created"),
    FINISHED("finished"),
    INITIALIZED("initialized");

    private final String value;

    RoundState(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    @Override
    public String toString(){
        return value;
    }
}
