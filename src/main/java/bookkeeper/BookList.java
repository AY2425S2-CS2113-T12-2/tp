package bookkeeper;

import java.util.ArrayList;

public class BookList {
    private ArrayList<Book> bookList;
    private String listName;

    public BookList(String listName) {
        this.listName = listName;
        this.bookList = new ArrayList<Book>();
    }

    public String getlistName() {
        return listName;
    }

    public void addBook(Book book) {
        bookList.add(book);
    }

    public Book findBookByTitle(String title) {
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getTitle().equals(title)) {
                return bookList.get(i);
            }
        }
        return null;
    }

    public ArrayList<Book> findBooksByKeyword(String keyword){
        ArrayList<Book> filteredBookList = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getTitle().contains(keyword)) {
                filteredBookList.add(book);
            }
        }
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
