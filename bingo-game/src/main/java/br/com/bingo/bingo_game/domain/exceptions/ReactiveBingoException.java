package br.com.bingo.bingo_game.domain.exceptions;

public class ReactiveBingoException extends RuntimeException{

    public ReactiveBingoException(String message){
        super(message);
    }

    public ReactiveBingoException(String message, Throwable cause){
        super(message, cause);
    }
}
