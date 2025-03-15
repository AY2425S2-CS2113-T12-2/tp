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
    
    public void removeBook(String title) {
        //Add Implementation 
    }

    public void viewBookList() {
        for (int i = 0; i < bookList.size(); i++) {
            System.out.println("Title: " + bookList.get(i).getTitle());
            System.out.println("Title: " + bookList.get(i).getAuthor());
            System.out.println("Title: " + bookList.get(i).getCategory());
            System.out.println("Title: " + bookList.get(i).getCondition());
            System.out.println("Title: " + bookList.get(i).getOnLoan());
        }
    }
}
