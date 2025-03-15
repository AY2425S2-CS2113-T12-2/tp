package bookkeeper;

public class InputParser {

    public static String[] extractCommandArgs(String input) throws IllegalArgumentException {
        return input.trim().split(" ", 2);
    }

    public static String[] extractXXXX(String input) {
        // Copy and Add Implementation
        return null;
    }

    public static String[] extractAddBook(String input) {
        String[] commandArgs = new String[5];

        String[] splitInput = input.trim().split("( a/)|( cat/)|( cond/)", 4);
        // Strip each element in the array
        for (int i = 0; i < splitInput.length; i++) {
            splitInput[i] = splitInput[i].trim();
        }

        commandArgs[0] = "add-book";
        commandArgs[1] = splitInput[0];
        commandArgs[2] = splitInput[1];
        commandArgs[3] = splitInput[2];
        commandArgs[4] = splitInput[3];

        return commandArgs;
    }

    public static String[] extractAddLoan(String input) {
        String[] commandArgs = new String[4];

        String[] splitInput = input.trim().split("( n/)|( d/)", 3);
        if (splitInput.length < 3) {
            throw new IllegalArgumentException("Invalid format for add-loan. Expected format: add-loan BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE");
        }

        commandArgs[0] = "add-loan";
        commandArgs[1] = splitInput[0]; // BOOK_TITLE
        commandArgs[2] = splitInput[1]; // BORROWER_NAME
        commandArgs[3] = splitInput[2]; // RETURN_DATE

        return commandArgs;
    }
}
