package bookkeeper.storage;

import bookkeeper.exceptions.InvalidArgumentException;
import bookkeeper.list.BookList;
import bookkeeper.list.LoanList;
import bookkeeper.model.Book;
import bookkeeper.model.Loan;
import bookkeeper.ui.Formatter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private static final String FOLDER_PATH = "./data";

    private static final String INVENTORY_FILE_NAME = "bookKeeper_bookList.txt";
    private static final String LOAN_LIST_FILE_NAME = "bookKeeper_loanList.txt";

    private static String inventoryFilePath = FOLDER_PATH + "/" + INVENTORY_FILE_NAME;
    private static String loanListFilePath = FOLDER_PATH + "/" + LOAN_LIST_FILE_NAME;


    // Setter for inventoryFilePath
    public static void setInventoryFilePath(String filePath) {
        inventoryFilePath = filePath;
    }

    // Setter for loanListFilePath
    public static void setLoanFilePath(String filePath) {
        loanListFilePath = filePath;
    }

    /**
     * Saves the given loan list to the file.
     * Each loan is saved as a line in the file.
     *
     * @param loanList LoanList containing the list of loans to save.
     */
    public static void saveLoans(LoanList loanList) {
        try {
            // Ensure the directory exists
            File directory = new File(FOLDER_PATH);
            if (!directory.exists()) {
                directory.mkdirs(); // Create the directory if it doesn't exist
            }

            // Create a FileWriter to write to the file
            FileWriter fileWriter = new FileWriter(loanListFilePath);

            // Write each loan to the file
            ArrayList<Loan> loans = loanList.getLoanList();
            for (Loan loan : loans) {
                fileWriter.write(loan.toFileString() + System.lineSeparator());
            }

            fileWriter.close(); // Close the FileWriter to complete the writing process
        } catch (IOException e) {
            Formatter.printBorderedMessage("Something went wrong while saving loans: " + e.getMessage());
        }
    }

    /**
     * Saves the given book list to the file.
     * Each book is saved as a line in the file.
     *
     * @param bookList BookList containing the list of loans to save.
     */
    public static void saveInventory(BookList bookList) {
        try {
            // Ensure the directory exists
            File directory = new File(FOLDER_PATH);
            if (!directory.exists()) {
                directory.mkdirs(); // Create the directory if it doesn't exist
            }

            // Create a FileWriter to write to the file
            FileWriter fileWriter = new FileWriter(inventoryFilePath);

            // Write each loan to the file
            ArrayList<Book> books = bookList.getBookList();
            for (Book book : books) {
                fileWriter.write(book.toFileString() + System.lineSeparator());
            }

            fileWriter.close(); // Close the FileWriter to complete the writing process
        } catch (IOException e) {
            Formatter.printBorderedMessage("Something went wrong while saving inventory: " + e.getMessage());
        }
    }

    public static ArrayList<Book> loadInventory() {
        ArrayList<Book> bookList = new ArrayList<>();
        File file = new File(inventoryFilePath);

        try {
            // Check if the file exists
            if (!file.exists()) {
                Formatter.printBorderedMessage("No saved inventory found. Starting with an empty inventory.\n" +
                        "Creating a new text file at " + inventoryFilePath + ".");
                return bookList;
            }

            // Create a Scanner to read from the file
            Scanner scanner = new Scanner(file);

            // Read each line and parse it into a book object
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Book book = parseBookFromString(line);

                // Skip invalid book entries
                if (book == null) {
                    continue;
                }

                // Skip duplicate books
                boolean isDuplicate = bookList.stream()
                        .anyMatch(existingBook -> existingBook.getTitle().equalsIgnoreCase(book.getTitle()));
                if (isDuplicate) {
                    Formatter.printBorderedMessage("Duplicate book found and skipped: " + book.getTitle());
                    continue;
                }

                // Add valid book to the list
                bookList.add(book);
            }

            scanner.close(); // Close the Scanner
        } catch (IOException e) {
            Formatter.printBorderedMessage("Something went wrong while loading inventory: " + e.getMessage());
        }

        Formatter.printBorderedMessage("Loaded " + bookList.size() + " books from " + inventoryFilePath + ".");
        return bookList;
    }

    public static ArrayList<Loan> loadLoans(BookList bookList) {
        ArrayList<Loan> loanList = new ArrayList<>();
        File file = new File(loanListFilePath);

        try {
            // Check if the file exists
            if (!file.exists()) {
                Formatter.printBorderedMessage("No saved loans found. Starting with an empty loan list.\n" +
                        "Creating a new text file at " + loanListFilePath + ".");
                return loanList;
            }

            // Create a Scanner to read from the file
            Scanner scanner = new Scanner(file);

            // Read each line and parse it into a loan object
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Loan loan = parseLoanFromString(line, bookList);

                // Skip invalid loans
                if (loan == null) {
                    continue;
                }

                // Skip duplicate loans
                boolean isDuplicate = loanList.stream()
                        .anyMatch(existingLoan -> existingLoan.getBook().equals(loan.getBook()));
                if (isDuplicate) {
                    Formatter.printBorderedMessage("Duplicate loan found and skipped: " +
                            loan.getBook().getTitle() + " borrowed by " + loan.getBorrowerName());
                    continue;
                }

                // Add valid loan to the list and mark the book as on loan
                loanList.add(loan);
                loan.getBook().setOnLoan(true);
            }

            scanner.close(); // Close the Scanner
        } catch (IOException e) {
            Formatter.printBorderedMessage("Something went wrong while loading loans: " + e.getMessage());
        } catch (InvalidArgumentException e) {
            Formatter.printBorderedMessage("Something went wrong while loading loans: " + e.getMessage());
        }

        Formatter.printBorderedMessage("Loaded " + loanList.size() + " loans from " + loanListFilePath + ".");
        return loanList;
    }

    private static Book parseBookFromString(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 6) {
            return null; // Invalid format
        }

        String title = parts[0].trim();
        String author = parts[1].trim();
        String category = parts[2].trim();
        String condition = parts[3].trim();
        boolean onLoan = Boolean.parseBoolean(parts[4].trim());
        String location = parts[5].trim();
        String note = (parts.length == 7) ? parts[6].trim() : "";

        Book book;
        // Normalize case for title, author, and category
        try {
            book = new Book(title, author, category, condition, location, note);
        } catch (IllegalArgumentException e) {
            // Handle invalid book creation
            Formatter.printBorderedMessage("Invalid book entry skipped: " + line + "\nReason: " + e.getMessage());
            return null; // Skip this book
        }
        return book;
    }

    private static Loan parseLoanFromString(String line, BookList bookList) throws InvalidArgumentException {
        String[] parts = line.split(" \\| ");

        if (parts.length < 5) {
            Formatter.printBorderedMessage("Invalid loan format: " + line);
            return null;
        }

        String title = parts[0].trim();
        String borrowerName = parts[1].trim();
        String returnDate = parts[2].trim();
        String phoneNumber = parts[3].trim();
        String email = parts[4].trim();

        // Find the book in the inventory
        Book loanedBook = bookList.findBookByTitle(title);
        if (loanedBook == null) {
            Formatter.printBorderedMessage("Invalid loan: Book not found in inventory - " + title);
            return null; // Skip this loan
        }

        if (!phoneNumber.matches("^[986][0-9]{7}$")) {
            throw new InvalidArgumentException("Invalid loan entry skipped: " + line + "\nReason: " + 
                    "Illegal phone number");
        }

        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new InvalidArgumentException("Invalid loan entry skipped: " + line + "\nReason: " 
                    + "Illegal email");
        }

        try {
            // Attempt to create a Loan object
            return new Loan(loanedBook, borrowerName, returnDate, phoneNumber, email);
        } catch (IllegalArgumentException e) {
            // Handle invalid date or other issues in Loan creation
            Formatter.printBorderedMessage("Invalid loan entry skipped: " + line + "\nReason: " + e.getMessage());
            return null; // Skip this loan
        }
    }

    public static void validateStorage(BookList bookList, LoanList loanList) {
        ArrayList<Book> books = bookList.getBookList();
        ArrayList<Loan> loans = loanList.getLoanList();

        // Reset all books to not on loan
        for (Book book : books) {
            book.setOnLoan(false);
        }

        // Mark books as on loan based on the LoanList
        for (Loan loan : loans) {
            loan.getBook().setOnLoan(true);
        }

        saveLoans(loanList);
        saveInventory(bookList);
    }

}
