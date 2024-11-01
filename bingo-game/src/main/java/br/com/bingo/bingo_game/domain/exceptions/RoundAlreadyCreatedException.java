package br.com.bingo.bingo_game.domain.exceptions;

public class RoundAlreadyCreatedException extends ReactiveBingoException{
    public RoundAlreadyCreatedException(String message) {
        super(message);
    }
}
