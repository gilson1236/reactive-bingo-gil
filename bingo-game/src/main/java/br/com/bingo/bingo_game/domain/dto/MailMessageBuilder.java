package br.com.bingo.bingo_game.domain.dto;

import br.com.bingo.bingo_game.domain.document.BingoCard;
import br.com.bingo.bingo_game.domain.document.Player;
import br.com.bingo.bingo_game.domain.document.Round;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MailMessageBuilder {

    private String destination;
    private String subject;
    private String template;
    private Map<String, Object> variables = new HashMap<>();

    public MailMessageBuilder create(final Round round, final Player player, final BingoCard bingoCard){
        return this.destination(player.email())
                .subject("Reactive bingo: Winner")
                .playerName(player.name())
                .roundId(round.id())
                .cardNumbers(bingoCard.numbers())
                .sortedNumbers(round.sortedNumbers())
                .hintCount(bingoCard.hintCount());
    }

    public MailMessageBuilder destination(final String destination){
        this.destination = destination;
        return this;
    }

    public MailMessageBuilder subject(final String subject){
        this.subject = subject;
        return this;
    }

    public MailMessageBuilder template(final String template) {
        this.template = template;
        return this;
    }

    public MailMessageBuilder playerName(final String playerName) {
        return variable("playerName", playerName);
    }

    public MailMessageBuilder roundId(final String roundId) {
        return variable("roundId", roundId);
    }

    public MailMessageBuilder cardNumbers(final List<Integer> cardNumbers) {

        cardNumbers.sort(Integer::compareTo);
        return variable("cardNumbers", StringUtils.join(cardNumbers, ","));
    }

    public MailMessageBuilder sortedNumbers(final List<Integer> sortedNumbers) {
        sortedNumbers.sort(Integer::compareTo);
        return variable("sortedNumbers", StringUtils.join(sortedNumbers, ","));
    }

    public MailMessageBuilder hintCount(final Integer hintCount) {
        return variable("hintCount", hintCount);
    }

    public MailMessage build() {
        return new MailMessage(destination, subject, template, variables);
    }

    private MailMessageBuilder variable(final String key, final Object value) {
        this.variables.put(key, value);
        return this;
    }
}
