package bookkeeper;

import bookkeeper.list.BookList;
import bookkeeper.list.LoanList;
import bookkeeper.model.Book;
import bookkeeper.model.Loan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LoanListTest {
    private LoanList loanList;
    private Book book1;
    private Book book2;
    private Loan loan1;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));

        loanList = new LoanList("Test Loan List", new ArrayList<Loan>());
        BookList bookList = new BookList("Test Book List", new ArrayList<Book>());

        book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction",
                "Good", "Shelf 1");
        book2 = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction",
                "Fair", "Shelf 2");

        bookList.addBook(book1);
        bookList.addBook(book2);

        // Set loan1 to 21 days from the current date (valid)
        String futureDate = LocalDate.now().plusDays(21).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        loan1 = new Loan(book1, "John Doe", futureDate, "98765432", "abc123@gmail.com");
    }

    @Test
    void addLoan_successfullyAddsLoan() {
        loanList.addLoan(loan1);
        Loan foundLoan = loanList.findLoan(book1);
        assertNotNull(foundLoan, "Loan should be added to the list");
        assertEquals(loan1, foundLoan, "The added loan should match the expected loan");
    }

    @Test
    void addLoan_addNullLoan_expectAssertionError() {
        // Expect an AssertionError when adding a null loan
        AssertionError error = assertThrows(AssertionError.class, () ->
                loanList.addLoan(null), "Adding a null loan should throw an AssertionError");

        assertEquals("Loan cannot be null", error.getMessage(),
                "The error message should indicate that the loan cannot be null");
    }

    @Test
    void deleteLoan_deleteNullLoan_expectAssertionError() {
        AssertionError error = assertThrows(AssertionError.class, () -> {
            loanList.addLoan(loan1);
            loanList.deleteLoan(null);
        }, "Deleting a null loan should throw an AssertionError");

        assertEquals("Loan cannot be null", error.getMessage(),
                "The error message should indicate that the loan cannot be null");
    }

    @Test
    void addLoan_duplicateLoans() {
        loanList.addLoan(loan1);
        loanList.addLoan(loan1); // Add the same loan again
        assertEquals(2, loanList.getLoanList().size(),
                "Duplicate loans should be allowed in the list");
    }

    @Test
    void deleteLoan_successfullyRemovesLoan() {
        loanList.addLoan(loan1);
        loanList.deleteLoan(loan1);
        Loan foundLoan = loanList.findLoan(book1);
        assertNull(foundLoan, "Loan should be removed from the list");
    }

    @Test
    void deleteLoan_nonExistingLoan() {
        loanList.addLoan(loan1);
        Loan nonExistingLoan = new Loan(book2, "Nonexistent Borrower", "10-01-2027",
                "87654321", "def321@gmail.com");
        loanList.deleteLoan(nonExistingLoan); // Attempt to delete a non-existing loan
        assertEquals(1, loanList.getLoanList().size(),
                "Deleting a non-existing loan should not affect the list");
    }

    @Test
    void findLoan_existingLoan() {
        loanList.addLoan(loan1);
        Loan foundLoan = loanList.findLoan(book1);
        assertNotNull(foundLoan, "Loan should be found in the list");
        assertEquals(loan1, foundLoan, "The found loan should match the expected loan");
    }

    @Test
    void findLoan_nonExistingLoan() {
        Loan foundLoan = loanList.findLoan(book1);
        assertNull(foundLoan, "Loan should not be found in the list for a nonexistent borrower");
    }

    @Test
    void getListName_test() {
        assertEquals("Test Loan List", loanList.getListName(),
                "getListName() should return the list name provided in the constructor");
    }

    @Test
    void findLoanByIndex_validIndex() {
        loanList.addLoan(loan1);
        Loan found = loanList.findLoanByIndex(1);
        assertEquals(loan1, found, "findLoanByIndex(1) should return the first loan");
    }

    @Test
    void findLoanByIndex_invalidIndex() {
        loanList.addLoan(loan1);
        // Index 0 or an index greater than list size should return null.
        assertNull(loanList.findLoanByIndex(0),
                "findLoanByIndex(0) should return null (invalid index)");
        assertNull(loanList.findLoanByIndex(2),
                "findLoanByIndex(2) should return null when no loan exists at that position");
    }

    @Test
    void removeLoansByBook_valid() {
        // Add two loans for book1 and one for book2.
        loanList.addLoan(loan1);
        Loan loan2 = new Loan(book1, "Jane Smith",
                LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                "12345678", "jane@example.com");
        Loan loan3 = new Loan(book2, "Bob Brown",
                LocalDate.now().plusDays(25).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                "87654321", "bob@example.com");
        loanList.addLoan(loan2);
        loanList.addLoan(loan3);

        // Verify that three loans are in the list.
        assertEquals(3, loanList.getLoanList().size(),
                "There should be three loans before removal");

        // Remove all loans associated with book1.
        loanList.removeLoansByBook(book1);

        // Only the loan associated with book2 should remain.
        assertEquals(1, loanList.getLoanList().size(),
                "After removal, only loans not associated with book1 should remain");
        assertEquals(loan3, loanList.getLoanList().get(0),
                "The remaining loan should be the one associated with book2");

        // Additionally, findLoan() for book1 should now return null.
        assertNull(loanList.findLoan(book1),
                "No loan for book1 should be found after removal");
    }

    @Test
    void removeLoansByBook_nullBook() {
        // Passing null should trigger an assertion.
        AssertionError error = assertThrows(AssertionError.class,
                () -> loanList.removeLoansByBook(null),
                "Passing null to removeLoansByBook() should throw an AssertionError");
        assertEquals("Book cannot be null", error.getMessage(),
                "Error message should indicate that the book cannot be null");
    }

    @Test
    void viewLoanList_emptyLoanList() {
        loanList.viewLoanList();
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("Loan List Empty!"));
    }

    @Test
    void viewLoanList_oneLoanLoanList() {
        loanList.addLoan(loan1);
        loanList.viewLoanList();
        String output = outputStreamCaptor.toString().trim();
        assertTrue(output.contains("The Great Gatsby"));
    }
}
