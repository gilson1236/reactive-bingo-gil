package br.com.bingo.bingo_game.utils;

import br.com.bingo.bingo_game.api.controller.impl.PlayerControllerImpl;

public class UtilLogger {

    private static final org.slf4j.Logger logger= org.slf4j.LoggerFactory.getLogger(UtilLogger.class);

    private UtilLogger(){
        throw new UnsupportedOperationException("UtilLogger class");
    }

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logError(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public static void logWarn(String message) {
        logger.warn(message);
    }

}
