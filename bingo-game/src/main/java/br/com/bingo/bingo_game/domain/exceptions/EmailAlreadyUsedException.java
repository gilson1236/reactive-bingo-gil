package br.com.bingo.bingo_game.domain.exceptions;

public class EmailAlreadyUsedException extends ReactiveBingoException{
    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
