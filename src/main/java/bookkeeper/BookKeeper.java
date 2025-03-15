package bookkeeper;

public class BookKeeper {
    /**
     * Main entry-point for the BookKeeper application.
     */
    public static void main(String[] args) {
        displayWelcomeMessage();
        InputHandler inputHandler = new InputHandler();
        inputHandler.askInput();
    }

    public static void displayWelcomeMessage() {
        System.out.println("Welcome to BookKeeper.");
    }
}
