package bookkeeper.logic;

import java.util.Scanner;
import java.util.logging.Logger;

import bookkeeper.list.BookList;
import bookkeeper.list.LoanList;
import bookkeeper.storage.LoggerConfig;
import bookkeeper.storage.Storage;
import bookkeeper.exceptions.BookNotFoundException;
import bookkeeper.exceptions.IncorrectFormatException;
import bookkeeper.exceptions.InvalidArgumentException;
import bookkeeper.exceptions.ErrorMessages;
import bookkeeper.model.Book;
import bookkeeper.model.Loan;
import bookkeeper.ui.Formatter;

public class InputHandler {
    private static final Logger logger = Logger.getLogger(InputHandler.class.getName());
    private final BookList bookList;
    private final LoanList loanList;

    public InputHandler() {
        LoggerConfig.configureLogger(logger); // Configure the logger
        this.bookList = new BookList("Inventory", Storage.loadInventory());
        this.loanList = new LoanList("Loan List", Storage.loadLoans(this.bookList));
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
            } else if (userInputLine.contains("|")){
                Formatter.printBorderedMessage("Please do not use \"|\" in your inputs");
            } else {
                Storage.validateStorage(bookList, loanList);
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
                    case "edit-loan":
                        editLoan(commandArgs);
                        break;
                    case "view-loans":
                        loanList.viewLoanList();
                        break;
                    case "update-book":
                        updateBook(commandArgs);
                        break;
                    case "search-title":
                        searchBook(commandArgs);
                        break;
                    case "list-category":
                        listCategory(commandArgs);
                        break;
                    case "update-title":
                        updateTitle(commandArgs);
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
                } catch (IncorrectFormatException | BookNotFoundException | InvalidArgumentException e) {
                    Formatter.printBorderedMessage(e.getMessage());
                }
            }
        }
    }


    private void displayHelp() {
        Formatter.printSimpleMessage("""
            ---------------------------------------------------------------------------------------------------------
            | Action         | Format                                                                               |
            |----------------|--------------------------------------------------------------------------------------|
            | Add Book       | `add-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION loc/LOCATION [note/NOTE]`  |
            | Remove Book    | `remove-book BOOK_TITLE`                                                             |
            | Update Book    | `update-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION loc/LOCATION note/NOTE` |
            | Update Title   | `update-title BOOK_TITLE new/NEW_TITLE                                               |
            | Search Book    | `search-title KEYWORD`                                                                |
            | View Inventory | `view-inventory`                                                                     |
            | List Category  | `list-category CATEGORY`                                                             |
            | Add Loan       | `add-loan BOOK_TITLE n/BORROWER_NAME d/RETURN_DATE p/PHONE_NUMBER e/EMAIL`           |
            | Delete Loan    | `delete-loan BOOK_TITLE`                                                             |
            | Edit Loan      | `edit-loan BOOK_TITLE [n/BORROWER_NAME] [d/RETURN_DATE] [p/PHONE_NUMBER] [e/EMAIL]`  |
            | View Loans     | `view-loans`                                                                         |
            | Display Help   | `help`                                                                               |
            | Exit Program   | `exit`                                                                               |
            ---------------------------------------------------------------------------------------------------------
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
     * @throws InvalidArgumentException 
     */
    private void addLoan(String[] commandArgs) throws IncorrectFormatException, BookNotFoundException, 
            InvalidArgumentException {
        if (commandArgs.length < 2) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_LOAN);
        }
        try {
            String[] loanArgs = InputParser.extractAddLoanArgs(commandArgs[1]);
            Book loanedBook = bookList.findBookByTitle(loanArgs[0]);
            if (loanedBook == null) {
                Formatter.printBorderedMessage("Book not found in inventory: " + loanArgs[0]);
            } else if (loanedBook.isOnLoan()) {
                assert loanedBook.getTitle() != null : "Loaned book must have a title";
                Formatter.printBorderedMessage("The book " + loanArgs[0] + " is currently out on loan.");
            } else {
                Loan loan = new Loan(loanedBook, loanArgs[1], loanArgs[2], loanArgs[3], loanArgs[4]);
                loanList.addLoan(loan);
                loanedBook.setOnLoan(true);
                Formatter.printBorderedMessage("Loan added successfully for book: " + loanedBook.getTitle());
                Storage.saveLoans(loanList);
                Storage.saveInventory(bookList); //to update the onLoan status of the book in inventory
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
    private void addBook(String[] commandArgs) throws IncorrectFormatException, IllegalArgumentException {
        if (commandArgs.length < 2) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_ADD_BOOK);
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

        try {
            // Add the new book to the book list
            Book newBook = new Book(bookTitle, bookArgs[1], bookArgs[2], bookArgs[3], bookArgs[4], note);
            bookList.addBook(newBook);
            Formatter.printBorderedMessage("New book added: " + newBook.getTitle());
            Storage.saveInventory(bookList);
        } catch (IllegalArgumentException e) {
            Formatter.printBorderedMessage(e.getMessage());
        }

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
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_REMOVE_BOOK);
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
            Storage.saveInventory(bookList);
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
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_DELETE_LOAN);
        }
        try {
            String bookTitle = commandArgs[1];
            Book loanedBook = bookList.findBookByTitle(bookTitle);
            Loan loan = loanList.findLoan(loanedBook);
            if (loanedBook == null) {
                Formatter.printBorderedMessage("Book not found in inventory: " + bookTitle);
            } else if (!loanedBook.isOnLoan()) {
                Formatter.printBorderedMessage("The book " + bookTitle + " is not currently out on loan.");
            } else if (loan == null) {
                Formatter.printBorderedMessage("No such loan with book title " + bookTitle);
            } else {
                loanList.deleteLoan(loan);
                loanedBook.setOnLoan(false);
                Formatter.printBorderedMessage("Loan deleted successfully for book: " + bookTitle);
                Storage.saveLoans(loanList);
                Storage.saveInventory(bookList); //to update the onLoan status of the book in inventory
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
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_SEARCH_BOOK);
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
        if (commandArgs.length < 2) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_LIST_CATEGORY);
        }
        String category = commandArgs[1].trim();
        try {
            Formatter.printBookList(bookList.findBooksByCategory(category));
        } catch (IllegalArgumentException e) {
            Formatter.printBorderedMessage("Invalid Category: " + category);
        }
    }

    /**
     * Updates details of an existing book.
     *
     * @param commandArgs The parsed command arguments.
     * @throws IncorrectFormatException If the input format is invalid.
     * @throws BookNotFoundException    If the book is not found in the inventory.
     * @throws IllegalArgumentException If the category or condition is invalid.
     */
    private void updateBook(String[] commandArgs) throws IncorrectFormatException, BookNotFoundException,
            IllegalArgumentException {
        if (commandArgs.length < 2) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_UPDATE_BOOK);
        }
        String[] bookArgs = InputParser.extractUpdateBookArgs(commandArgs[1]);
        assert bookArgs.length >= 5 : "Book arguments should contain at least 5 elements";

        String bookTitle = bookArgs[0];
        String author = bookArgs[1];
        String category = bookArgs[2];
        String condition = bookArgs[3];
        String location = bookArgs[4];
        String note = bookArgs[5];

        // Check if book already exists in the inventory
        Book book = bookList.findBookByTitle(bookTitle);
        if (book == null) {
            throw new BookNotFoundException("Book not found in inventory: " + bookTitle);
        }

        if ((author == null || author.isBlank()) &&
                (category == null || category.isBlank()) &&
                (condition == null || condition.isBlank()) &&
                (location == null || location.isBlank()) &&
                (note == null || note.isBlank())) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_UPDATE_BOOK_NO_UPDATES);
        }

        try {
            book.setBookFields(author, category, condition, location, note);
            Formatter.printBorderedMessage("Book Updated:\n" + book);
            Storage.saveInventory(bookList);
        } catch
        (IllegalArgumentException e) {
            Formatter.printBorderedMessage(e.getMessage());
        }
    }

    /**
     * Updates the title of an existing book.
     *
     * @param commandArgs The parsed command arguments.
     * @throws IncorrectFormatException If the input format is invalid.
     * @throws BookNotFoundException    If the book is not found in the inventory.
     */

    private void updateTitle(String[] commandArgs) throws IncorrectFormatException, BookNotFoundException, 
            IllegalArgumentException {
        if (commandArgs.length < 2) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_UPDATE_TITLE);
        }
        String[] updateTitleArgs = InputParser.extractUpdateTitleArgs(commandArgs[1]);
        String oldTitle = updateTitleArgs[0];
        String newTitle = updateTitleArgs[1];

        if(oldTitle.equals(newTitle)){
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_SAME_TITLE);
        }

        // Check if book already exists in the inventory
        Book book = bookList.findBookByTitle(oldTitle);
        if (book == null) {
            throw new BookNotFoundException("Book not found in inventory: " + oldTitle);
        }

        book.setTitle(newTitle);
        Formatter.printBorderedMessage("Book Updated:\n" + book);
        Storage.saveInventory(bookList);

    }
    

    private void editLoan(String[] commandArgs) throws IncorrectFormatException, BookNotFoundException, 
            InvalidArgumentException {
        if (commandArgs.length < 2) {
            throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_EDIT_LOAN);
        }
        String[] editLoanArgs = InputParser.extractEditLoanArgs(commandArgs[1]);
        assert editLoanArgs.length == 5 : "Book arguments should contain 5 elements";

        String bookTitle = editLoanArgs[0];
        String borrowerName = editLoanArgs[1];
        String returnDate = editLoanArgs[2];
        String phoneNumber = editLoanArgs[3];
        String email = editLoanArgs[4];

        // Check if book already exists in the inventory
        Book book = bookList.findBookByTitle(bookTitle);
        Loan loan = loanList.findLoan(book);

        if (book == null) {
            throw new BookNotFoundException("Book not found in inventory: " + bookTitle);
        } else if (!book.isOnLoan()) {
            Formatter.printBorderedMessage("The book " + bookTitle + " is not currently out on loan.");
        } else {
            if ((borrowerName == null || borrowerName.isBlank()) &&
                    (returnDate == null || returnDate.isBlank()) &&
                    (phoneNumber == null || phoneNumber.isBlank()) &&
                    (email == null || email.isBlank())) {
                throw new IncorrectFormatException(ErrorMessages.INVALID_FORMAT_EDIT_LOAN_NO_EDITS);
            }
                
            try {
                loan.setLoanFields(borrowerName, returnDate, phoneNumber, email);
                Formatter.printBorderedMessage("Loan Updated:\n" + loan);
                Storage.saveLoans(loanList);
            } catch (IllegalArgumentException e) {
                Formatter.printBorderedMessage(e.getMessage());
            }
        }
    }
}
