package bookkeeper.storage;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerConfig {
    private static FileHandler fileHandler;

    static {
        try {
            // Create a FileHandler that writes to "bookkeeper.log" in append mode
            fileHandler = new FileHandler("bookkeeper.log", true);
            fileHandler.setFormatter(new SimpleFormatter()); // Use a simple text format
        } catch (IOException e) {
            Logger.getLogger(LoggerConfig.class.getName()).severe("Failed to initialize FileHandler: " +
                    e.getMessage());
        }
    }

    public static void configureLogger(Logger logger) {
        if (fileHandler != null) {
            logger.addHandler(fileHandler); // Add the shared FileHandler
            logger.setUseParentHandlers(false); // Disable console logging if needed
        }
    }
}
