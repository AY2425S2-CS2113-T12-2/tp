package bookkeeper.list;

import bookkeeper.model.Book;
import bookkeeper.ui.Formatter;
import bookkeeper.model.Category;

import java.util.ArrayList;

public class BookList {
    private final ArrayList<Book> bookList;
    private final String listName;

    public BookList(String listName, ArrayList<Book> bookList) {
        this.listName = listName;
        this.bookList = bookList;
    }

    public String getListName() {
        return listName;
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public void addBook(Book book) {
        bookList.add(book);
    }

    public Book searchBook(String title) {
        for (Book book : bookList) {
            if (book.getTitle().equals(title)) { 
                return book;
            }
        }
        return null;
    }

    public ArrayList<Book> findBooksByKeyword(String keyword) {
        ArrayList<Book> filteredBookList = new ArrayList<>();
        for (Book book : bookList) {
            String titleLowerCase = book.getTitle().toLowerCase();
            String keywordLowerCase = keyword.toLowerCase();
            if (titleLowerCase.contains(keywordLowerCase)) {
                filteredBookList.add(book);
            }
        }
        return filteredBookList;
    }

    public ArrayList<Book> findBooksByCategory(String category) throws IllegalArgumentException {
        ArrayList<Book> filteredBookList = new ArrayList<>();

        // Normalize the input category string to a Category enum
        Category targetCategory = Category.fromString(category);
        for (Book book : bookList) {
            if (book.getCategory() == targetCategory) { // Compare using the enum value
                filteredBookList.add(book);
            }
        }
        // Handle invalid category input (optional)
        return filteredBookList;
    }

    public void removeBook(Book book) {
        bookList.remove(book);
    }

    /**
     * Prints all books in the bookList.
     * First prints the book title, followed by the remaining attributes indented.
     */
    public void viewBookList() {

        if (bookList.isEmpty()) {
            Formatter.printBorderedMessage("Book List Empty!");
            return;
        }

        Formatter.printBookList(bookList);
    }
}
