package seedu.bookKeeper;
public class InputParser {

    public static String[] extractCommandArgs(String input) {
        String[] commandArgs = input.strip().split(" ", 2); 
        switch (commandArgs[0]) {
            case "add-book":
                return extractAddBook(commandArgs[1]); // commandArgs[1] is the input that needs to be parsed (without the command)
            case "view-inventory":
                return commandArgs;
            case "remove-book":
                break;
            case "add-loan":
                break;
            case "delete-loan":
                break;
            case "view-loans":
                break;
            default:
                break;
            }
        // Add Implementation
        // First parse to find out which command, then call the parser for that command
        // Return String Array with commandArgs[0] being the command, followed by necessary parts to the command
        return null;
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