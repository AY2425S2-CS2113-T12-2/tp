package bookkeeper;

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
            FileWriter fw = new FileWriter(loanListFilePath);

            // Write each loan to the file
            ArrayList<Loan> loans = loanList.getLoanList();
            for (Loan loan : loans) {
                fw.write(loan.toFileString() + System.lineSeparator());
            }

            fw.close(); // Close the FileWriter to complete the writing process
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
            FileWriter fw = new FileWriter(inventoryFilePath);

            // Write each loan to the file
            ArrayList<Book> books = bookList.getBookList();
            for (Book book : books) {
                fw.write(book.toFileString() + System.lineSeparator());
            }

            fw.close(); // Close the FileWriter to complete the writing process
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

                if (book != null) {
                    // Check for duplicates
                    boolean isDuplicate = bookList.stream()
                            .anyMatch(existingBook -> existingBook.getTitle().equalsIgnoreCase(book.getTitle()));

                    if (isDuplicate) {
                        Formatter.printBorderedMessage("Duplicate book found and skipped: " + book.getTitle());
                    } else {
                        bookList.add(book);
                    }
                } else {
                    Formatter.printBorderedMessage("Invalid book entry skipped: " + line);
                }
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

                if (loan != null) {
                    // Check for duplicates
                    boolean isDuplicate = loanList.stream()
                            .anyMatch(existingLoan -> existingLoan.getBook().equals(loan.getBook()) &&
                                    existingLoan.getBorrowerName().equalsIgnoreCase(loan.getBorrowerName()));

                    if (isDuplicate) {
                        Formatter.printBorderedMessage("Duplicate loan found and skipped: " +
                                loan.getBook().getTitle() + " borrowed by " + loan.getBorrowerName());
                    } else {
                        loanList.add(loan);
                    }
                } else {
                    Formatter.printBorderedMessage("Invalid loan entry skipped: " + line);
                }
            }

            scanner.close(); // Close the Scanner
        } catch (IOException e) {
            Formatter.printBorderedMessage("Something went wrong while loading loans: " + e.getMessage());
        }

        Formatter.printBorderedMessage("Loaded " + loanList.size() + " loans from " + loanListFilePath + ".");
        return loanList;
    }

    private static Book parseBookFromString(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 6) {
            return null; //invalid format
        }

        String title = parts[0];
        String author = parts[1];
        String category = parts[2];
        String condition = parts[3];
        boolean onLoan = Boolean.parseBoolean(parts[4]);
        String location = parts[5];
        String note = (parts.length == 7) ? parts[6] : "";
        Book book = new Book(title, author, category, condition, location, note);
        book.setOnLoan(onLoan);
        return book;
    }

    private static Loan parseLoanFromString(String line, BookList bookList) {
        String[] parts = line.split(" \\| ");

        if (parts.length < 5) {
            Formatter.printBorderedMessage("Invalid loan format: " + line);
            return null;
        }
        String title = parts[0];
        String borrowerName = parts[1];
        String returnDate = parts[2];
        String phoneNumber = parts[3];
        String email = parts[4];

        Book loanedBook = bookList.findBookByTitle(title); //to get the exact reference for the book
        if (loanedBook == null) {
            Formatter.printBorderedMessage("Invalid loan: Book not found in inventory - " + title);
            return null; // Skip this loan
        }

        return new Loan(loanedBook, borrowerName, returnDate, phoneNumber, email);
    }

    public static void validateStorage(BookList bookList, LoanList loanList) {
        ArrayList<Book> books = bookList.getBookList();
        ArrayList<Loan> loans = loanList.getLoanList();

        // Remove duplicate books
        ArrayList<Book> uniqueBooks = new ArrayList<>();
        ArrayList<Book> duplicateBooks = new ArrayList<>();

        for (Book book : books) {
            boolean isDuplicate = uniqueBooks.stream()
                    .anyMatch(existingBook -> existingBook.getTitle().equalsIgnoreCase(book.getTitle()));

            if (isDuplicate) {
                duplicateBooks.add(book);
                Formatter.printBorderedMessage("Duplicate book found: " + book.getTitle());
            } else {
                uniqueBooks.add(book);
            }
        }

        books.removeAll(duplicateBooks);
        if (!duplicateBooks.isEmpty()) {
            Formatter.printBorderedMessage("Duplicate books found and removed: " + duplicateBooks.size());
        }

        // Remove duplicate loans
        ArrayList<Loan> uniqueLoans = new ArrayList<>();
        ArrayList<Loan> duplicateLoans = new ArrayList<>();

        for (Loan loan : loans) {
            boolean isDuplicate = uniqueLoans.stream()
                    .anyMatch(existingLoan -> existingLoan.getBook().equals(loan.getBook()) &&
                            existingLoan.getBorrowerName().equalsIgnoreCase(loan.getBorrowerName()));

            if (isDuplicate) {
                duplicateLoans.add(loan);
                Formatter.printBorderedMessage("Duplicate loan found: " +
                        loan.getBook().getTitle() + " borrowed by " + loan.getBorrowerName());
            } else {
                uniqueLoans.add(loan);
            }
        }

        loans.removeAll(duplicateLoans);

        if (!duplicateLoans.isEmpty()) {
            Formatter.printBorderedMessage("Duplicate loans found and removed: " + duplicateLoans.size());
        }

        // Save the updated lists to the files
        saveLoans(loanList);
        saveInventory(bookList);
        Formatter.printBorderedMessage("Storage validated and updated successfully.");
    }

}
