package br.com.bingo.bingo_game.domain.exceptions;

public class RoundNotInitiatedException extends ReactiveBingoException{
    public RoundNotInitiatedException(String message) {
        super(message);
    }
}
