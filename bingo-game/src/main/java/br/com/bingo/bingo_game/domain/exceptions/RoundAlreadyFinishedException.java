package br.com.bingo.bingo_game.domain.exceptions;

public class RoundAlreadyFinishedException extends ReactiveBingoException{
    public RoundAlreadyFinishedException(String message) {
        super(message);
    }
}
