package bookkeeper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class BookListTest {
    private BookList bookList;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        bookList = new BookList("My Book List");

        // Creating mock book objects
        book1 = new Book("Book One", "Author One", "Fiction", "New");
        book2 = new Book("Book Two", "Author Two", "Non-Fiction", "Good");
    }

    @Test
    void testGetListName() {
        assertEquals("My Book List", bookList.getlistName());
    }

    @Test
    void testAddBook() {
        bookList.addBook(book1);
        assertEquals(book1, bookList.findBookByTitle("Book One"));
    }

    @Test
    void testFindBookByTitle() {
        bookList.addBook(book1);
        bookList.addBook(book2);

        assertEquals(book1, bookList.findBookByTitle("Book One"));
        assertEquals(book2, bookList.findBookByTitle("Book Two"));
        assertNull(bookList.findBookByTitle("Nonexistent Book"));
    }

    @Test
    void testRemoveBook() {
        bookList.addBook(book1);
        bookList.addBook(book2);

        bookList.removeBook(book1);
        assertNull(bookList.findBookByTitle("Book One"));
        assertNotNull(bookList.findBookByTitle("Book Two"));
    }

    @Test
    void testViewBookList() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        bookList.viewBookList();
        assertTrue(outContent.toString().contains("Book List Empty!"));

        bookList.addBook(book1);
        bookList.addBook(book2);

        outContent.reset();
        bookList.viewBookList();

        String output = outContent.toString();
        assertTrue(output.contains("1. Book One"));
        assertTrue(output.contains("Author: Author One"));
        assertTrue(output.contains("Category: Fiction"));
        assertTrue(output.contains("Condition: New"));
        assertTrue(output.contains("On Loan: false"));

        assertTrue(output.contains("2. Book Two"));
        assertTrue(output.contains("Author: Author Two"));
        assertTrue(output.contains("Category: Non-Fiction"));
        assertTrue(output.contains("Condition: Good"));
        assertTrue(output.contains("On Loan: false"));

        System.setOut(System.out);
    }
}