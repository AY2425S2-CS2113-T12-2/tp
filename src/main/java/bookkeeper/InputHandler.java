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

        while (isAskingInput) {
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
                    "Expected format: add-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION");
        }
        String[] bookArgs = InputParser.extractAddBookArgs(commandArgs[1]);
        assert bookArgs.length == 4 : "Book arguments should contain exactly 4 elements";

        // Trim whitespaces from the book title
        String bookTitle = bookArgs[0].trim();

        // Check if book already exists in the inventory
        if (bookList.findBookByTitle(bookTitle) != null) {
            Formatter.printBorderedMessage("Book already exists in inventory: " + bookTitle);
            return;
        }

        Book newBook = new Book(bookArgs[0], bookArgs[1], bookArgs[2], bookArgs[3]);
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
        String bookTitle = commandArgs[1];
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
}
