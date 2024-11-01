package br.com.bingo.bingo_game.domain.service.impl;

import br.com.bingo.bingo_game.core.retry.RetryHelper;
import br.com.bingo.bingo_game.domain.dto.MailMessage;
import br.com.bingo.bingo_game.domain.exceptions.ReactiveBingoException;
import br.com.bingo.bingo_game.domain.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.thymeleaf.context.Context;

@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final String sender;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final RetryHelper retryHelper;
    public MailServiceImpl(String sender, JavaMailSender javaMailSender, TemplateEngine templateEngine, RetryHelper retryHelper) {
        this.sender = sender;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.retryHelper = retryHelper;
    }

    @Override
    public Mono<Void> send(final MailMessage mailMessage) {
        return Mono.just(javaMailSender.createMimeMessage())
                .flatMap(mimeMessage -> buildMimeMessage(mimeMessage, mailMessage))
                .flatMap(this::sendWithRetry);
    }

    private Mono<MimeMessage> buildMimeMessage(MimeMessage mimeMessage, MailMessage mailMessage) {
        return Mono.fromCallable(() -> {
            try {
                var helper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
                helper.setTo(mailMessage.destination());
                helper.setFrom(sender);
                helper.setSubject(mailMessage.subject());
                String body = buildMailFromTemplate(mailMessage.template(), mailMessage.variables());
                helper.setText(body);
                return helper.getMimeMessage();
            } catch (MessagingException e){
                throw new ReactiveBingoException(e.getMessage(), e);
            }
        });
    }

    private String buildMailFromTemplate(final String template, final Map<String, Object> variables) {
        var context = new Context(Locale.of("pt", "BR"));
        context.setVariables(variables);
        return templateEngine.process(template, context);
    }

    private Mono<Void> sendWithRetry(final MimeMessage mimeMessage){
        return Mono.fromCallable(() -> {
            javaMailSender.send(mimeMessage);
            return mimeMessage;
        }).retryWhen(retryHelper.processRetry(UUID.randomUUID().toString(),
                throwable -> throwable instanceof MailException))
                .then();
    }
}
