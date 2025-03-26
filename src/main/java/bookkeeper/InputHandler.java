package bookkeeper;

import java.util.logging.Logger;

import bookkeeper.exceptions.BookNotFoundException;
import bookkeeper.exceptions.IncorrectFormatException;

import java.util.Scanner;

public class InputHandler {
    private static final Logger logger = Logger.getLogger(InputHandler.class.getName());
    private BookList bookList;
    private LoanList loanList;

    public InputHandler() {
        LoggerConfig.configureLogger(logger); // Configure the logger
        this.bookList = new BookList("Inventory");
        this.loanList = new LoanList("Loan List");
        logger.info("InputHandler initialized");
    }

    public void askInput() {
        boolean isAskingInput = true;
        String userInputLine;
        Scanner scanner = new Scanner(System.in);

        displayHelp();

        while (isAskingInput) {
            System.out.println("Enter a command:");

            if (!scanner.hasNextLine()) {  // Prevents NoSuchElementException
                break;
            }
            userInputLine = scanner.nextLine();
            if (userInputLine.isEmpty()) {
                Formatter.printBorderedMessage("Please enter a command");
            } else {
                try {
                    String[] commandArgs = InputParser.extractCommandArgs(userInputLine);
                    assert commandArgs.length > 0 : "commandArgs should have at least one element";

                    switch (commandArgs[0]) {
                    case "add-book":
                        addBook(commandArgs);
                        break;
                    case "view-inventory":
                        bookList.viewBookList();
                        break;
                    case "remove-book":
                        removeBook(commandArgs);
                        break;
                    case "add-loan":
                        addLoan(commandArgs);
                        break;
                    case "delete-loan":
                        deleteLoan(commandArgs);
                        break;
                    case "view-loans":
                        loanList.viewLoanList();
                        break;
                    case "add-note":
                        addNote(commandArgs);
                        break;
                    case "delete-note":
                        deleteNote(commandArgs);
                        break;
                    case "update-book":
                        updateBook(commandArgs);
                        break;
                    case "search-book":
                        searchBook(commandArgs);
                        break;  
                    case "list-category":
                        listCategory(commandArgs);
                        break;
                    case "help":
                        displayHelp();
                        break;
                    case "exit":
                        Formatter.printBorderedMessage("Exiting BookKeeper...");
                        isAskingInput = false;
                        break;
                    default:
                        throw new IncorrectFormatException("Unknown command: " + commandArgs[0]);
                    }
                } catch (IncorrectFormatException | BookNotFoundException e) {
                    Formatter.printBorderedMessage(e.getMessage());
                }
            }
        }
    }

    private void displayHelp() {
        Formatter.printSimpleMessage("""
                -------------------------------------------------------------------------------------------------------
                | Action         | Format                                                                             |
                |----------------|------------------------------------------------------------------------------------|
                | Add Book       | `add-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION loc/LOCATION [note/NOTE]`|
                | View Inventory | `view-inventory`                                                                   |
                | Remove Book    | `remove-book BOOK_TITLE`                                                           |
                | Search Book    | `search-book KEYWORD`                                                              |
                | List Category  | `list-category CATEGORY`                                                           |
                | Add Loan       | `add-loan BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE`                                |
                | Delete Loan    | `delete-loan BOOK_TITLE n/BORROWER_NAME`                                           |
                | View Loans     | `view-loans`                                                                       |
                | Add note       | `add-note BOOK_TITLE note/NOTE`                                                    |
                | Delete note    | `delete-note BOOK_TITLE`                                                           |
                -------------------------------------------------------------------------------------------------------
                """);
    }

    /**
     * Adds loan object to loanList by first extracting arguments needed to create loan object.
     * Before adding, book has to exist in bookList and is available for loan.
     *
     * @param commandArgs The parsed command arguments.
     * @throws IncorrectFormatException If the input format is invalid.
     * @throws BookNotFoundException    If the book is not found in the inventory.
     * @throws BookNotFoundException    If the book is already on loan.
     */
    private void addLoan(String[] commandArgs) throws IncorrectFormatException, BookNotFoundException {
        if (commandArgs.length < 2) {
            throw new IncorrectFormatException("Invalid format for add-loan.\n" +
                    "Expected format: add-loan BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE");
        }
        try {
            String[] loanArgs = InputParser.extractAddLoanArgs(commandArgs[1]);
            Book loanedBook = bookList.findBookByTitle(loanArgs[0]);
            if (loanedBook == null) {
                Formatter.printBorderedMessage("Book not found in inventory: " + loanArgs[0]);
            } else if (loanedBook.getOnLoan()) {
                assert loanedBook.getTitle() != null : "Loaned book must have a title";
                Formatter.printBorderedMessage("The book " + loanArgs[0] + " is currently out on loan.");
            } else {
                Loan loan = new Loan(loanedBook, loanArgs[2], loanArgs[1]);
                loanList.addLoan(loan);
                loanedBook.setOnLoan(true);
                Formatter.printBorderedMessage("Loan added successfully for book: " + loanedBook.getTitle());
            }
        } catch (IllegalArgumentException e) {
            Formatter.printBorderedMessage(e.getMessage());
        }
    }

    /**
     * Extract arguments needed to create book object and adds book object to book list.
     *
     * @param commandArgs The parsed command arguments.
     * @throws IncorrectFormatException If the input format is invalid.
     */
    private void addBook(String[] commandArgs) throws IncorrectFormatException {
        if (commandArgs.length < 2) {
            throw new IncorrectFormatException("Invalid format for add-book.\n" +
                "Expected format: add-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION loc/LOCATION [note/NOTE]");
        }
        String[] bookArgs = InputParser.extractAddBookArgs(commandArgs[1]);
        assert bookArgs.length >= 5 : "Book arguments should contain at least 5 elements";

        String bookTitle = bookArgs[0]; //Already trimmed whitespaces in extractAddBookArgs

        // Check if book already exists in the inventory
        if (bookList.findBookByTitle(bookTitle) != null) {
            Formatter.printBorderedMessage("Book already exists in inventory: " + bookTitle);
            return;
        }

        // Handle optional note
        String note = bookArgs.length == 6 ? bookArgs[5] : ""; // Default to empty string if note is not provided

        // Add the new book to the book list
        Book newBook = new Book(bookTitle, bookArgs[1], bookArgs[2], bookArgs[3], bookArgs[4], note);
        bookList.addBook(newBook);
        Formatter.printBorderedMessage("New book added: " + newBook.getTitle());
    }

    /**
     * Extract arguments needed to remove book object and removes book object from book list.
     *
     * @param commandArgs The parsed command arguments.
     * @throws IncorrectFormatException If the input format is invalid.
     * @throws BookNotFoundException    If the book is not found in the inventory.
     */
    private void removeBook(String[] commandArgs) throws IncorrectFormatException, BookNotFoundException {
        if (commandArgs.length != 2) {
            throw new IncorrectFormatException("Invalid format for remove-book.\n" +
                    "Expected format: remove-book BOOK_TITLE");
        }
        String bookTitle = commandArgs[1].trim();
        Book toRemove = bookList.findBookByTitle(bookTitle);

        if (toRemove == null) {
            Formatter.printBorderedMessage("Book not found in inventory: " + bookTitle);
        } else {
            assert toRemove.getTitle() != null : "Book to remove must have a valid title";
            loanList.removeLoansByBook(toRemove);
            bookList.removeBook(toRemove);
            Formatter.printBorderedMessage("Removed book: " + toRemove.getTitle());
        }
    }

    /**
     * Extract arguments needed to delete loan and delete loan
     * Checks if book and loan exist before deleting
     *
     * @param commandArgs The parsed command arguments.
     * @throws IncorrectFormatException If the input format is invalid.
     * @throws BookNotFoundException    If the book is not found in the inventory.
     */
    private void deleteLoan(String[] commandArgs) throws IncorrectFormatException, BookNotFoundException {
        if (commandArgs.length < 2) {
            throw new IncorrectFormatException("Invalid format for delete-loan.\n" +
                    "Expected format: delete-loan BOOK_TITLE n/BORROWER_NAME");
        }
        try {
            String[] deleteLoanArgs = InputParser.extractDeleteLoanArgs(commandArgs[1]);
            assert deleteLoanArgs.length == 2;
            String bookTitle = deleteLoanArgs[0];
            String borrowerName = deleteLoanArgs[1];
            Book loanedBook = bookList.findBookByTitle(bookTitle);
            Loan loan = loanList.findLoan(loanedBook, borrowerName);
            if (loanedBook == null) {
                Formatter.printBorderedMessage("Book not found in inventory: " + bookTitle);
            } else if (!loanedBook.getOnLoan()) {
                Formatter.printBorderedMessage("The book " + bookTitle + " is not currently out on loan.");
            } else if (loan == null) {
                Formatter.printBorderedMessage("No such loan with book title " + bookTitle +
                        " and borrower " + borrowerName);
            } else {
                loanList.deleteLoan(loan);
                loanedBook.setOnLoan(false);
                Formatter.printBorderedMessage("Loan deleted successfully for book: " + loanedBook.getTitle());
            }
        } catch (IllegalArgumentException e) {
            Formatter.printBorderedMessage(e.getMessage());
        }
    }

    /**
     * Prints out all books in BookList that contains the keyword.
     * 
     * @param commandArgs The parsed command arguments
     * @throws IncorrectFormatException If the input format is invalid
     */
    private void searchBook(String[] commandArgs) throws IncorrectFormatException {
        if (commandArgs.length < 2) {
            throw new IncorrectFormatException("Invalid format for search-book.\n" +
                    "Expected format: search-book KEYWORD");
        }

        String keyword = commandArgs[1].trim();
        Formatter.printBookList(bookList.findBooksByKeyword(keyword));
    }

    /**
     * Prints out all books in BookList that is of the provided category.
     * 
     * @param commandArgs The parsed command arguments
     * @throws IncorrectFormatException If the input format is invalid
     */
    private void listCategory(String[] commandArgs) throws IncorrectFormatException {
        if (commandArgs.length < 2){
            throw new IncorrectFormatException("Invalid format for list-category.\n" +
                    "Expected format: list-category CATEGORY");
        }

        String category = commandArgs[1].trim();
        Formatter.printBookList(bookList.findBooksByCategory(category));
    }

    /**
     * Adds a note to a specified book.
     *
     * @param commandArgs The parsed command arguments.
     * @throws IncorrectFormatException If the input format is invalid.
     * @throws BookNotFoundException    If the book is not found in the inventory.
     */
    private void addNote(String[] commandArgs) throws IncorrectFormatException, BookNotFoundException {
        if (commandArgs.length < 2) {
            throw new IncorrectFormatException("Invalid format for add-note.\n" +
                    "Expected format: add-note BOOK_TITLE note/NOTE");
        }

        String[] noteArgs = InputParser.extractAddNoteArgs(commandArgs[1]);
        String bookTitle = noteArgs[0];
        String note = noteArgs[1];

        Book book = bookList.findBookByTitle(bookTitle);
        if (book == null) {
            throw new BookNotFoundException("Book not found in inventory: " + bookTitle);
        }

        if (!book.getNote().isEmpty()) {
            Formatter.printBorderedMessage("Book already has a note:\n" + book.getNote());
            return;
        }

        book.setNote(note);
        Formatter.printBorderedMessage("Note added to book: " + bookTitle);
    }

    /**
     * Deletes the note from a specified book.
     *
     * @param commandArgs The parsed command arguments.
     * @throws IncorrectFormatException If the input format is invalid.
     * @throws BookNotFoundException    If the book is not found in the inventory.
     */
    private void deleteNote(String[] commandArgs) throws IncorrectFormatException, BookNotFoundException {
        if (commandArgs.length != 2) {
            throw new IncorrectFormatException("Invalid format for delete-note.\n" +
                    "Expected format: delete-note BOOK_TITLE");
        }

        String bookTitle = commandArgs[1].trim();

        Book book = bookList.findBookByTitle(bookTitle);
        if (book == null) {
            throw new BookNotFoundException("Book not found in inventory: " + bookTitle);
        }

        if (book.getNote().isEmpty()) {
            Formatter.printBorderedMessage("No note exists for the book: " + bookTitle);
            return;
        }

        book.setNote("");
        Formatter.printBorderedMessage("Note deleted for book: " + bookTitle);
    }

    /**
     * Updates details of an existing book.
     *
     * @param commandArgs The parsed command arguments.
     * @throws IncorrectFormatException If the input format is invalid.
     * @throws BookNotFoundException    If the book is not found in the inventory.
     */
    private void updateBook(String[] commandArgs) throws IncorrectFormatException, BookNotFoundException {
        if (commandArgs.length < 2) {
            throw new IncorrectFormatException("Invalid format for update-book.\n" +
                    "Expected format: update-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION [note/NOTE]");
        }
        String[] bookArgs = InputParser.extractUpdateBookArgs(commandArgs[1]);
        assert bookArgs.length >= 4 : "Book arguments should contain at least 4 elements";

        String bookTitle = bookArgs[0].trim();

        // Check if book already exists in the inventory
        Book book = bookList.findBookByTitle(bookTitle);
        if (book == null) {
            throw new BookNotFoundException("Book not found in inventory: " + bookTitle);
        }

        book.setAuthor(bookArgs[1]);
        book.setCategory(bookArgs[2]);
        book.setCondition(bookArgs[3]);
        if(bookArgs.length == 5 && !bookArgs[4].isBlank()) {
            book.setNote(bookArgs[4]);
        }
        Formatter.printBorderedMessage("Book Updated:\n" + book.toString());
    }

}
