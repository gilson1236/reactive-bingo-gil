package br.com.bingo.bingo_game.domain.exceptions;

public class PlayerNotFoundException extends RuntimeException{

    public PlayerNotFoundException(String message){
        super(message);
    }
}
