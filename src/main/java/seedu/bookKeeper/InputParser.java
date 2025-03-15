package seedu.bookKeeper;

public class InputParser {

    public static String[] extractCommandArgs(String input) throws IllegalArgumentException {
        return input.strip().split(" ", 2);
    }

    public static String[] extractXXXX(String input) {
        // Copy and Add Implementation
        return null;
    }

    public static String[] extractAddBook(String input) {
        String commandArgs[] = new String[5];

        String[] splitInput = input.strip().split("( a/)|( cat/)|( cond/)", 4);
        // Strip each element in the array
        for (int i = 0; i < splitInput.length; i++) {
            splitInput[i] = splitInput[i].strip();
        }

        commandArgs[0] = "add-book";
        commandArgs[1] = splitInput[0];
        commandArgs[2] = splitInput[1];
        commandArgs[3] = splitInput[2];
        commandArgs[4] = splitInput[3];

        return commandArgs;
    }
}