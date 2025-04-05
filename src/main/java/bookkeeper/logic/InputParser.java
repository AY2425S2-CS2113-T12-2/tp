package bookkeeper.logic;

import bookkeeper.exceptions.IncorrectFormatException;
import bookkeeper.exceptions.ErrorMessages;

import java.util.HashSet;
import java.util.Set;

public class InputParser {

    public static String[] extractCommandArgs(String input) throws IncorrectFormatException {
        String[] commandArgs = input.trim().split(" ", 2);
        if (commandArgs.length < 1) {
            throw new IncorrectFormatException("Invalid command format.\nExpected: COMMAND [ARGUMENTS]");
        }
        return commandArgs;
    }


    /**
     * Extracts the arguments for the add-book command.
     * <p>
     * The expected input format is:
     * add-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION [note/NOTE]
     * Example: "Cheese Chronicles a/Mouse cat/Adventure cond/Good"
     *
     * @param input The user input for the add-book command.
     * @return An array of strings containing the arguments for the add-book command:
     *      [0] - Book title
     *      [1] - Author
     *      [2] - Category
     *      [3] - Condition
     *      [4] - Location
     *      [5] - Note (Optional)
     * @throws IncorrectFormatException if the input format is invalid.
     */
    public static String[] extractAddBookArgs(String input) throws IncorrectFormatException {
        String bookTitle = null;
        String author = null;
        String category = null;
        String condition = null;
        String location = null;
        String note = "";

        Set<String> processedPrefixes = new HashSet<>();
        String[] parts = input.trim().split("\\s+(?=\\w+/|$)");

        if (parts.length == 0 || parts[0].startsWith("a/") || parts[0].startsWith("cat/") ||
                parts[0].startsWith("cond/") || parts[0].startsWith("loc/") || parts[0].startsWith("note/")) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_BOOK);
        }
        bookTitle = parts[0].trim();

        for (int i = 1; i < parts.length; i++) {
            String part = parts[i].trim();
            String prefix = part.substring(0, part.indexOf("/") + 1);

            if (processedPrefixes.contains(prefix)) {
                throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_BOOK_DUPLICATE_PREFIX);
            }
            processedPrefixes.add(prefix);

            if (part.startsWith("a/")) {
                author = part.substring(2).trim();
            } else if (part.startsWith("cat/")) {
                category = part.substring(4).trim();
            } else if (part.startsWith("cond/")) {
                condition = part.substring(5).trim();
            } else if (part.startsWith("loc/")) {
                location = part.substring(4).trim();
            } else if (part.startsWith("note/")) {
                note = part.substring(5).trim();
            } else {
                throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_BOOK);
            }
        }

        if (bookTitle.isEmpty() || author == null || author.isEmpty() ||
                category == null || category.isEmpty() ||
                condition == null || condition.isEmpty() ||
                location == null || location.isEmpty()) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_BOOK);
        }

        return new String[]{bookTitle, author, category, condition, location, note};
    }

    /**
     * Extracts the arguments for the update-book command.
     * <p>
     * The expected input format is:
     * BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION [note/NOTE]
     * Example: "Cheese Chronicles a/Mouse cat/Adventure cond/Good"
     *
     * @param input The user input for the update-book command.
     * @return An array of strings containing the arguments for the update-book command:
     *      [0] - Book title
     *      [1] - Author
     *      [2] - Category
     *      [3] - Condition
     *      [4] - Location
     *      [5] - Note (Optional)
     * @throws IncorrectFormatException if the input format is invalid.
     */
    public static String[] extractUpdateBookArgs(String input) throws IncorrectFormatException {
        String bookTitle = null;
        String author = null;
        String category = null;
        String condition = null;
        String location = null;
        String note = "";

        Set<String> processedPrefixes = new HashSet<>();
        String[] parts = input.trim().split("\\s+(?=\\w+/|$)");

        if (parts.length == 0 || parts[0].startsWith("a/") || parts[0].startsWith("cat/") ||
                parts[0].startsWith("cond/") || parts[0].startsWith("loc/") || parts[0].startsWith("note/")) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_UPDATE_BOOK);
        }
        bookTitle = parts[0].trim();

        for (int i = 1; i < parts.length; i++) {
            String part = parts[i].trim();
            String prefix = part.substring(0, part.indexOf("/") + 1);

            if (processedPrefixes.contains(prefix)) {
                throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_UPDATE_BOOK_DUPLICATE_PREFIX);
            }
            processedPrefixes.add(prefix);

            if (part.startsWith("a/")) {
                author = part.substring(2).trim();
            } else if (part.startsWith("cat/")) {
                category = part.substring(4).trim();
            } else if (part.startsWith("cond/")) {
                condition = part.substring(5).trim();
            } else if (part.startsWith("loc/")) {
                location = part.substring(4).trim();
            } else if (part.startsWith("note/")) {
                note = part.substring(5).trim();
            } else {
                throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_UPDATE_BOOK);
            }
        }

        if (bookTitle.isEmpty() || author == null || author.isEmpty() ||
                category == null || category.isEmpty() ||
                condition == null || condition.isEmpty() ||
                location == null || location.isEmpty()) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_UPDATE_BOOK);
        }

        return new String[]{bookTitle, author, category, condition, location, note};
    }

    /**
     * Extracts the arguments for the add-loan command.
     * <p>
     * The expected input format is: BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE p/PHONE_NUMBER e/EMAIL
     * Example: "The Great Gatsby n/John Doe d/01-12-2026 p/1234567890 e/johndoe@example.com"
     *
     * @param input The user input for the add-loan command.
     * @return An array of strings containing the arguments for the add-loan command:
     *      [0] - Book title
     *      [1] - Borrower's name
     *      [2] - Return date
     *      [3] - Phone number
     *      [4] - Email
     * @throws IncorrectFormatException if the input format is invalid.
     */
    public static String[] extractAddLoanArgs(String input) throws IncorrectFormatException {
        // Initialize variables for each argument
        String bookTitle = null;
        String borrowerName = null;
        String returnDate = null;
        String phoneNumber = null;
        String email = null;

        // Set to track processed prefixes
        Set<String> processedPrefixes = new HashSet<>();

        // Split the input into parts based on spaces
        String[] parts = input.trim().split("\\s+(?=\\w+/|$)");

        // Validate and extract the first argument as the book title
        if (parts.length == 0 || parts[0].startsWith("n/") || parts[0].startsWith("d/") ||
                parts[0].startsWith("p/") || parts[0].startsWith("e/")) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_LOAN);
        }
        bookTitle = parts[0].trim();

        // Iterate through the remaining parts and extract arguments based on prefixes
        for (int i = 1; i < parts.length; i++) {
            String part = parts[i].trim();
            String prefix = part.substring(0, 2); // Extract the prefix (e.g., "n/", "d/")

            // Check for duplicate prefixes
            if (processedPrefixes.contains(prefix)) {
                throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_LOAN_DUPLICATE_PREFIX);
            }
            processedPrefixes.add(prefix); // Mark the prefix as processed

            // Extract the argument based on the prefix
            if (part.startsWith("n/")) {
                borrowerName = part.substring(2).trim();
            } else if (part.startsWith("d/")) {
                returnDate = part.substring(2).trim();
            } else if (part.startsWith("p/")) {
                phoneNumber = part.substring(2).trim();
                if (!phoneNumber.matches("^[0-9]+$")) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_PHONE_NUMBER_ADD_LOAN);
                }
            } else if (part.startsWith("e/")) {
                email = part.substring(2).trim();
                if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_EMAIL_ADD_LOAN);
                }
            }
        }

        // Validate required fields
        if (bookTitle.isEmpty() || borrowerName == null || borrowerName.isEmpty() ||
                returnDate == null || returnDate.isEmpty() ||
                phoneNumber == null || phoneNumber.isEmpty() ||
                email == null || email.isEmpty()) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_LOAN);
        }

        // Return all fields
        return new String[]{bookTitle, borrowerName, returnDate, phoneNumber, email};
    }

    /**
     * Extracts the arguments for the delete-loan command.
     * <p>
     * The expected input format is: BOOK_TITLE
     * Example: "The Great Gatsby"
     *
     * @param input The user input for the delete-loan command.
     * @return An array of strings containing the arguments for the add-loan command:
     *      [0] - Book title
     *      [1] - Borrower's name
     * @throws IncorrectFormatException if the input format is invalid.
     */
    public static String[] extractDeleteLoanArgs(String input) throws IncorrectFormatException {
        String[] commandArgs = new String[2];
        String[] splitInput = input.trim().split("( n/)", 2);

        if (splitInput.length < 2) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_DELETE_LOAN);
        }

        commandArgs[0] = splitInput[0].trim();
        commandArgs[1] = splitInput[1].trim();

        if (commandArgs[0].isEmpty() || commandArgs[1].isEmpty()) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_DELETE_LOAN);
        }

        return commandArgs;
    }

    public static String[] extractAddNoteArgs(String input) throws IncorrectFormatException {
        String[] splitInput = input.trim().split(" note/", 2);

        if (splitInput.length != 2) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_NOTE);
        }

        String bookTitle = splitInput[0].trim();
        String note = splitInput[1].trim();

        if (bookTitle.isBlank() || note.isBlank()) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_NOTE);
        }

        return new String[]{bookTitle, note};
    }

    public static String[] extractUpdateNoteArgs(String input) throws IncorrectFormatException {
        String[] splitInput = input.trim().split(" note/", 2);

        if (splitInput.length != 2) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_UPDATE_NOTE);
        }

        String bookTitle = splitInput[0].trim();
        String note = splitInput[1].trim();

        if (bookTitle.isBlank() || note.isBlank()) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_UPDATE_NOTE);
        }

        return new String[]{bookTitle, note};
    }

    public static String[] extractEditLoanArgs(String input) throws IncorrectFormatException {
        String bookTitle = null;
        String borrowerName = null;
        String returnDate = null;
        String phoneNumber = null;
        String email = null;

        Set<String> processedPrefixes = new HashSet<>();
        String[] parts = input.trim().split("\\s+(?=\\w+/|$)");

        if (parts.length == 0 || parts[0].startsWith("n/") || parts[0].startsWith("d/") ||
                parts[0].startsWith("p/") || parts[0].startsWith("e/")) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_EDIT_LOAN);
        }
        bookTitle = parts[0].trim();

        for (int i = 1; i < parts.length; i++) {
            String part = parts[i].trim();
            String prefix = part.substring(0, part.indexOf("/") + 1);

            if (processedPrefixes.contains(prefix)) {
                throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_EDIT_LOAN_DUPLICATE_PREFIX);
            }
            processedPrefixes.add(prefix);

            if (part.startsWith("n/")) {
                borrowerName = part.substring(2).trim();
            } else if (part.startsWith("d/")) {
                returnDate = part.substring(2).trim();
            } else if (part.startsWith("p/")) {
                phoneNumber = part.substring(2).trim();
                if (!phoneNumber.matches("^[0-9]+$")) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_PHONE_NUMBER_EDIT_LOAN);
                }
            } else if (part.startsWith("e/")) {
                email = part.substring(2).trim();
                if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_EMAIL_EDIT_LOAN);
                }
            } else {
                throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_EDIT_LOAN);
            }
        }

        if (bookTitle.isEmpty() || borrowerName == null || borrowerName.isEmpty() ||
                returnDate == null || returnDate.isEmpty() ||
                phoneNumber == null || phoneNumber.isEmpty() ||
                email == null || email.isEmpty()) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_EDIT_LOAN);
        }

        return new String[]{bookTitle, borrowerName, returnDate, phoneNumber, email};
    }
}
