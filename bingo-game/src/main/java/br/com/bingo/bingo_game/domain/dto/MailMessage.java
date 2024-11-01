package br.com.bingo.bingo_game.domain.dto;

import br.com.bingo.bingo_game.domain.document.BingoCard;
import br.com.bingo.bingo_game.domain.document.Player;
import br.com.bingo.bingo_game.domain.document.Round;

import java.util.Map;

public record MailMessage(String destination, String subject,
                          String template, Map<String, Object> variables) {

    public static MailMessage create(final Round round, final Player player, final BingoCard bingoCard){
        return MailMessage.builder()
                .create(round, player, bingoCard)
                .template("mail/roundResult")
                .build();
    }

    public static MailMessageBuilder builder(){
        return new MailMessageBuilder();
    }
}
