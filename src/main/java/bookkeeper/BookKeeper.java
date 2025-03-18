package bookkeeper;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class BookKeeper {
    /**
     * Main entry-point for the BookKeeper application.
     */
    private static final Logger logger = Logger.getLogger("BookKeeper Logger");

    public static void main(String[] args) {
        setupLogger();
        logger.info("Starting BookKeeper...");
        displayWelcomeMessage();
        InputHandler inputHandler = new InputHandler();
        inputHandler.askInput();
        logger.info("Exiting BookKeeper...");
    }

    public static void displayWelcomeMessage() {
        System.out.println("Welcome to BookKeeper.");
    }

    private static void setupLogger() {
        try {
            FileHandler fileHandler = new FileHandler("bookkeeper.log", true);
            fileHandler.setFormatter(new SimpleFormatter());

            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            for (Handler handler : handlers) {
                if (handler instanceof ConsoleHandler) {
                    rootLogger.removeHandler(handler);
                }
            }

            rootLogger.addHandler(fileHandler);
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }
}
