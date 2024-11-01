package br.com.bingo.bingo_game.core.retry;

import br.com.bingo.bingo_game.domain.exceptions.RetryException;
import br.com.bingo.bingo_game.utils.UtilLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.util.retry.Retry;

import java.util.function.Predicate;

@Component
public class RetryHelper {

    private final RetryConfig retryConfig;

    public RetryHelper(RetryConfig retryConfig) {
        this.retryConfig = retryConfig;
    }

    public Retry processRetry(final String retryIdentifier, final Predicate<? super Throwable> errorFilter){
        return Retry.backoff(retryConfig.maxRetries(), retryConfig.minDurationSeconds())
                .filter(errorFilter)
                .doBeforeRetry(retrySignal -> UtilLogger.logWarn("=== Retrying " +
                        "{" + retryIdentifier + "} - {" + retrySignal.totalRetries() +
                        "} times ===="))
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> new RetryException("Max retries achieved"));
    }
}
