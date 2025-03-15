package bookkeeper;

import java.util.Scanner;

public class InputHandler {
    private BookList bookList;
    private LoanList loanList;

    public InputHandler() {
        this.bookList = new BookList("Inventory");
        this.loanList = new LoanList("Loan List");
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
                System.out.println("Please enter a command");
            } else {
                try {
                    String[] commandArgs = InputParser.extractCommandArgs(userInputLine);
                    switch (commandArgs[0]) {
                    case "add-book":
                        addBook(commandArgs);
                        break;
                    case "view-inventory":
                        bookList.viewBookList();
                        break;
                    case "remove-book":
                        break;
                    case "add-loan":
                        addLoan(commandArgs);
                        break;
                    case "delete-loan":
                        break;
                    case "view-loans":
                        loanList.viewLoanList();
                        break;
                    case "exit":
                        System.out.println("Exiting BookKeeper...");
                        isAskingInput = false;
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown command: " + commandArgs[0]);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /**
     * Adds loan object to loanList by first extracting arguments needed to create loan object.
     * Before adding, book has to exist in bookList and is available for loan.
     *
     * @param commandArgs The parsed command arguments.
     */
    private void addLoan(String[] commandArgs) {
        try {
            String[] loanArgs = InputParser.extractAddLoanArgs(commandArgs[1]);
            Book loanedBook = bookList.findBookByTitle(loanArgs[0]);
            if (loanedBook == null) {
                System.out.println("Book not found in inventory: " + loanArgs[0]);
            } else if (loanedBook.getOnLoan()) {
                System.out.println("The book " + loanArgs[0] + "is currently out on loan.");
            } else {
                Loan loan = new Loan(loanedBook, loanArgs[2], loanArgs[1]);
                loanList.addLoan(loan);
                System.out.println("Loan added successfully for book: " + loanedBook.getTitle());
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Extract arguments needed to create book object and adds book object to book list.
     *
     * @param commandArgs The parsed command arguments.
     */
    private void addBook(String[] commandArgs) {
        if (commandArgs.length < 2) {
            throw new IllegalArgumentException("Invalid format for add-book. " +
                    "Expected format: add-book BOOK_TITLE a/AUTHOR cat/CATEGORY cond/CONDITION");
        }
        String[] bookArgs = InputParser.extractAddBookArgs(commandArgs[1]);
        Book newBook = new Book(bookArgs[0], bookArgs[1], bookArgs[2], bookArgs[3]);
        bookList.addBook(newBook);
        System.out.println("New book added: " + newBook.getTitle());
    }
}
