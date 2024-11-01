package br.com.bingo.bingo_game.domain.exceptions;

public class RecursionException extends ReactiveBingoException{
    public RecursionException(String message) {
        super(message);
    }

    public RecursionException(String message, Throwable cause) {
        super(message, cause);
    }
}
