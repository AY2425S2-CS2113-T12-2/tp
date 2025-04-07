package bookkeeper;

import bookkeeper.list.BookList;
import bookkeeper.list.LoanList;
import bookkeeper.model.Book;
import bookkeeper.model.Loan;
import bookkeeper.storage.Storage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class StorageTest {

    private static final String TEST_FOLDER_PATH = "./test_data";
    private static final String TEST_BOOK_LIST_FILE_PATH = TEST_FOLDER_PATH + "/test_bookKeeper_bookList.txt";
    private static final String TEST_LOAN_LIST_FILE_PATH = TEST_FOLDER_PATH + "/test_bookKeeper_loanList.txt";

    private BookList bookList;
    private LoanList loanList;

    @BeforeEach
    void setUp() {
        // Create test folder
        File testFolder = new File(TEST_FOLDER_PATH);
        if (!testFolder.exists()) {
            testFolder.mkdirs();
        }

        // Initialize empty BookList and LoanList
        bookList = new BookList("Test Inventory", new ArrayList<>());
        loanList = new LoanList("Test Loan List", new ArrayList<>());

        // Update file paths for testing
        Storage.setInventoryFilePath(TEST_BOOK_LIST_FILE_PATH);
        Storage.setLoanFilePath(TEST_LOAN_LIST_FILE_PATH);
    }

    @AfterEach
    void tearDown() {
        // Delete test files after each test
        File bookFile = new File(TEST_BOOK_LIST_FILE_PATH);
        File loanFile = new File(TEST_LOAN_LIST_FILE_PATH);

        if (bookFile.exists()) {
            bookFile.delete();
        }
        if (loanFile.exists()) {
            loanFile.delete();
        }
    }

    @Test
    void saveInventory_validBookList_fileCreatedWithCorrectData() {
        // Add books to the BookList
        bookList.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction",
                "Good", "Shelf 1", "Classic novel"));
        bookList.addBook(new Book("To Kill a Mockingbird", "Harper Lee", "Fiction",
                "Fair", "Shelf 2", "Pulitzer Prize winner"));

        // Save the inventory
        Storage.saveInventory(bookList);

        // Verify the file exists
        File bookFile = new File(TEST_BOOK_LIST_FILE_PATH);
        assertTrue(bookFile.exists());
    }

    @Test
    void loadInventory_validFile_correctBookListLoaded() {
        // Create a test file with valid book data
        try (FileWriter writer = new FileWriter(TEST_BOOK_LIST_FILE_PATH)) {
            writer.write("The Great Gatsby | F. Scott Fitzgerald | Fiction | Good | false | Shelf 1 | " +
                    "Classic novel\n");
            writer.write("To Kill a Mockingbird | Harper Lee | Fiction | Fair | true | Shelf 2 | " +
                    "Pulitzer Prize winner\n");
        } catch (Exception e) {
            fail("Failed to create test file: " + e.getMessage());
        }

        // Load the inventory
        ArrayList<Book> loadedBooks = Storage.loadInventory();

        // Verify the loaded books
        assertEquals(2, loadedBooks.size());
        assertEquals("The Great Gatsby", loadedBooks.get(0).getTitle());
        assertEquals("To Kill a Mockingbird", loadedBooks.get(1).getTitle());
    }

    @Test
    void loadInventory_invalidLines_invalidLinesSkipped() {
        // Create a test file with invalid lines
        try (FileWriter writer = new FileWriter(TEST_BOOK_LIST_FILE_PATH)) {
            writer.write("The Great Gatsby | F. Scott Fitzgerald | Fiction | Good | false | Shelf 1 | " +
                    "Classic novel\n");
            writer.write("Invalid Book Entry\n");
            writer.write("To Kill a Mockingbird | Harper Lee | Fiction | Fair | true | Shelf 2 | " +
                    "Pulitzer Prize winner\n");
        } catch (Exception e) {
            fail("Failed to create test file: " + e.getMessage());
        }

        // Load the inventory
        ArrayList<Book> loadedBooks = Storage.loadInventory();

        // Verify the loaded books
        assertEquals(2, loadedBooks.size());
        assertEquals("The Great Gatsby", loadedBooks.get(0).getTitle());
        assertEquals("To Kill a Mockingbird", loadedBooks.get(1).getTitle());
    }

    @Test
    void saveLoans_validLoanList_fileCreatedWithCorrectData() {
        // Add books to the BookList
        Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction",
                "Good", "Shelf 1", "Classic novel");
        Book book2 = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction",
                "Fair", "Shelf 2", "Pulitzer Prize winner");
        bookList.addBook(book1);
        bookList.addBook(book2);

        // Add loans to the LoanList
        String futureDate1 = LocalDate.now().plusDays(21).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String futureDate2 = LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        loanList.addLoan(new Loan(book1, "John Doe", futureDate1, "1234567890",
                "johndoe@example.com"));
        loanList.addLoan(new Loan(book2, "Jane Doe", futureDate2, "0987654321",
                "janedoe@example.com"));

        // Save the loans
        Storage.saveLoans(loanList);

        // Verify the file exists
        File loanFile = new File(TEST_LOAN_LIST_FILE_PATH);
        assertTrue(loanFile.exists());
    }

    @Test
    void loadLoans_validFile_correctLoanListLoaded() {
        // Add books to the BookList
        Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction",
                "Good", "Shelf 1", "Classic novel");
        Book book2 = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction",
                "Fair", "Shelf 2", "Pulitzer Prize winner");
        bookList.addBook(book1);
        bookList.addBook(book2);

        // Create a test file with valid loan data
        try (FileWriter writer = new FileWriter(TEST_LOAN_LIST_FILE_PATH)) {
            writer.write("The Great Gatsby | John Doe | 21-12-2026 | 81234567 | johndoe@example.com\n");
            writer.write("To Kill a Mockingbird | Jane Doe | 30-12-2026 | 98765432 | janedoe@example.com\n");
        } catch (Exception e) {
            fail("Failed to create test file: " + e.getMessage());
        }

        // Load the loans
        ArrayList<Loan> loadedLoans = Storage.loadLoans(bookList);

        // Verify the loaded loans
        assertEquals(2, loadedLoans.size());
        assertEquals("The Great Gatsby", loadedLoans.get(0).getBook().getTitle());
        assertEquals("John Doe", loadedLoans.get(0).getBorrowerName());
        assertEquals("To Kill a Mockingbird", loadedLoans.get(1).getBook().getTitle());
        assertEquals("Jane Doe", loadedLoans.get(1).getBorrowerName());
    }

    @Test
    void loadLoans_invalidLines_invalidLinesSkipped() {
        // Add books to the BookList
        Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction",
                "Good", "Shelf 1", "Classic novel");
        Book book2 = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction",
                "Fair", "Shelf 2", "Pulitzer Prize winner");
        bookList.addBook(book1);
        bookList.addBook(book2);

        // Create a test file with invalid lines
        try (FileWriter writer = new FileWriter(TEST_LOAN_LIST_FILE_PATH)) {
            writer.write("The Great Gatsby | John Doe | 21-12-2026 | 1234567 | johndoe@example.com\n");
            writer.write("Invalid Loan Entry\n");
            writer.write("To Kill a Mockingbird | Jane Doe | 30-12-2026 | 98765432 | janedoe@example.com\n");
        } catch (Exception e) {
            fail("Failed to create test file: " + e.getMessage());
        }

        // Load the loans
        ArrayList<Loan> loadedLoans = Storage.loadLoans(bookList);

        // Verify the loaded loans
        assertEquals(1, loadedLoans.size());
        assertEquals("To Kill a Mockingbird", loadedLoans.get(0).getBook().getTitle());
    }
}
