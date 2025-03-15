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

    public void removeBook(Book book) { bookList.remove(book); }

    /**
     * Prints all books in the bookList.
     * First prints the book title, followed by the remaining attributes indented. 
     */
    public void viewBookList() {

        if(bookList.isEmpty()) {
            System.out.println("Book List Empty!");
        }


        for (int i = 0; i < bookList.size(); i++) {
            Book currentBook = bookList.get(i);
            System.out.println((i + 1) + ". " + currentBook.getTitle()
                    + System.lineSeparator() + "    Author: " + currentBook.getAuthor()
                    + System.lineSeparator() + "    Category: " + currentBook.getCategory()
                    + System.lineSeparator() + "    Condition: " + currentBook.getCondition()
                    + System.lineSeparator() + "    On Loan: " + currentBook.getOnLoan());
        }
    }
}
