package bookkeeper;

import bookkeeper.exceptions.IncorrectFormatException;
import bookkeeper.exceptions.ErrorMessages;

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
     *      [4] - Note (Optional)
     * @throws IncorrectFormatException if the input format is invalid.
     */
    public static String[] extractAddBookArgs(String input) throws IncorrectFormatException {
        // Initialize variables for each argument
        String bookTitle = null;
        String author = null;
        String category = null;
        String condition = null;
        String location = null;
        String note = "";

        // Split the input into parts based on spaces
        String[] parts = input.trim().split("\\s+(?=\\w+/|$)");

        // Validate and extract the first argument as the book title
        if (parts.length == 0 || parts[0].startsWith("a/") || parts[0].startsWith("cat/") ||
                parts[0].startsWith("cond/") || parts[0].startsWith("loc/") || parts[0].startsWith("note/")) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_BOOK);
        }
        bookTitle = parts[0].trim();

        // Iterate through the remaining parts and extract arguments based on prefixes
        for (int i = 1; i < parts.length; i++) {
            String part = parts[i].trim();
            if (part.startsWith("a/")) {
                if (author != null) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_BOOK_DUPLICATE_PREFIX);
                }
                author = part.substring(2).trim();
            } else if (part.startsWith("cat/")) {
                if (category != null) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_BOOK_DUPLICATE_PREFIX);
                }
                category = part.substring(4).trim();
            } else if (part.startsWith("cond/")) {
                if (condition != null) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_BOOK_DUPLICATE_PREFIX);
                }
                condition = part.substring(5).trim();
            } else if (part.startsWith("loc/")) {
                if (location != null) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_BOOK_DUPLICATE_PREFIX);
                }
                location = part.substring(4).trim();
            } else if (part.startsWith("note/")) {
                if (!note.isEmpty()) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_BOOK_DUPLICATE_PREFIX);
                }
                note = part.substring(5).trim();
            } else {
                throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_BOOK);
            }
        }

        // Validate required fields
        if (bookTitle.isEmpty() || author == null || category == null || condition == null || location == null) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_BOOK);
        }

        // Return all fields, including the optional note
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
        // Initialize variables for each argument
        String bookTitle = null;
        String author = null;
        String category = null;
        String condition = null;
        String location = null;
        String note = "";

        // Split the input into parts based on spaces
        String[] parts = input.trim().split("\\s+(?=\\w+/|$)");

        // Validate and extract the first argument as the book title
        if (parts.length == 0 || parts[0].startsWith("a/") || parts[0].startsWith("cat/") ||
                parts[0].startsWith("cond/") || parts[0].startsWith("loc/") || parts[0].startsWith("note/")) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_UPDATE_BOOK);
        }
        bookTitle = parts[0].trim();

        // Iterate through the remaining parts and extract arguments based on prefixes
        for (int i = 1; i < parts.length; i++) {
            String part = parts[i].trim();
            if (part.startsWith("a/")) {
                if (author != null) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_UPDATE_BOOK_DUPLICATE_PREFIX);
                }
                author = part.substring(2).trim();
            } else if (part.startsWith("cat/")) {
                if (category != null) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_UPDATE_BOOK_DUPLICATE_PREFIX);
                }
                category = part.substring(4).trim();
            } else if (part.startsWith("cond/")) {
                if (condition != null) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_UPDATE_BOOK_DUPLICATE_PREFIX);
                }
                condition = part.substring(5).trim();
            } else if (part.startsWith("loc/")) {
                if (location != null) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_UPDATE_BOOK_DUPLICATE_PREFIX);
                }
                location = part.substring(4).trim();
            } else if (part.startsWith("note/")) {
                if (!note.isEmpty()) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_UPDATE_BOOK_DUPLICATE_PREFIX);
                }
                note = part.substring(5).trim();
            } else {
                throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_UPDATE_BOOK);
            }
        }

        // Validate required fields
        if (bookTitle.isEmpty() || author == null || category == null || condition == null || location == null) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_UPDATE_BOOK);
        }

        // Return all fields, including the optional note
        return new String[]{bookTitle, author, category, condition, location, note};
    }

    /**
     * Extracts the arguments for the add-loan command.
     * <p>
     * The expected input format is: BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE p/PHONE_NUMBER e/EMAIL
     * Example: "The Great Gatsby n/John Doe d/2023-12-01 p/1234567890 e/johndoe@example.com"
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
            if (part.startsWith("n/")) {
                if (borrowerName != null) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_LOAN_DUPLICATE_PREFIX);
                }
                borrowerName = part.substring(2).trim();
            } else if (part.startsWith("d/")) {
                if (returnDate != null) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_LOAN_DUPLICATE_PREFIX);
                }
                returnDate = part.substring(2).trim();
            } else if (part.startsWith("p/")) {
                if (phoneNumber != null) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_LOAN_DUPLICATE_PREFIX);
                }
                phoneNumber = part.substring(2).trim();
            } else if (part.startsWith("e/")) {
                if (email != null) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_LOAN_DUPLICATE_PREFIX);
                }
                email = part.substring(2).trim();
            } else {
                throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_LOAN);
            }
        }

        // Validate required fields
        if (bookTitle.isEmpty() || borrowerName == null || returnDate == null || phoneNumber == null || email == null) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_LOAN);
        }

        // Return all fields
        return new String[]{bookTitle, borrowerName, returnDate, phoneNumber, email};
    }

    /**
     * Extracts the arguments for the delete-loan command.
     * <p>
     * The expected input format is: BOOK_TITLE n/BORROWER_NAME
     * Example: "The Great Gatsby n/John Doe"
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

        for (int i = 0; i < splitInput.length; i++) {
            if (splitInput[i].isBlank()) {
                throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_DELETE_LOAN);
            }
            commandArgs[i] = splitInput[i].trim();
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

    public static String[] extractEditLoanArgs(String input) throws IncorrectFormatException {
        // Initialize variables for each argument
        String bookTitle = null;
        String borrowerName = null;
        String returnDate = null;
        String phoneNumber = null;
        String email = null;

        // Split the input into parts based on spaces
        String[] parts = input.trim().split("\\s+(?=\\w+/|$)");

        // Validate and extract the first argument as the book title
        if (parts.length == 0 || parts[0].startsWith("n/") || parts[0].startsWith("d/") ||
                parts[0].startsWith("p/") || parts[0].startsWith("e/")) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_EDIT_LOAN);
        }
        bookTitle = parts[0].trim();

        // Iterate through the remaining parts and extract arguments based on prefixes
        for (int i = 1; i < parts.length; i++) {
            String part = parts[i].trim();
            if (part.startsWith("n/")) {
                if (borrowerName != null) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_EDIT_LOAN_DUPLICATE_PREFIX);
                }
                borrowerName = part.substring(2).trim();
            } else if (part.startsWith("d/")) {
                if (returnDate != null) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_EDIT_LOAN_DUPLICATE_PREFIX);
                }
                returnDate = part.substring(2).trim();
            } else if (part.startsWith("p/")) {
                if (phoneNumber != null) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_EDIT_LOAN_DUPLICATE_PREFIX);
                }
                phoneNumber = part.substring(2).trim();
            } else if (part.startsWith("e/")) {
                if (email != null) {
                    throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_EDIT_LOAN_DUPLICATE_PREFIX);
                }
                email = part.substring(2).trim();
            } else {
                throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_EDIT_LOAN);
            }
        }

        // Validate required fields
        if (bookTitle.isEmpty() || borrowerName == null || returnDate == null || phoneNumber == null || email == null) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_EDIT_LOAN);
        }

        // Return all fields
        return new String[]{bookTitle, borrowerName, returnDate, phoneNumber, email};
    }
}
