package bookkeeper;

import bookkeeper.logic.InputHandler;
import bookkeeper.storage.LoggerConfig;
import bookkeeper.ui.Formatter;

import java.util.logging.Logger;

public class BookKeeper {
    /**
     * Main entry-point for the BookKeeper application.
     */
    private static final Logger logger = Logger.getLogger(BookKeeper.class.getName());

    public static void main(String[] args) {
        LoggerConfig.configureLogger(logger); // Configure the logger
        logger.info("Starting BookKeeper...");
        displayWelcomeMessage();
        InputHandler inputHandler = new InputHandler();
        inputHandler.askInput();
        logger.info("Exiting BookKeeper...");
    }

    public static void displayWelcomeMessage() {
        Formatter.printBorderedMessage("Welcome to BookKeeper.");
    }
}
