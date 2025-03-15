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
                        bookList.addBook(new Book(commandArgs[1], commandArgs[2], commandArgs[3], commandArgs[4]));
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
     * Extract arguments needed to create loan object and adds loan object to loan list.
     *
     * @param commandArgs The parsed command arguments.
     */
    private void addLoan(String[] commandArgs) {
        try {
            String[] loanArgs = InputParser.extractAddLoanArgs(commandArgs[1]);
            Book loanedBook = bookList.findBookByTitle(loanArgs[0]);
            if (loanedBook == null) {
                System.out.println("Book not found in inventory: " + loanArgs[0]);
            } else {
                Loan loan = new Loan(loanedBook, loanArgs[2], loanArgs[1]);
                loanList.addLoan(loan);
                System.out.println("Loan added successfully for book: " + loanedBook.getTitle());
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
