package bookkeeper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class BookListTest {
    private BookList bookList;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        bookList = new BookList("My Book List");

        // Creating mock book objects
        book1 = new Book("Book One", "Author One", "Fiction", "New", "Shelf 1");
        book2 = new Book("Book Two", "Author Two", "Non-Fiction", "Good", "Shelf 2");
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

}
