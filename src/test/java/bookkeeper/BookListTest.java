package bookkeeper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

class BookListTest {
    private BookList bookList;
    private Book book1;
    private Book book2;
    private Book book3;

    @BeforeEach
    void setUp() {
        bookList = new BookList("My Book List");

        // Creating mock book objects
        book1 = new Book("Book One", "Author One", "Fiction", "poor", "Shelf 1");
        book2 = new Book("Book Two", "Author Two", "Non-Fiction", "Good", "Shelf 2");
        book3 = new Book("Harry Potter", "Author Two", "Non-Fiction", "Good", "Shelf 2");
    }

    @Test
    void testGetListName() {
        assertEquals("My Book List", bookList.getListName());
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
    void testFindBooksByKeyword() {
        bookList.addBook(book1);
        bookList.addBook(book2);
        bookList.addBook(book3);

        ArrayList<Book> foundBooks = bookList.findBooksByKeyword("e");
        assertEquals(foundBooks.get(0), book1);
        assertNotEquals(foundBooks.get(0), book2);
        assertNotEquals(foundBooks.get(1), book2);
        assertEquals(foundBooks.get(1), book3);
    }

    @Test
    void testFindBooksByCategory() {
        bookList.addBook(book1);
        bookList.addBook(book2);
        bookList.addBook(book3);

        ArrayList<Book> foundBooks = bookList.findBooksByCategory("Non-Fiction");
        assertEquals(foundBooks.get(0), book2);
        assertEquals(foundBooks.get(1), book3);
        assertNotEquals(foundBooks.get(0), book1);
        assertNotEquals(foundBooks.get(1), book1);
    }
}
