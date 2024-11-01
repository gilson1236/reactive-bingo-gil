package br.com.bingo.bingo_game.domain.exceptions;

public class RetryException extends ReactiveBingoException{
    public RetryException(String message) {
        super(message);
    }
}
