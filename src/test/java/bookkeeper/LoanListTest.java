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

import java.util.ArrayList;


public class LoanListTest {
    private LoanList loanList;
    private Book book1;
    private Book book2;
    private Loan loan1;
    //private Loan loan2;

    @BeforeEach
    public void setUp() {
        loanList = new LoanList("Test Loan List", new ArrayList<Loan>());
        BookList bookList = new BookList("Test Book List", new ArrayList<Book>());

        book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", "Good", "Shelf 1");
        book2 = new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", "fair", "Shelf 2");

        bookList.addBook(book1);
        bookList.addBook(book2);

        loan1 = new Loan(book1, "John Doe", "2023-12-01", "98765432", "abc123@gmail.com");
    }

    @Test
    void addLoan_successfullyAddsLoan() {
        loanList.addLoan(loan1);
        Loan foundLoan = loanList.findLoan(book1, "John Doe");
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
        Loan foundLoan = loanList.findLoan(book1, "John Doe");
        assertNull(foundLoan, "Loan should be removed from the list");
    }

    @Test
    void deleteLoan_nonExistingLoan() {
        loanList.addLoan(loan1);
        Loan nonExistingLoan = new Loan(book2, "Nonexistent Borrower", "2024-01-10", "87654321", "def321@gmail.com");
        loanList.deleteLoan(nonExistingLoan); // Attempt to delete a non-existing loan
        assertEquals(1, loanList.getLoanList().size(),
                "Deleting a non-existing loan should not affect the list");
    }

    @Test
    void findLoan_existingLoan() {
        loanList.addLoan(loan1);
        Loan foundLoan = loanList.findLoan(book1, "John Doe");
        assertNotNull(foundLoan, "Loan should be found in the list");
        assertEquals(loan1, foundLoan, "The found loan should match the expected loan");
    }

    @Test
    void findLoan_nonExistingLoan() {
        Loan foundLoan = loanList.findLoan(book1, "Nonexistent Borrower");
        assertNull(foundLoan, "Loan should not be found in the list for a nonexistent borrower");
    }
}
